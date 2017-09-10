package br.com.leonardoz.quantocusta.contrato;

public class CriarOrcamentoDto {

	private String nome;
	private String cliente;
	private String responsavel;

	public CriarOrcamentoDto() {

	}
	
	public CriarOrcamentoDto(String nome, String cliente, String responsavel) {
		super();
		this.nome = nome;
		this.cliente = cliente;
		this.responsavel = responsavel;
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

}
