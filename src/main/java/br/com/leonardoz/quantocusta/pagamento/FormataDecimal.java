package br.com.leonardoz.quantocusta.pagamento;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public class FormataDecimal {
	
	private static final NumberFormat formatadorDinheiro = new DecimalFormat("'R$' #,##0.00",
			new DecimalFormatSymbols(new Locale("pt", "BR")));
	private static final DecimalFormat formatadorPercentual = new DecimalFormat(" #,##0.00'%'");

	public static String formatarDinheiro(BigDecimal valor) {
		return formatadorDinheiro.format(valor.doubleValue());
	}
	
	public static String formatarPercentual(BigDecimal valor) {
		return formatadorPercentual.format(valor.doubleValue());
	}

}
