package br.com.leonardoz.quantocusta.repositorio;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.leonardoz.quantocusta.entidade.Orcamento;

public interface OrcamentosRepository extends PagingAndSortingRepository<Orcamento, Long> {

	Page<Orcamento> findByUsuarioId(Long id, Pageable pageable);

	Optional<Orcamento> findByUuid(String uuid);

	int contaUnidades(long idOrcamento);

	int contaArtefatos(long idOrcamento);

	double somaValorTotal(long idOrcamento);
}
