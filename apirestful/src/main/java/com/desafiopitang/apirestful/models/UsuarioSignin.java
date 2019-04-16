package com.desafiopitang.apirestful.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.desafiopitang.apirestful.util.Constantes;

public class UsuarioSignin {

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@NotBlank(message = Constantes.MISSING_FIELDS)
	@Email(message = Constantes.INVALID_FIELDS)
	private String email;

	@NotBlank(message = Constantes.MISSING_FIELDS)
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
