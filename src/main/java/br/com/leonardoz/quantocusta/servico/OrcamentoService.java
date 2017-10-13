package br.com.leonardoz.quantocusta.servico;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.leonardoz.quantocusta.entidade.Orcamento;
import br.com.leonardoz.quantocusta.exceptions.RecursoNaoEncontradoException;
import br.com.leonardoz.quantocusta.repositorio.OrcamentosRepository;

@Service
public class OrcamentoService {

	@Autowired
	private OrcamentosRepository orcamentoRepository;
	
	public Orcamento recuperaOrcamento(String uuid) throws RecursoNaoEncontradoException {
		return orcamentoRepository
						.findByUuid(uuid)
						.orElseThrow(() -> new RecursoNaoEncontradoException("Orcamento"));
	}
	
	@Async
	public CompletableFuture<Integer> recuperaQuantidadeArtefatos(Orcamento orcamento) {
		int contaArtefatos = orcamentoRepository.contaArtefatos(orcamento.getId());
		return CompletableFuture.completedFuture(contaArtefatos);
	}
	
	@Async
	public CompletableFuture<Integer> recuperaQuantidadeUnidades(Orcamento orcamento) {
		int contaUnidades = orcamentoRepository.contaUnidades(orcamento.getId());
		return CompletableFuture.completedFuture(contaUnidades);
	}
	
	@Async
	public CompletableFuture<Double> calcularSomaTotal(Orcamento orcamento) {
		double somatorio = orcamentoRepository.somaValorTotal(orcamento.getId());
		return CompletableFuture.completedFuture(somatorio);
	}
	
}
