package br.com.leonardoz.quantocusta.apicontrato;

import java.math.BigDecimal;

public class CriarAtualizarPagamentDto {

	private int pagamentoEmVezes;
	private BigDecimal entrada;
	private BigDecimal jurosMensaisPercentuais;
	private BigDecimal descontoAVistaPercentual;

	public CriarAtualizarPagamentDto() {
	}

	public int getPagamentoEmVezes() {
		return pagamentoEmVezes;
	}

	public void setPagamentoEmVezes(int pagamentoEmVezes) {
		this.pagamentoEmVezes = pagamentoEmVezes;
	}

	public BigDecimal getJurosMensaisPercentuais() {
		return jurosMensaisPercentuais;
	}

	public void setJurosMensaisPercentuais(BigDecimal jurosMensais) {
		this.jurosMensaisPercentuais = jurosMensais;
	}

	public BigDecimal getEntrada() {
		return entrada;
	}

	public void setEntrada(BigDecimal entrada) {
		this.entrada = entrada;
	}

	public BigDecimal getDescontoAVistaPercentual() {
		return descontoAVistaPercentual;
	}

	public void setDescontoAVistaPercentual(BigDecimal descontoAVista) {
		this.descontoAVistaPercentual = descontoAVista;
	}

	@Override
	public String toString() {
		return "CriarAtualizarPagamentDto [pagamentoEmVezes=" + pagamentoEmVezes + ", entrada=" + entrada
				+ ", jurosMensaisPercentuais=" + jurosMensaisPercentuais + ", descontoAVistaPercentual="
				+ descontoAVistaPercentual + "]";
	}
	
	

}
