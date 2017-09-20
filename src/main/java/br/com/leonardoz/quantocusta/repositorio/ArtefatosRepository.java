package br.com.leonardoz.quantocusta.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.leonardoz.quantocusta.entidade.Artefato;

public interface ArtefatosRepository extends PagingAndSortingRepository<Artefato, Long> {

	List<Artefato> findByUnidadeId(Long id);
	
	Optional<Artefato> findByUuid(String uuid);
	
	
	
}
