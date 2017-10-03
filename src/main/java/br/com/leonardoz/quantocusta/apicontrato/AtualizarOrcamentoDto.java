package br.com.leonardoz.quantocusta.apicontrato;

import java.time.LocalDateTime;

public class AtualizarOrcamentoDto {

	private String nome;
	private String cliente;
	private String responsavel;
	private LocalDateTime validoAte;

	public AtualizarOrcamentoDto() {

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

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}


	public LocalDateTime getValidoAte() {
		return validoAte;
	}


	public void setValidoAte(LocalDateTime validoAte) {
		this.validoAte = validoAte;
	}
	
	

	
	
}
