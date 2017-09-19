package br.com.leonardoz.quantocusta.util;

public class Concatenador {

	public static String concatenar(String...valores) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < valores.length; i++) {
			sb.append(valores[i]);
		}
		return sb.toString();
	}
	
}
