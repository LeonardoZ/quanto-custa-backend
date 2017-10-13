package br.com.leonardoz.quantocusta.exceptions;

public class PagamentoInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PagamentoInvalidoException(String mensagem) {
		super(mensagem);
	}

}
