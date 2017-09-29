package br.com.leonardoz.quantocusta.seguranca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.leonardoz.quantocusta.entidade.Usuario;
import br.com.leonardoz.quantocusta.exceptions.UsuarioNaoConfirmadoException;
import br.com.leonardoz.quantocusta.repositorio.UsuariosRepository;
import br.com.leonardoz.quantocusta.seguranca.JwtUserFactory;
import br.com.leonardoz.quantocusta.util.Concatenador;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuariosRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String usernameOrLogin) throws UsernameNotFoundException {
		Usuario usuario = userRepository.findByEmailOrLogin(usernameOrLogin, usernameOrLogin)
				.orElseThrow(() -> new UsernameNotFoundException(
						Concatenador.concatenar("Nenhum usuário contrado com a informação: ", usernameOrLogin)));
		if (!usuario.isConfirmado()) {
			throw new UsuarioNaoConfirmadoException();
		}
		return JwtUserFactory.create(usuario);
	}
}
