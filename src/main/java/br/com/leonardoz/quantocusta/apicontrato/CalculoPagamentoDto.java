package br.com.leonardoz.quantocusta.apicontrato;

import java.math.BigDecimal;

public class CalculoPagamentoDto {

	private BigDecimal valor;
	private String representacao;
	
	public CalculoPagamentoDto() {
	}

	public CalculoPagamentoDto(BigDecimal valor, String representacao) {
		super();
		this.valor = valor;
		this.representacao = representacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getRepresentacao() {
		return representacao;
	}

	public void setRepresentacao(String representacao) {
		this.representacao = representacao;
	}
}
