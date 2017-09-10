package br.com.leonardoz.quantocusta.repositorio;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.leonardoz.quantocusta.entidade.Pagamento;

public interface PagamentosRepository extends PagingAndSortingRepository<Pagamento, Long> {

}
