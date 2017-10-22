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
		constraintCodeMap.put("un_orcamento_id_titulo",
				"Já existe uma Unidade de Software com esse título no orçamento.");
	}
	
	private static String mensagemDeRestricoes(ConstraintViolationException ex) {
		String base = "Conflito ao cadastrar informações. ";
		String mensagem = constraintCodeMap.get(ex.getConstraintName());
		return Concatenador.concatenar(base, mensagem);
	}
//
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ErroDto> defaultExceptionHandler(HttpServletRequest req, Exception ex) {
//		return gerarErroDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, req);
//	}
	
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<ErroDto> recurnoNaoEncontradoExceptionHandler(HttpServletRequest req, RecursoNaoEncontradoException ex) {
		String mensagem = Concatenador.concatenar("Recurso do tipo ", ex.getTipoDeRecurso(),
				" não encontrado com o UUID informado");
		return gerarErroDto(mensagem, HttpStatus.BAD_REQUEST, req);
	}

	@ExceptionHandler(ConflitoDeInformacaoException.class)
	public ResponseEntity<ErroDto> conflitoDeInformacaoExceptionHandler(HttpServletRequest req,
			ConflitoDeInformacaoException ex) {
		String mensagem = Concatenador.concatenar("Conflito ao cadastrar ", ex.getTipoDeRecurso(), ". ",
				"Campo conflitante: " + ex.getCampo(), ". Tente outro valor.");
		return gerarErroDto(mensagem, HttpStatus.CONFLICT, req);
	}

	// Fallback em caso de não verificar algum campo no Controller
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErroDto> integrityExceptionHandler(HttpServletRequest req, ConstraintViolationException ex) {
		String mensagem = mensagemDeRestricoes(ex);
		return gerarErroDto(mensagem, HttpStatus.CONFLICT, req);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErroDto> badCredentialsException(HttpServletRequest req, BadCredentialsException ex) {
		String mensagem = "Usuário e/ou senhas não autorizados.";
		return gerarErroDto(mensagem, HttpStatus.UNAUTHORIZED, req);
	}

	@ExceptionHandler(UsuarioNaoConfirmadoException.class)
	public ResponseEntity<ErroDto> usuarioNaoConfirmadoException(HttpServletRequest req,
			UsuarioNaoConfirmadoException ex) {
		return gerarErroDto(ex.getMessage(), HttpStatus.UNAUTHORIZED, req);
	}

	@ExceptionHandler(JsonParseException.class)
	public ResponseEntity<ErroDto> jsonParseException(HttpServletRequest req, JsonParseException ex) {
		String mensagem = "Requisição em formato inválido. Contate os administradores";
		return gerarErroDto(mensagem, HttpStatus.INTERNAL_SERVER_ERROR, req);
	}

	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<ErroDto> malformedJwtException(HttpServletRequest req, MalformedJwtException ex) {
		String mensagem = "Token em formato inválido";
		return gerarErroDto(mensagem, HttpStatus.BAD_REQUEST, req);
	}

	@ExceptionHandler(PagamentoInvalidoException.class)
	public ResponseEntity<ErroDto> pagamentoInvalidoHandler(HttpServletRequest req, PagamentoInvalidoException ex) {
		ex.printStackTrace();
		return gerarErroDto(ex.getMessage(), HttpStatus.BAD_REQUEST, req);
	}

	private ResponseEntity<ErroDto> gerarErroDto(String mensagem, HttpStatus httpStatus, HttpServletRequest request) {
		ErroDto erro = new ErroDto(mensagem, request.getRequestURI(), httpStatus.value());
		return ResponseEntity.status(httpStatus).body(erro);
	}


}
