package br.com.leonardoz.quantocusta.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.leonardoz.quantocusta.seguranca.JwtTokenUtil;

@Service
public class TokenUrlService {

	@Autowired
	private JwtTokenUtil tokenUtil;
	
	@Value("${cors.origin}")
	private String origin;
	
	public String gerarValidarUrlToken(String email) {
		String urlTemplate = "%s/validar/token/%s/email/%s";
		String token = tokenUtil.generateToken(email);
		String url = String.format(urlTemplate, origin, token, email);
		return url;
	}
	
	
	public String gerarEsqueciASenhaToken(String email) {
		String urlTemplate = "%s/alterar/senha/token/%s/email/%s";
		String token = tokenUtil.generateToken(email);
		String url = String.format(urlTemplate, origin, token, email);
		return url;
	}
	
	
	public boolean tokenEhValido(String token, String email) {
		return tokenUtil.validateToken(token, email);
	}
}
