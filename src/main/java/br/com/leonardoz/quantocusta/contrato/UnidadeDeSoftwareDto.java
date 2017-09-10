package br.com.leonardoz.quantocusta.contrato;

public class UnidadeDeSoftwareDto {

	private String uuid;
	private String titulo;
	private String orcamentoUuid;

	public UnidadeDeSoftwareDto() {
	}

	public UnidadeDeSoftwareDto(String uuid, String titulo, String orcamentoUuid) {
		super();
		this.uuid = uuid;
		this.titulo = titulo;
		this.orcamentoUuid = orcamentoUuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getOrcamentoUuid() {
		return orcamentoUuid;
	}

	public void setOrcamentoUuid(String orcamentoUuid) {
		this.orcamentoUuid = orcamentoUuid;
	}
}
