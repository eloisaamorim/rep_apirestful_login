package com.desafiopitang.apirestful.util;

import java.util.Base64;
import java.util.Date;

public class Util {

	public static String getSenhaCriptografada(String senha) {

		senha = Base64.getEncoder().encodeToString(senha.getBytes());
		return senha;
	}
	
	public static Date getExpirationToken(int qtdMinutosAtivo) {

		return new Date(System.currentTimeMillis() + (qtdMinutosAtivo * Constantes.ONE_MINUTE_IN_MILLIS));
	}
	
}
