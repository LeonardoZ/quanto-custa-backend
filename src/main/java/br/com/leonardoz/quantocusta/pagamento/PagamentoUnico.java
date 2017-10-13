package br.com.leonardoz.quantocusta.pagamento;

import java.math.BigDecimal;

public class PagamentoUnico implements FormatoPagamento {

	private BigDecimal valor;
	
	public PagamentoUnico(BigDecimal valor) {
		super();
		this.valor = valor;
	}

	@Override
	public BigDecimal getValor() {
		return valor;
	}

	@Override
	public String getRepresentacao() {
		return FormataDecimal.formatarDinheiro(valor);
	}

}
