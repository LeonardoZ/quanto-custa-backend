package br.com.leonardoz.quantocusta.controller;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.leonardoz.quantocusta.apicontrato.AtualizarOrcamentoDto;
import br.com.leonardoz.quantocusta.apicontrato.CriarOrcamentoDto;
import br.com.leonardoz.quantocusta.apicontrato.OrcamentoDto;
import br.com.leonardoz.quantocusta.apicontrato.RemovidoDto;
import br.com.leonardoz.quantocusta.entidade.Orcamento;
import br.com.leonardoz.quantocusta.entidade.Usuario;
import br.com.leonardoz.quantocusta.exceptions.RecursoNaoEncontradoException;
import br.com.leonardoz.quantocusta.repositorio.OrcamentosRepository;
import br.com.leonardoz.quantocusta.repositorio.UsuariosRepository;
import br.com.leonardoz.quantocusta.servico.OrcamentoService;

@Transactional
@RestController
public class OrcamentoController {

	@Autowired
	private OrcamentosRepository repositorio;

	@Autowired
	private OrcamentoService servico;

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/orcamentos/do/usuario/{usuarioUuid}")
	public Page<OrcamentoDto> listar(Pageable pageable, @PathVariable String usuarioUuid) {
		Usuario usuario = recuperarUsuario(usuarioUuid);

		Page<OrcamentoDto> pagina = repositorio.findByUsuarioId(usuario.getId(), pageable).map(o -> {
			OrcamentoDto dto = mapper.map(o, OrcamentoDto.class);
			completaDto(o, dto);
			return dto;
		});
		return pagina;
	}

	@GetMapping("/orcamento/{uuid}")
	public OrcamentoDto retornar(@PathVariable String uuid) throws RecursoNaoEncontradoException {
		Orcamento orcamento = recuperarOrcamento(uuid);
		OrcamentoDto dto = mapper.map(orcamento, OrcamentoDto.class);
		completaDto(orcamento, dto);
		return dto;
	}

	private void completaDto(Orcamento orcamento, OrcamentoDto dto) {
		CompletableFuture<Integer> recuperaQuantidadeArtefatos = servico.recuperaQuantidadeArtefatos(orcamento);
		CompletableFuture<Integer> recuperaQuantidadeUnidades = servico.recuperaQuantidadeUnidades(orcamento);
		CompletableFuture<Double> calcularSomaTotal = servico.calcularSomaTotal(orcamento);
		try {
			dto.setQuantidadeArtefatos(recuperaQuantidadeArtefatos.get());
			dto.setQuantidadeUnidades(recuperaQuantidadeUnidades.get());
			dto.setValorTotal(calcularSomaTotal.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

	@PostMapping("/orcamento/do/usuario/{usuarioUuid}")
	public OrcamentoDto salvar(@PathVariable String usuarioUuid, @RequestBody CriarOrcamentoDto dto) {
		Usuario usuario = recuperarUsuario(usuarioUuid);
		Orcamento orcamento = mapper.map(dto, Orcamento.class);
		orcamento.setUsuario(usuario);
		Orcamento salvo = repositorio.save(orcamento);
		return mapper.map(salvo, OrcamentoDto.class);
	}

	@PutMapping("/orcamento/{uuid}")
	public OrcamentoDto atualizar(@PathVariable String uuid, @RequestBody AtualizarOrcamentoDto dto)
			throws RecursoNaoEncontradoException {
		Orcamento orcamento = recuperarOrcamento(uuid);

		// ModelMapper? Não ficou bom fazer um update parcial aqui.
		// TODO - Pensar em um "merger"
		orcamento.setCliente(dto.getCliente());
		orcamento.setResponsavel(dto.getResponsavel());
		orcamento.setNome(dto.getNome());
		orcamento.setValidoAte(dto.getValidoAte());

		orcamento = repositorio.save(orcamento);
		return mapper.map(orcamento, OrcamentoDto.class);

	}

	@DeleteMapping("/orcamento/{uuid}")
	public RemovidoDto remover(@PathVariable String uuid) throws RecursoNaoEncontradoException {
		Orcamento orcamento = recuperarOrcamento(uuid);
		repositorio.delete(orcamento.getId());
		return new RemovidoDto("Orcamento", uuid, "Orcamento removido com sucesso");
	}

	private Orcamento recuperarOrcamento(String uuid) {
		Orcamento orcamento = servico.recuperaOrcamento(uuid);
		return orcamento;
	}

	@Autowired
	private UsuariosRepository usuarioRepository;

	private Usuario recuperarUsuario(String uuid) {
		Optional<Usuario> encontrado = usuarioRepository.findByUuid(uuid);
		Usuario usuario = encontrado.orElseThrow(() -> new RecursoNaoEncontradoException("Usuário"));
		return usuario;
	}

}
