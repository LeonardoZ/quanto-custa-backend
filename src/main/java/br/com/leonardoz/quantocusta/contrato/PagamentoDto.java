package br.com.leonardoz.quantocusta.contrato;

public class PagamentoDto {

	private int orcamentoId;
	private int pagamentoEmVezes;
	private double jurosMensais;
	private double entrada;
	private double descontoAVista;
	
	public PagamentoDto() {

	}

	public int getOrcamentoId() {
		return orcamentoId;
	}

	public void setOrcamentoId(int orcamentoId) {
		this.orcamentoId = orcamentoId;
	}

	public int getPagamentoEmVezes() {
		return pagamentoEmVezes;
	}

	public void setPagamentoEmVezes(int pagamentoEmVezes) {
		this.pagamentoEmVezes = pagamentoEmVezes;
	}

	public double getJurosMensais() {
		return jurosMensais;
	}

	public void setJurosMensais(double jurosMensais) {
		this.jurosMensais = jurosMensais;
	}

	public double getEntrada() {
		return entrada;
	}

	public void setEntrada(double entrada) {
		this.entrada = entrada;
	}

	public double getDescontoAVista() {
		return descontoAVista;
	}

	public void setDescontoAVista(double descontoAVista) {
		this.descontoAVista = descontoAVista;
	}

	@Override
	public String toString() {
		return "PagamentoDto [orcamentoId=" + orcamentoId + ", pagamentoEmVezes=" + pagamentoEmVezes + ", jurosMensais="
				+ jurosMensais + ", entrada=" + entrada + ", descontoAVista=" + descontoAVista + "]";
	}
	
	

}
