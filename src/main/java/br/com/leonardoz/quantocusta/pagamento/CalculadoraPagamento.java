package br.com.leonardoz.quantocusta.pagamento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import br.com.leonardoz.quantocusta.exceptions.PagamentoInvalidoException;
import br.com.leonardoz.quantocusta.util.Concatenador;

public class CalculadoraPagamento {

	private int vezes;
	private BigDecimal entrada;
	private BigDecimal descontoAVistaUnitario;
	private BigDecimal jurosMensaisUnitario;
	private BigDecimal valor;
	private BigDecimal jurosMensaisPercentuais;
	private BigDecimal descontoAVistaPercentual;

	public List<FormatoPagamento> calcular(
			BigDecimal valor, 
			int vezes, BigDecimal entrada,
			BigDecimal descontoAVistaPercentual, 
			BigDecimal jurosMensaisPercentuais)
					throws PagamentoInvalidoException {
		
		verificarParametrosBasicos(valor, vezes);
		atribuirValores(valor, vezes, entrada, descontoAVistaPercentual, jurosMensaisPercentuais);
		return executar();
	}
	
	private void verificarParametrosBasicos(BigDecimal valor, int vezes) {
		avaliarSeEhValido(valor, "Valor");
		avaliarSeEhValido(vezes, "Número de vezes (parcelas)");
	}
	
	private void verificarParametrosParcelado(BigDecimal entrada, BigDecimal jurosMensaisPercentuais) {
		avaliarSeEhValido(jurosMensaisPercentuais, "Juros mensais percentuais");
		avaliarSeEhValido(entrada, "Entrada");
		avaliaEntrada(entrada, valor);
	}
	
	private void verificarParametrosAVista(BigDecimal descontoAVistaPercentual) {
		avaliarSeEhValido(descontoAVistaPercentual, "Desconto à vista percentual");
		avaliaDesconto(descontoAVistaPercentual, valor);
	}
	
	private void atribuirValores(BigDecimal valor, int vezes, BigDecimal entrada, BigDecimal descontoAVistaPercentual,
			BigDecimal jurosMensaisPercentuais) {
		this.valor = valor;
		this.vezes = vezes;
		this.entrada = entrada;
		this.descontoAVistaPercentual = descontoAVistaPercentual;
		this.jurosMensaisPercentuais = jurosMensaisPercentuais;
		this.jurosMensaisUnitario = adequarTaxas(jurosMensaisPercentuais, BigDecimal.ZERO);
		this.descontoAVistaUnitario = adequarTaxas(descontoAVistaPercentual, BigDecimal.ZERO);
	}

	private List<FormatoPagamento> executar() {
		boolean ehParcelado = vezes > 1;
		if (ehParcelado) {
			return calculaParcelado();
		} else {
			return Arrays.asList(calculaAVista());
		}
	}

	private void avaliaEntrada(BigDecimal entrada, BigDecimal valorTotal) {
		int comparado = entrada.compareTo(valorTotal);
		if (comparado >= 0) {
			String msg = Concatenador.concatenar("Entrada não deve ser igual ou maior que ",
					valorTotal.toString());
			throw new PagamentoInvalidoException(msg);
		}
	}

	private void avaliaDesconto(BigDecimal descontoAVistaPercentual, BigDecimal valorTotal) {
		int comparado = descontoAVistaPercentual.compareTo(BigDecimal.valueOf(100));
		if (comparado == 1) {
			String msg = Concatenador.concatenar("Desconto à vista percentual não deve ser maior que 100% ");
			throw new PagamentoInvalidoException(msg);
		}
	}
	
	private void avaliarSeEhValido(Integer valor, String campo) {
		if (valor == null)
			throw new PagamentoInvalidoException(Concatenador.concatenar(campo, " não deve ser nulo."));

		if (valor.compareTo(0) == -1)
			throw new PagamentoInvalidoException(Concatenador.concatenar(campo, " não deve ser um valor negativo."));
	}
	
	private void avaliarSeEhValido(BigDecimal valor, String campo) {
		if (valor == null)
			throw new PagamentoInvalidoException(Concatenador.concatenar(campo, " não deve ser nulo."));

		if (valor.compareTo(BigDecimal.ZERO) == -1)
			throw new PagamentoInvalidoException(Concatenador.concatenar(campo, " não deve ser um valor negativo."));
	}

	private BigDecimal adequarTaxas(BigDecimal taxaPercentual, BigDecimal fallback) {
		if (taxaPercentual == null || taxaPercentual.equals(BigDecimal.ZERO)) {
			taxaPercentual = fallback;
			return taxaPercentual;
		} else {
			BigDecimal cento = BigDecimal.valueOf(100);
			return taxaPercentual.divide(cento, 2, RoundingMode.CEILING);
		}
	}

	private List<FormatoPagamento> calculaParcelado() {
		verificarParametrosParcelado(entrada, jurosMensaisPercentuais);
		BigDecimal subTotal = valor.subtract(entrada);

		BigDecimal valorDeParcela = subTotal.divide(BigDecimal.valueOf(vezes), 2, RoundingMode.CEILING);
		// se aumento é de 1,75%, então multiplica valor por 1 + 0,0175
		BigDecimal fator = BigDecimal.ONE.add(jurosMensaisUnitario);
		BigDecimal valorDaParcelaComJuros = valorDeParcela.multiply(fator);
		
		List<FormatoPagamento> parcelas = IntStream
										.range(1, vezes + 1)
										.mapToObj(num -> new Parcela(num, valorDaParcelaComJuros, jurosMensaisPercentuais))
										.collect(Collectors.toList());
		return parcelas;
	}

	private FormatoPagamento calculaAVista() {
		verificarParametrosAVista(descontoAVistaPercentual);
		// se desconto é de 0,1, então 1 - 0,1 = 0,9; 0,9 * valor = valor descontado
		BigDecimal fator = BigDecimal.ONE.subtract(descontoAVistaUnitario);
		BigDecimal total = valor.multiply(fator);
		PagamentoUnico pagamentoUnico = new PagamentoUnico(total);
		return pagamentoUnico;
	}

}
