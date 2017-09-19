package br.com.leonardoz.quantocusta.contrato;

import java.io.Serializable;

public class JwtAutenticadoDto implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    public JwtAutenticadoDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
