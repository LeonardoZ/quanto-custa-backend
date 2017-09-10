package br.com.leonardoz.quantocusta.contrato;

public class CriarAtualizarUnidadeDeSoftwareDto {

	private String titulo;

	public CriarAtualizarUnidadeDeSoftwareDto() {
	}

	public CriarAtualizarUnidadeDeSoftwareDto(String titulo) {
		super();
		this.titulo = titulo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
