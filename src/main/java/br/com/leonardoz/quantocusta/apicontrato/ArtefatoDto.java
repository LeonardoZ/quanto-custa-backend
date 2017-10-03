package br.com.leonardoz.quantocusta.apicontrato;

import java.math.BigDecimal;

public class ArtefatoDto {

	private String uuid;
	private String nome;
	private BigDecimal custo;
	private String unidadeUuid;

	public ArtefatoDto() {
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getCusto() {
		return custo;
	}

	public void setCusto(BigDecimal custo) {
		this.custo = custo;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUnidadeUuid() {
		return unidadeUuid;
	}

	public void setUnidadeUuid(String unidadeUuid) {
		this.unidadeUuid = unidadeUuid;
	}

}
