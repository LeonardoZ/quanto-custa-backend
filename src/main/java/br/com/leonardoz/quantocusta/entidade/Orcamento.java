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
import java.time.LocalDateTime;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

/**
 * @author Leonardo H. Zapparoli 2017-07-07
 */
@Entity
@Table(name = "orcamentos")
public class Orcamento extends Entidade {

	private static final long serialVersionUID = 1L;

	@Column(name = "nome", length = 120)
	private String nome;

	@Length(min = 3, max = 120)
	@Column(name = "cliente", length = 120)
	private String cliente;

	@Column(name = "criado_em")
	private LocalDateTime criadoEm;

	@Column(name = "valido_ate")
	private LocalDateTime validoAte;

	@Column(name = "responsavel", length = 120)
	private String responsavel;

	@OneToMany(mappedBy = "orcamento", cascade = CascadeType.ALL)
	private List<UnidadeDeSoftware> unidades;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "orcamento", cascade = CascadeType.ALL)
	private Pagamento pagamento;

	public Orcamento() {
		valoresIniciais();
	}

	public Orcamento(String nome, String cliente, LocalDateTime criadoEm, LocalDateTime validoAte, String responsavel) {
		super();
		this.nome = nome;
		this.cliente = cliente;
		this.criadoEm = criadoEm;
		this.validoAte = validoAte;
		this.responsavel = responsavel;
		this.unidades = new LinkedList<>();
	}

	private void valoresIniciais() {
		this.criadoEm = LocalDateTime.now();
		this.validoAte = this.criadoEm.plus(Period.ofMonths(1));
	}

	public void adiciona(UnidadeDeSoftware unidade) {
		this.unidades.add(unidade);
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(LocalDateTime criadoEm) {
		this.criadoEm = criadoEm;
	}

	public LocalDateTime getValidoAte() {
		return validoAte;
	}

	public void setValidoAte(LocalDateTime validoAte) {
		this.validoAte = validoAte;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public List<UnidadeDeSoftware> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<UnidadeDeSoftware> unidades) {
		this.unidades = unidades;
	}

	public BigDecimal calcularValorTotal() {
		return unidades.stream().flatMap(u -> u.getArtefatos().stream()).map(Artefato::getCusto)
				.reduce((a, b) -> a.add(b)).get();
	}

}
