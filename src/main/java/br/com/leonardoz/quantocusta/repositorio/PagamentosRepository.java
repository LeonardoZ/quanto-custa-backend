package br.com.leonardoz.quantocusta.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.leonardoz.quantocusta.entidade.Pagamento;

public interface PagamentosRepository extends JpaRepository<Pagamento, Long> {

	Optional<Pagamento> findByOrcamentoId(Long orcamentoId);
	
}
