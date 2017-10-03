package br.com.leonardoz.quantocusta.apicontrato;


public class OrcamentoDto {
	
	private String uuid;
	private String nome;
	private String cliente;
	private String responsavel;
	private String criadoEm;
	private String validoAte;
	private String usuarioUuid;

	public OrcamentoDto() {
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(String criadoEm) {
		this.criadoEm = criadoEm;
	}

	public String getValidoAte() {
		return validoAte;
	}

	public void setValidoAte(String validoAte) {
		this.validoAte = validoAte;
	}
	

	public String getUsuarioUuid() {
		return usuarioUuid;
	}

	public void setUsuarioUuid(String usuarioUuid) {
		this.usuarioUuid = usuarioUuid;
	}

	@Override
	public String toString() {
		return new StringBuilder().append("Nome: ").append(nome).append(" - ").append("Cliente: ").append(cliente)
				.append(" - ").append("Responsável: ").append(" - ").append(responsavel).append(" - ")
				.append("Criado em: ").append(criadoEm).append(" - ").append("Válido até: ").append(validoAte)
				.toString();
	}

}
