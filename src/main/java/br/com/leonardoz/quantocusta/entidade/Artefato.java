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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Leonardo H. Zapparoli 2017-07-07
 */
@Entity
@Table(name = "artefatos")
public class Artefato extends Entidade {

	private static final long serialVersionUID = 1L;

	@Column(name = "nome", length = 150, nullable = false)
	private String nome;

	@Column(name = "custo", precision = 8, scale = 2)
	private BigDecimal custo;

	@ManyToOne
	@JoinColumn(name = "unidade")
	private UnidadeDeSoftware unidade;

	public Artefato() {
	}

	public Artefato(String nome, double custo, UnidadeDeSoftware unidadePertencente) {
		super();
		this.nome = nome;
		this.custo = BigDecimal.valueOf(custo);
		configuraCusto();
		this.unidade = unidadePertencente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getCusto() {
		return custo;
	}

	public void setCusto(BigDecimal custo) {
		this.custo = custo;
		configuraCusto();
	}

	public UnidadeDeSoftware getUnidade() {
		return unidade;
	}

	public void setUnidade(UnidadeDeSoftware unidade) {
		this.unidade = unidade;
	}

	private void configuraCusto() {
		this.custo = this.custo.setScale(2, RoundingMode.CEILING);
	}

}
