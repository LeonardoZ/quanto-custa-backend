package br.com.leonardoz.quantocusta.controller;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.leonardoz.quantocusta.apicontrato.CriarUsuarioDto;
import br.com.leonardoz.quantocusta.apicontrato.EmailDto;
import br.com.leonardoz.quantocusta.apicontrato.ErroDto;
import br.com.leonardoz.quantocusta.apicontrato.AlterarSenhaDto;
import br.com.leonardoz.quantocusta.apicontrato.ValidarEmailDto;
import br.com.leonardoz.quantocusta.email.MailService;
import br.com.leonardoz.quantocusta.email.TokenUrlService;

import br.com.leonardoz.quantocusta.entidade.Usuario;
import br.com.leonardoz.quantocusta.exceptions.RecursoNaoEncontradoException;
import br.com.leonardoz.quantocusta.repositorio.UsuariosRepository;
import br.com.leonardoz.quantocusta.seguranca.JwtTokenUtil;
import br.com.leonardoz.quantocusta.seguranca.JwtUser;

@RestController
public class UsuarioController {

	private String tokenHeader = "Authorization";

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private UsuariosRepository repositorio;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private MailService mailService;

	@Autowired
	private TokenUrlService tokenService;

	@GetMapping(value = "/usuario")
	public JwtUser recuperarUsuarioAutenticado(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader).substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

		return user;
	}

	@PostMapping(value = "/usuario")
	public ResponseEntity<?> criarNovoUsuario(@RequestBody CriarUsuarioDto dto) throws ConstraintViolationException {
		Usuario usuario = mapper.map(dto, Usuario.class);
		usuario.setSenha(encoder.encode(dto.getSenha()));
		repositorio.save(usuario);
		String tokenAtivacao = tokenService.gerarValidarUrlToken(dto.getEmail());
		mailService.prepareAndSend(dto.getEmail(), tokenAtivacao, "Validar nova conta", "validaCadastroEmail");
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping(value = "/reenviar/email")
	public ResponseEntity<?> reenviarEmailAtivacao(@RequestBody EmailDto dto) {
		String tokenAtivacao = tokenService.gerarValidarUrlToken(dto.getEmail());
	
		mailService.prepareAndSend(dto.getEmail(), tokenAtivacao, "Validar nova conta","validaCadastroEmail");
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PostMapping(value = "/validar")
	public ResponseEntity<?> validarEmailAtivacao(@RequestBody ValidarEmailDto dto, HttpServletRequest req) {
		boolean tokenEhValido = tokenService.tokenEhValido(dto.getToken(), dto.getEmail());
	
		if (tokenEhValido) {
			Usuario usuario = repositorio.findByEmail(dto.getEmail())
				.orElseThrow(() -> new RecursoNaoEncontradoException("Usuário"));
			if (usuario.isConfirmado()) {
				return ResponseEntity.status(HttpStatus.IM_USED).build();
			}
			usuario.setConfirmado(true);
			repositorio.save(usuario);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			ErroDto body = new ErroDto("Token inválido", req.getRequestURL().toString(),
					HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
		}
	}

	@PostMapping(value = "/esqueci/senha")
	public ResponseEntity<?> enviarEmailEsqueciSenha(@RequestBody EmailDto dto) {
		String tokenAtivacao = tokenService.gerarEsqueciASenhaToken(dto.getEmail());
	
		mailService.prepareAndSend(dto.getEmail(), tokenAtivacao, "Alterar a senha", "alterarSenhaEmail");
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@PostMapping(value = "/alterar/senha")
	public ResponseEntity<?> validarEmailAtivacao(@RequestBody AlterarSenhaDto dto, HttpServletRequest req) {
		boolean tokenEhValido = tokenService.tokenEhValido(dto.getToken(), dto.getEmail());
		if (tokenEhValido) {
			Usuario usuario = repositorio.findByEmail(dto.getEmail())
				.orElseThrow(() -> new RecursoNaoEncontradoException("Usuário"));
			usuario.setSenha(encoder.encode(dto.getNovaSenha()));
			repositorio.save(usuario);
			return ResponseEntity.status(HttpStatus.OK).build();
		} else {
			ErroDto body = new ErroDto("Token inválido", req.getRequestURL().toString(),
					HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
		}
	}
	
	
	

}
