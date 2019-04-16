package com.desafiopitang.apirestful.util;

import java.util.Base64;

public class Util {

	public static String getSenhaCriptografada(String senha) {

		senha = Base64.getEncoder().encodeToString(senha.getBytes());
		return senha;
	}
	
}
