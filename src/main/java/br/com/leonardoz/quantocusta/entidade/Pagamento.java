/**
MIT License

Copyright (c) [2017] [Leonardo Henrique Zapparoli]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package br.com.leonardoz.quantocusta.entidade;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.leonardoz.quantocusta.pagamento.CalculadoraPagamento;
import br.com.leonardoz.quantocusta.pagamento.FormatoPagamento;

/**
 * @author Leonardo H. Zapparoli 2017-07-07
 */
@Entity
@Table(name = "pagamentos")
public class Pagamento {

	@Id
	@Column(name = "orcamento_id")
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumn(name = "orcamento_id", referencedColumnName = "orcamento_id")
	private Orcamento orcamento;

	@Column(name = "vezes", nullable = false)
	private int pagamentoEmVezes = 1;

	@Column(name = "juros_mensais", nullable = false, scale = 3, precision = 7)
	private BigDecimal jurosMensaisPercentuais = BigDecimal.ZERO;

	@Column(name = "entrada", nullable = false, scale = 2, precision = 7)
	private BigDecimal entrada = BigDecimal.ZERO;

	@Column(name = "desconto_a_vista", nullable = false, scale = 2, precision = 7)
	private BigDecimal descontoAVistaPercentual = BigDecimal.ZERO;

	@Transient
	private CalculadoraPagamento calculadora;

	public Pagamento() {
		calculadora = new CalculadoraPagamento();
	}

	public Pagamento(int pagamentoEmVezes, BigDecimal jurosMensais, BigDecimal entrada, BigDecimal descontoAVista,
			Orcamento orcamento) {
		super();
		this.pagamentoEmVezes = pagamentoEmVezes;
		this.jurosMensaisPercentuais = jurosMensais;
		this.entrada = entrada;
		this.descontoAVistaPercentual = descontoAVista;
		this.orcamento = orcamento;
	}

	public int getPagamentoEmVezes() {
		return pagamentoEmVezes;
	}

	public void setPagamentoEmVezes(int pagamentoEmVezes) {
		this.pagamentoEmVezes = pagamentoEmVezes;
	}

	public BigDecimal getDescontoAVistaPercentual() {
		return descontoAVistaPercentual;
	}

	public void setDescontoAVistaPercentual(BigDecimal descontoAVista) {
		this.descontoAVistaPercentual = descontoAVista;
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

	public Orcamento getOrcamento() {
		return orcamento;
	}
	
	public boolean ehParcelado() {
		return pagamentoEmVezes > 1;
	}
	
	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
		this.id = orcamento.getId();
	}

	public List<FormatoPagamento> formatoPagamento() {
		if (ehParcelado()) {
			return parcelado();
		} else {
			return Arrays.asList(valorAVista());
		}
	}

	public FormatoPagamento valorAVista() {
		if (ehParcelado())
			throw new IllegalArgumentException("Valor à vista não pode ter mais de 1 parcela.");
		return calculadora
				.calcular(orcamento.calcularValorTotal(), 1, entrada, descontoAVistaPercentual, BigDecimal.ZERO)
				.get(0);
	}

	public List<FormatoPagamento> parcelado() {
		if (pagamentoEmVezes < 2)
			throw new IllegalArgumentException("Valor parcelado deve possuír mais de 1 parcela.");
		return calculadora.calcular(orcamento.calcularValorTotal(), pagamentoEmVezes, BigDecimal.ZERO, BigDecimal.ZERO,
				jurosMensaisPercentuais);
	}

}
