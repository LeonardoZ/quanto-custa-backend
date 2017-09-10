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
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Leonardo H. Zapparoli 2017-07-07
 */
@Entity
@Table(name = "pagamentos")
public class Pagamento {

	@Id
	private Long id;

	@MapsId
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Orcamento orcamento;

	@Column(name = "vezes", nullable = false)
	private int pagamentoEmVezes;

	@Column(name = "juros_mensais", nullable = false, scale = 3, precision = 7)
	private double jurosMensais;

	@Column(name = "entrada", nullable = false, scale = 2, precision = 7)
	private double entrada;

	@Column(name = "desconto_a_vista", nullable = false, scale = 2, precision = 7)
	private double descontoAVista;

	@Column(name = "uuid", unique = false, length = 32)
	private String uuid;

	public Pagamento() {
	}

	public Pagamento(int pagamentoEmVezes, double jurosMensais, double entrada, double descontoAVista,
			Orcamento orcamento) {
		super();
		this.pagamentoEmVezes = pagamentoEmVezes;
		this.jurosMensais = jurosMensais;
		this.entrada = entrada;
		this.descontoAVista = descontoAVista;
		this.orcamento = orcamento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPagamentoEmVezes() {
		return pagamentoEmVezes;
	}

	public void setPagamentoEmVezes(int pagamentoEmVezes) {
		this.pagamentoEmVezes = pagamentoEmVezes;
	}

	public double getDescontoAVista() {
		return descontoAVista;
	}

	public void setDescontoAVista(double descontoAVista) {
		this.descontoAVista = descontoAVista;
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

	public Orcamento getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Orcamento orcamento) {
		this.orcamento = orcamento;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public BigDecimal valorAVista() {
		BigDecimal valorTotal = orcamento.calcularValorTotal();
		BigDecimal descontoPercentual = BigDecimal.valueOf(descontoAVista).multiply(valorTotal);
		return valorTotal.subtract(descontoPercentual);
	}

	public List<BigDecimal> parcelado() {
		BigDecimal valorTotal = orcamento.calcularValorTotal();
		BigDecimal valorDescontado = valorTotal.subtract(BigDecimal.valueOf(entrada));
		List<BigDecimal> parcelas = new ArrayList<>();
		BigDecimal juros = jurosMensais == 0 ? null : BigDecimal.valueOf(1 + jurosMensais);
		for (int i = 0; i < pagamentoEmVezes; i++) {
			BigDecimal parcela = valorDescontado.divide(BigDecimal.valueOf(pagamentoEmVezes), RoundingMode.CEILING);
			if (juros != null) {
				parcela = parcela.multiply(juros);
			}
			parcelas.add(parcela);
		}
		return parcelas;
	}

}
