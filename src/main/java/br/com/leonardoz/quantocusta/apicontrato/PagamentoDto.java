package br.com.leonardoz.quantocusta.apicontrato;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDto {

	private String orcamentoUuid;
	private int pagamentoEmVezes;
	private BigDecimal entrada;
	private BigDecimal jurosMensaisPercentuais;
	private BigDecimal descontoAVistaPercentual;
	private boolean ehParcelado;
	private List<CalculoPagamentoDto> calculoPagamento;
	
	public PagamentoDto() {
		calculoPagamento = new ArrayList<>();
	}

	public String getOrcamentoUuid() {
		return orcamentoUuid;
	}

	public void setOrcamentoUuid(String orcamentoUuid) {
		this.orcamentoUuid = orcamentoUuid;
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
	

	public List<CalculoPagamentoDto> getCalculoPagamento() {
		return calculoPagamento;
	}

	public void setCalculoPagamento(List<CalculoPagamentoDto> calculoPagamento) {
		this.calculoPagamento = calculoPagamento;
	}
	

	public boolean isEhParcelado() {
		return ehParcelado;
	}

	public void setEhParcelado(boolean ehParcelado) {
		this.ehParcelado = ehParcelado;
	}

	@Override
	public String toString() {
		return "PagamentoDto [orcamentoId=" + orcamentoUuid + ", pagamentoEmVezes=" + pagamentoEmVezes + ", jurosMensais="
				+ jurosMensaisPercentuais + ", entrada=" + entrada + ", descontoAVista=" + descontoAVistaPercentual + "]";
	}
	
	

}
