package br.com.leonardoz.quantocusta.pagamento;

import java.math.BigDecimal;

public interface FormatoPagamento {

	BigDecimal getValor();	
	
	String getRepresentacao();
	
}
