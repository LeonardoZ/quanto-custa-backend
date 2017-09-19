package br.com.leonardoz.quantocusta.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.leonardoz.quantocusta.entidade.Usuario;

@Transactional
public interface UsuariosRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByEmailOrLogin(String email, String login);

	Optional<Usuario> findByEmail(String email);

	Optional<Usuario> findByLogin(String login);

}
