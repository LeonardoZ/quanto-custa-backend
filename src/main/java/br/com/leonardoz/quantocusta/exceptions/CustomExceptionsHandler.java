package br.com.leonardoz.quantocusta.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.leonardoz.quantocusta.contrato.Erro;

@RestControllerAdvice
public class CustomExceptionsHandler {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "")
	@ExceptionHandler(RecursoNaoEncontradoException.class)
	public ResponseEntity<Erro> exceptionHandler(HttpServletRequest req, RecursoNaoEncontradoException ex) {
		Erro erro = new Erro("Recurso do tipo \"" + ex.getTipoDeRecurso() + "\" não encontrado com o UUID informado",
				req.getRequestURI(), HttpStatus.BAD_REQUEST.value());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

}
