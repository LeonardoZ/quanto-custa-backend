package br.com.leonardoz.quantocusta.exceptions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonParseException;

import br.com.leonardoz.quantocusta.apicontrato.ErroDto;
import br.com.leonardoz.quantocusta.util.Concatenador;
import io.jsonwebtoken.MalformedJwtException;

@RestControllerAdvice
public class CustomExceptionsHandler {

	private static Map<String, String> constraintCodeMap = new HashMap<String, String>();

	{
		constraintCodeMap.put("un_login", "Login já está em uso.");
		constraintCodeMap.put("un_email", "E-mail já está em uso.");
		constraintCodeMap.put("un_unidade_id_nome", "Já existe um Artefato com esse nome na unidade de software.");
		constraintCodeMap.put("un_uuid", "UUID gerado pelo sistema já está em uso.");
		constraintCodeMap.put("un_orcamento_id_titulo", "Já existe uma Unidade de Software com esse título no orçamento.");
	}

	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<ErroDto> exceptionHandler(HttpServletRequest req, RecursoNaoEncontradoException ex) {
		String mensagem = Concatenador.concatenar("Recurso do tipo ", ex.getTipoDeRecurso(),
				" não encontrado com o UUID informado");
		ErroDto erro = new ErroDto(mensagem, req.getRequestURI(), HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(ConflitoDeInformacaoException.class)
	public ResponseEntity<ErroDto> conflitoDeInformacaoExceptionHandler(HttpServletRequest req,
			ConflitoDeInformacaoException ex) {
		String mensagem = Concatenador.concatenar("Conflito ao cadastrar ", ex.getTipoDeRecurso(), ". ",
				"Campo conflitante: " + ex.getCampo(), ". Tente outro valor.");
		ErroDto erro = new ErroDto(mensagem, req.getRequestURI(), HttpStatus.CONFLICT.value());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}

	// Fallback em caso de não verificar algum campo no Controller
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErroDto> integrityExceptionHandler(HttpServletRequest req, ConstraintViolationException ex) {
		ErroDto erro = new ErroDto(mensagemDeRestricoes(ex), req.getRequestURI(), HttpStatus.CONFLICT.value());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErroDto> badCredentialsException(HttpServletRequest req, BadCredentialsException ex) {
		ErroDto erro = new ErroDto("Usuário e/ou senhas não autorizados.", 
					req.getRequestURI(), HttpStatus.UNAUTHORIZED.value());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
	}
	
	@ExceptionHandler(UsuarioNaoConfirmadoException.class)
	public ResponseEntity<ErroDto> usuarioNaoConfirmadoException(HttpServletRequest req, UsuarioNaoConfirmadoException ex) {
		ErroDto erro = new ErroDto(ex.getMessage(), 
					req.getRequestURI(), HttpStatus.UNAUTHORIZED.value());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
	}
	
	@ExceptionHandler(JsonParseException.class)
	public ResponseEntity<ErroDto> jsonParseException(HttpServletRequest req, JsonParseException ex) {
		ErroDto erro = new ErroDto("Requisição em formato inválido. Contate os administradores", 
					req.getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
	}

	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<ErroDto> malformedJwtException(HttpServletRequest req, MalformedJwtException ex) {
		ex.printStackTrace();
		ErroDto erro = new ErroDto("Token em formato inválido", 
					req.getRequestURI(), HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	private static String mensagemDeRestricoes(ConstraintViolationException ex) {
		String base = "Conflito ao cadastrar informações. ";
		String mensagem = constraintCodeMap.get(ex.getConstraintName());
		return Concatenador.concatenar(base, mensagem);
	}

}
