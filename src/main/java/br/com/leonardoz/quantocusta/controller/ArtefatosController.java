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

import br.com.leonardoz.quantocusta.contrato.ArtefatoDto;
import br.com.leonardoz.quantocusta.contrato.CriarAtualizarArtefatoDto;
import br.com.leonardoz.quantocusta.contrato.Removido;
import br.com.leonardoz.quantocusta.entidade.Artefato;
import br.com.leonardoz.quantocusta.entidade.UnidadeDeSoftware;
import br.com.leonardoz.quantocusta.exceptions.RecursoNaoEncontradoException;
import br.com.leonardoz.quantocusta.repositorio.ArtefatosRepository;
import br.com.leonardoz.quantocusta.repositorio.UnidadesDeSoftwareRepository;

@Transactional
@RestController
public class ArtefatosController {

	@Autowired
	private UnidadesDeSoftwareRepository unidadeRepositorio;

	@Autowired
	private ArtefatosRepository artefatoRepositorio;

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/artefatos/da/unidade/{unidadeUuid}")
	public List<ArtefatoDto> listar(@PathVariable String unidadeUuid) {
		UnidadeDeSoftware unidade = recuperarUnidadeDeSoftware(unidadeUuid);
		List<ArtefatoDto> unidades = artefatoRepositorio.findByUnidadeId(unidade.getId()).stream()
				.map(a -> mapper.map(a, ArtefatoDto.class)).collect(Collectors.toList());
		return unidades;
	}

	@GetMapping("/artefato/{uuid}")
	public ArtefatoDto retornar(@PathVariable String uuid) throws RecursoNaoEncontradoException {
		Artefato artefato = recuperarArtefato(uuid);
		return mapper.map(artefato, ArtefatoDto.class);
	}

	@PostMapping("/artefato/da/unidade/{unidadeUuid}")
	public ArtefatoDto salvar(@PathVariable String unidadeUuid, @RequestBody CriarAtualizarArtefatoDto dto)
			throws RecursoNaoEncontradoException {
		UnidadeDeSoftware unidade = recuperarUnidadeDeSoftware(unidadeUuid);
		Artefato artefato = mapper.map(dto, Artefato.class);
		artefato.setUnidade(unidade);
		Artefato salvo = artefatoRepositorio.save(artefato);
		return mapper.map(salvo, ArtefatoDto.class);
	}

	@PutMapping("/artefato/{uuid}")
	public ArtefatoDto atualizar(@PathVariable String uuid, @RequestBody CriarAtualizarArtefatoDto dto)
			throws RecursoNaoEncontradoException {

		Artefato artefato = recuperarArtefato(uuid);
		artefato.setCusto(dto.getCusto());
		artefato.setNome(dto.getNome());
		artefato = artefatoRepositorio.save(artefato);
		return mapper.map(artefato, ArtefatoDto.class);
	}

	@DeleteMapping("/artefato/{uuid}")
	public Removido remover(@PathVariable String uuid) throws RecursoNaoEncontradoException {
		Artefato artefato = recuperarArtefato(uuid);
		artefatoRepositorio.delete(artefato.getId());
		return new Removido("Unidade de Software", uuid, "Unidade de Software removida com sucesso");
	}

	private UnidadeDeSoftware recuperarUnidadeDeSoftware(String uuid) {
		Optional<UnidadeDeSoftware> encontrado = unidadeRepositorio.findByUuid(uuid);
		UnidadeDeSoftware unidade = encontrado
				.orElseThrow(() -> new RecursoNaoEncontradoException("Unidade de Software"));
		return unidade;
	}

	private Artefato recuperarArtefato(String uuid) {
		Optional<Artefato> encontrado = artefatoRepositorio.findByUuid(uuid);
		Artefato artefato = encontrado.orElseThrow(() -> new RecursoNaoEncontradoException("Artefato"));
		return artefato;
	}

}
