package br.com.leonardoz.quantocusta.exceptions;

public class UsuarioNaoConfirmadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UsuarioNaoConfirmadoException() {
		super("Usuário ainda não confirmado/ativado");
	}

}
