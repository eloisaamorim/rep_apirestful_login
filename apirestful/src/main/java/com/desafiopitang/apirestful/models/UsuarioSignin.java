package com.desafiopitang.apirestful.models;

import java.time.LocalDate;

public class UsuarioSignin {

	private String email;

	private String password;

	public UsuarioSignin(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "UsuarioSignin [email=" + email + ", password=" + password + "]";
	}

}
