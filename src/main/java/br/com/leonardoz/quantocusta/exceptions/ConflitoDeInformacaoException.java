package br.com.leonardoz.quantocusta.exceptions;

public class ConflitoDeInformacaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String tipoDeRecurso;
	private String campo;

	public ConflitoDeInformacaoException(String tipoDeRecurso, String campo) {
		this.tipoDeRecurso = tipoDeRecurso;
		this.campo = campo;
	}

	public String getTipoDeRecurso() {
		return tipoDeRecurso;
	}

	public void setTipoDeRecurso(String tipoDeRecurso) {
		this.tipoDeRecurso = tipoDeRecurso;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

}
