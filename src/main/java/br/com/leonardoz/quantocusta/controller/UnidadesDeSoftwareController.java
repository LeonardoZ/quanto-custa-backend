package br.com.leonardoz.quantocusta.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.leonardoz.quantocusta.apicontrato.CriarAtualizarUnidadeDeSoftwareDto;
import br.com.leonardoz.quantocusta.apicontrato.RemovidoDto;
import br.com.leonardoz.quantocusta.apicontrato.UnidadeDeSoftwareDto;
import br.com.leonardoz.quantocusta.entidade.Orcamento;
import br.com.leonardoz.quantocusta.entidade.UnidadeDeSoftware;
import br.com.leonardoz.quantocusta.exceptions.RecursoNaoEncontradoException;
import br.com.leonardoz.quantocusta.repositorio.OrcamentosRepository;
import br.com.leonardoz.quantocusta.repositorio.UnidadesDeSoftwareRepository;

@Transactional
@RestController
public class UnidadesDeSoftwareController {

	@Autowired
	private UnidadesDeSoftwareRepository unidadeRepositorio;

	@Autowired
	private OrcamentosRepository orcamentoRepositorio;

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/unidades/do/orcamento/{orcamentoUuid}")
	public List<UnidadeDeSoftwareDto> listar(@PathVariable String orcamentoUuid) {
		Orcamento orcamento = recuperarOrcamento(orcamentoUuid);
		List<UnidadeDeSoftwareDto> unidades = unidadeRepositorio
				.findByOrcamentoId(orcamento.getId()).stream()
				.map(u -> {
					UnidadeDeSoftwareDto dto = mapper.map(u, UnidadeDeSoftwareDto.class);
					dto.setSubTotal(u.calculaSubTotal());
					return dto;
				}).collect(Collectors.toList());
		return unidades;
	}

	@GetMapping("/unidade/{uuid}")
	public UnidadeDeSoftwareDto retornar(@PathVariable String uuid) throws RecursoNaoEncontradoException {
		UnidadeDeSoftware unidade = recuperarUnidade(uuid);
		UnidadeDeSoftwareDto dto = mapper.map(unidade, UnidadeDeSoftwareDto.class);
		dto.setSubTotal(unidade.calculaSubTotal());
		return dto;
	}

	@PostMapping("/unidade/do/orcamento/{orcamentoUuid}")
	public UnidadeDeSoftwareDto salvar(@PathVariable String orcamentoUuid,
			@RequestBody CriarAtualizarUnidadeDeSoftwareDto dto) throws RecursoNaoEncontradoException {
		Orcamento orcamento = recuperarOrcamento(orcamentoUuid);
		UnidadeDeSoftware unidade = mapper.map(dto, UnidadeDeSoftware.class);
		unidade.setOrcamento(orcamento);
		UnidadeDeSoftware salvo = unidadeRepositorio.save(unidade);
		return mapper.map(salvo, UnidadeDeSoftwareDto.class);
	}

	@PutMapping("/unidade/{uuid}")
	public UnidadeDeSoftwareDto atualizar(@PathVariable String uuid,
			@RequestBody CriarAtualizarUnidadeDeSoftwareDto dto) throws RecursoNaoEncontradoException {
		UnidadeDeSoftware unidade = recuperarUnidade(uuid);
		unidade.setTitulo(dto.getTitulo());
		unidade = unidadeRepositorio.save(unidade);
		return mapper.map(unidade, UnidadeDeSoftwareDto.class);
	}

	@DeleteMapping("/unidade/{uuid}")
	public RemovidoDto remover(@PathVariable String uuid) throws RecursoNaoEncontradoException {
		UnidadeDeSoftware unidade = recuperarUnidade(uuid);
		unidadeRepositorio.delete(unidade.getId());
		return new RemovidoDto("Unidade de Software", uuid, "Unidade de Software removida com sucesso");
	}

	private Orcamento recuperarOrcamento(String uuid) {
		Optional<Orcamento> encontrado = orcamentoRepositorio.findByUuid(uuid);
		Orcamento orcamento = encontrado.orElseThrow(() -> new RecursoNaoEncontradoException("Orcamento"));
		return orcamento;
	}

	private UnidadeDeSoftware recuperarUnidade(String uuid) {
		Optional<UnidadeDeSoftware> encontrado = unidadeRepositorio.findByUuid(uuid);
		UnidadeDeSoftware unidade = encontrado
				.orElseThrow(() -> new RecursoNaoEncontradoException("Unidade de Software"));
		return unidade;
	}

}
