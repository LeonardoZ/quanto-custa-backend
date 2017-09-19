package br.com.leonardoz.quantocusta.contrato;

public class RemovidoDto {

	private String recurso;
	private String uuid;
	private String mensagem;

	public RemovidoDto(String recurso, String uuid, String mensagem) {
		super();
		this.recurso = recurso;
		this.uuid = uuid;
		this.mensagem = mensagem;
	}

	public String getRecurso() {
		return recurso;
	}

	public void setRecurso(String recurso) {
		this.recurso = recurso;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
