package br.com.leonardoz.quantocusta.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario extends Entidade {

	private static final long serialVersionUID = 1L;
	@Column(name = "login", length = 120, nullable = false, unique = true)
	private String login;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "senha", nullable = false)
	private String senha;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "perfil", length = 25, nullable = false)
	private Perfil perfil;
	
	public Usuario() {
		this.perfil = Perfil.ROLE_USUARIO;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

}
