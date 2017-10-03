package br.com.leonardoz.quantocusta.apicontrato;

public class ErroDto {

	private String mensagem;
	private String uri;
	private int status;

	public ErroDto(String mensagem, String uri, int status) {
		super();
		this.mensagem = mensagem;
		this.uri = uri;
		this.status = status;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
