package br.com.leonardoz.quantocusta.seguranca;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.leonardoz.quantocusta.entidade.Perfil;
import br.com.leonardoz.quantocusta.entidade.Usuario;

public final class JwtUserFactory {

	private JwtUserFactory() {
	}

	public static JwtUser create(Usuario user) {
		return new JwtUser(user.getUuid(), user.getLogin(), user.getSenha(), user.getEmail(),
				mapToGrantedAuthorities(user.getPerfil()));
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(Perfil perfil) {
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority(perfil.name());
		List<GrantedAuthority> list = new ArrayList<>();
		list.add(authority);
		return list;
	}
}
