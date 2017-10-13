package br.com.leonardoz.quantocusta.apicontrato;

public class PagamentDto {

	private String orcamentoUuid;
	private int pagamentoEmVezes;
	private double jurosMensais;
	private double entrada;
	private double descontoAVista;

	public PagamentDto() {

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

}
