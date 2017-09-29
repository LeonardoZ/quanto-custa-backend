package br.com.leonardoz.quantocusta.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.leonardoz.quantocusta.entidade.Usuario;

@Transactional
public interface UsuariosRepository extends JpaRepository<Usuario, Long> {
	
	@Query(name = "SELECT u FROM Usuario u WHERE (u.email = ?1 OR u.login = %2) AND confirmado = 1")
	Optional<Usuario> findByEmailOrLogin(String email, String login);

	Optional<Usuario> findByEmail(String email);

	Optional<Usuario> findByLogin(String login);

	Optional<Usuario> findByUuid(String uuid);

}
