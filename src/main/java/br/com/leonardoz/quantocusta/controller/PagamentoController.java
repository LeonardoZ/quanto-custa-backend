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

import br.com.leonardoz.quantocusta.apicontrato.CalculoPagamentoDto;
import br.com.leonardoz.quantocusta.apicontrato.CriarAtualizarPagamentDto;
import br.com.leonardoz.quantocusta.apicontrato.PagamentoDto;
import br.com.leonardoz.quantocusta.apicontrato.RemovidoDto;
import br.com.leonardoz.quantocusta.entidade.Orcamento;
import br.com.leonardoz.quantocusta.entidade.Pagamento;
import br.com.leonardoz.quantocusta.exceptions.RecursoNaoEncontradoException;
import br.com.leonardoz.quantocusta.pagamento.CalculadoraPagamento;
import br.com.leonardoz.quantocusta.repositorio.OrcamentosRepository;
import br.com.leonardoz.quantocusta.repositorio.PagamentosRepository;

@Transactional
@RestController
public class PagamentoController {

	@Autowired
	private PagamentosRepository pagamentoRepositorio;

	@Autowired
	private OrcamentosRepository orcamentoRepositorio;

	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/orcamento/{orcamentoUuid}/pagamento")
	public PagamentoDto retornar(@PathVariable String orcamentoUuid) throws RecursoNaoEncontradoException {
		Orcamento orcamento = recuperarOrcamento(orcamentoUuid);
		Pagamento pagamento = recuperarPagamento(orcamento.getId());
		PagamentoDto dto = mapper.map(pagamento, PagamentoDto.class);
		calcularPagamento(pagamento, dto);
		dto.setEhParcelado(pagamento.ehParcelado());
		return dto;
	}

	private void calcularPagamento(Pagamento pagamento, PagamentoDto dto) {
		dto.setCalculoPagamento(
				pagamento.formatoPagamento()
					.stream()
					.map(f -> mapper.map(f, CalculoPagamentoDto.class))
					.collect(Collectors.toList())
				);
	}

	@PostMapping("/orcamento/{orcamentoUuid}/pagamento")
	public PagamentoDto salvar(@PathVariable String orcamentoUuid, @RequestBody CriarAtualizarPagamentDto dto)
			throws RecursoNaoEncontradoException {
		Orcamento orcamento = recuperarOrcamento(orcamentoUuid);
		Pagamento pagamento = mapper.map(dto, Pagamento.class);
		pagamento.setOrcamento(orcamento);
		Pagamento salvo = pagamentoRepositorio.save(pagamento);
		PagamentoDto dtoSalvo = mapper.map(salvo, PagamentoDto.class);
		calcularPagamento(pagamento, dtoSalvo);
		return dtoSalvo;
	}

	@PutMapping("/orcamento/{orcamentoUuid}/pagamento")
	public PagamentoDto atualizar(@PathVariable String orcamentoUuid, @RequestBody CriarAtualizarPagamentDto dto)
			throws RecursoNaoEncontradoException {
		Orcamento orcamento = recuperarOrcamento(orcamentoUuid);
		Pagamento pagamento = recuperarPagamento(orcamento.getId());
		
		pagamento.setDescontoAVistaPercentual(dto.getDescontoAVistaPercentual());
		pagamento.setEntrada(dto.getEntrada());
		pagamento.setJurosMensaisPercentuais(dto.getJurosMensaisPercentuais());
		pagamento.setPagamentoEmVezes(dto.getPagamentoEmVezes());
		pagamento.setOrcamento(orcamento);
		orcamento.setPagamento(pagamento);
		pagamento = pagamentoRepositorio.saveAndFlush(pagamento);
		PagamentoDto dtoAtualizado = mapper.map(pagamento, PagamentoDto.class);
		calcularPagamento(pagamento, dtoAtualizado);
		return dtoAtualizado;
	}

	@DeleteMapping("/orcamento/{orcamentoUuid}/pagamento")
	public RemovidoDto remover(@PathVariable String orcamentoUuid) throws RecursoNaoEncontradoException {
		Orcamento orcamento = recuperarOrcamento(orcamentoUuid);
		Pagamento pagamento = recuperarPagamento(orcamento.getId());
		orcamento.setPagamento(null);
		pagamentoRepositorio.delete(pagamento);
		orcamentoRepositorio.save(orcamento);
		return new RemovidoDto("Pagamento", orcamentoUuid, "Pagamento removido com sucesso");
	}

	@PostMapping("/orcamento/{orcamentoUuid}/pagamento/calcula")
	public List<CalculoPagamentoDto> calcularPagamento(@PathVariable String orcamentoUuid,
			@RequestBody CriarAtualizarPagamentDto dto) {
		Orcamento orcamento = recuperarOrcamento(orcamentoUuid);
		CalculadoraPagamento calculadora = new CalculadoraPagamento();
		List<CalculoPagamentoDto> dtos = calculadora
				.calcular(orcamento.calcularValorTotal(), 
						  dto.getPagamentoEmVezes(), 
						  dto.getEntrada(),
						  dto.getDescontoAVistaPercentual(), 
						  dto.getJurosMensaisPercentuais())
				.stream()
				.map(f -> mapper.map(f, CalculoPagamentoDto.class))
				.collect(Collectors.toList());
		return dtos;
	}

	private Orcamento recuperarOrcamento(String uuid) {
		Optional<Orcamento> encontrado = orcamentoRepositorio.findByUuid(uuid);
		Orcamento orcamento = encontrado.orElseThrow(() -> new RecursoNaoEncontradoException("Orcamento"));
		return orcamento;
	}

	private Pagamento recuperarPagamento(Long orcamentoId) {
		Optional<Pagamento> encontrado = pagamentoRepositorio.findByOrcamentoId(orcamentoId);
		Pagamento pagamento = encontrado.orElseThrow(() -> new RecursoNaoEncontradoException("Pagamento"));
		return pagamento;
	}

}
