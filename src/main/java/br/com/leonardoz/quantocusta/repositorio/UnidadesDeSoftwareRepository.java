package br.com.leonardoz.quantocusta.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.leonardoz.quantocusta.entidade.UnidadeDeSoftware;

public interface UnidadesDeSoftwareRepository extends PagingAndSortingRepository<UnidadeDeSoftware, Long>{

	public List<UnidadeDeSoftware> findByOrcamentoId(Long orcamentoId);

	public Optional<UnidadeDeSoftware> findByUuid(String uuid);
	
}
