package br.com.leonardoz.quantocusta.repositorio;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.leonardoz.quantocusta.entidade.Orcamento;

public interface OrcamentosRepository extends PagingAndSortingRepository<Orcamento, Long>{

	Optional<Orcamento> findByUuid(String uuid);
	
}
