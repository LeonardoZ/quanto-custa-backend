package br.com.leonardoz.quantocusta.contrato;

import java.math.BigDecimal;

public class CriarAtualizarArtefatoDto {

	private String nome;
	private BigDecimal custo;
	
	public CriarAtualizarArtefatoDto() {

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

}
