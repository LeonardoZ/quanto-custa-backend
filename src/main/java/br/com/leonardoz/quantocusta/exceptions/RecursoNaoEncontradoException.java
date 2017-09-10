package br.com.leonardoz.quantocusta.exceptions;

public class RecursoNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String tipoDeRecurso;
	
	public RecursoNaoEncontradoException(String tipoDeRecurso) {
		this.tipoDeRecurso = tipoDeRecurso;
	}
	
	public String getTipoDeRecurso() {
		return tipoDeRecurso;
	}

}
