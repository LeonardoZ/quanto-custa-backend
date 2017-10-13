package br.com.leonardoz.quantocusta.pagamento;

import java.math.BigDecimal;

import br.com.leonardoz.quantocusta.util.Concatenador;

public class Parcela implements FormatoPagamento {

	private BigDecimal taxaDeJurosPercentualMensal;
	private BigDecimal valor;
	private int indice;

	public Parcela(int indice, BigDecimal valor, BigDecimal taxaDeJuros) {
		super();
		this.indice = indice;
		this.taxaDeJurosPercentualMensal = taxaDeJuros;
		this.valor = valor;
	}

	public BigDecimal getTaxaDeJurosPercentualMensal() {
		return taxaDeJurosPercentualMensal;
	}

	public void setTaxaDeJurosPercentualMensal(BigDecimal taxaDeJurosPercentualMensal) {
		this.taxaDeJurosPercentualMensal = taxaDeJurosPercentualMensal;
	}

	@Override
	public BigDecimal getValor() {
		return valor;
	}

	@Override
	public String getRepresentacao() {
		String valorFormatado = FormataDecimal.formatarDinheiro(valor);
		String taxaFormatada = FormataDecimal.formatarPercentual(taxaDeJurosPercentualMensal);
		String resultado = Concatenador.concatenar(valorFormatado, " (", taxaFormatada, " a. m.)");
		return resultado;
	}

	public int getIndice() {
		return indice;
	}

}
