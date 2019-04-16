package com.desafiopitang.apirestful.resources;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.desafiopitang.apirestful.models.ErrorMessage;
import com.desafiopitang.apirestful.models.Usuario;
import com.desafiopitang.apirestful.models.UsuarioSignin;
import com.desafiopitang.apirestful.repository.UsuarioRepository;
import com.desafiopitang.apirestful.util.Constantes;
import com.desafiopitang.apirestful.util.Util;

@RestController
@RequestMapping
public class UsuarioResource {

	@Autowired
	UsuarioRepository usuarioRepository;

	@GetMapping("/all")
	public Object listaUsuarios() {
		return usuarioRepository.findAll();
	}

	// cadastra usuário
	// FIXME - PITANG - RETORNAR TOKEN
	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.OK)
	public Object salvarUsuario(@RequestBody Usuario usuario) {
		try {

			if (usuario.getPassword() != null) {
				usuario.setPassword(Util.getSenhaCriptografada(usuario.getPassword()));
			}
			usuario.setCreated_at(new Date());

			Usuario usuario2 = usuarioRepository.save(usuario);

			return usuario2;
		} catch (DataIntegrityViolationException duplicado) {
			// TODO - NÃO ESTÁ TRATANDO QUANDO VEM COM TELEFONE VAZIO, CAI NESSA EXCEÇÃO
			ErrorMessage erro = new ErrorMessage(Constantes.EMAIL_ALREADY_EXISTS, HttpStatus.BAD_REQUEST.value());
			return erro;
		} catch (Exception e) {
			ConstraintViolationException teste = (ConstraintViolationException) e.getCause().getCause();
			Set<ConstraintViolation<?>> violations = teste.getConstraintViolations();
			ErrorMessage erro = null;
			for (ConstraintViolation<?> violation : violations) {
				if (violation.getMessage().equals(Constantes.INVALID_FIELDS)) {
					erro = new ErrorMessage(violation.getMessage(), HttpStatus.FORBIDDEN.value());
				} else if (violation.getMessage().equals(Constantes.MISSING_FIELDS)) {
					erro = new ErrorMessage(violation.getMessage(), HttpStatus.BAD_REQUEST.value());
				}
				break;
			}

			return erro;
		}
		// TODO deve retornar token
	}

	/*
	 * { "firstName": "Hello2", "lastName": "World2", "email": "teste@teste.com",
	 * "password": "hunter", "phones": [ { "number": 988887888, "area_code": 81,
	 * "country_code": "+55" } ] }
	 */

	// efetuar login
	// FIXME - PITANG - RETORNAR TOKEN
	@PostMapping("/signin")
	@ResponseStatus(HttpStatus.OK)
	public Object efetuarLogin(@RequestBody UsuarioSignin usuarioSignin) {

		if (usuarioSignin.getPassword() != null) {
			usuarioSignin.setPassword(Util.getSenhaCriptografada(usuarioSignin.getPassword()));
		}
		Usuario usuario = usuarioRepository.findByEmailAndPassword(usuarioSignin.getEmail(),
				usuarioSignin.getPassword());
		if (usuario == null) {
			ErrorMessage erro = new ErrorMessage(Constantes.INVALID_EMAIL_PASSWORD, HttpStatus.FORBIDDEN.value());
			return erro;
		} else if ((usuarioSignin.getEmail() == null || usuarioSignin.getEmail().isEmpty())
				|| (usuarioSignin.getPassword() == null || usuarioSignin.getPassword().isEmpty())) {
			ErrorMessage erro = new ErrorMessage(Constantes.MISSING_FIELDS, HttpStatus.BAD_REQUEST.value());
			return erro;
		} else {
			usuario.setUpdate_at(new Date());
			usuarioRepository.save(usuario);
			return usuario;
		}

		// TODO deve retornar token
		/*
		 * { "email": "teste@teste.com", "password": "hunter" }
		 */
	}

	// consulta informações do usuário
	// FIXME - PITANG - TOKEN NO HEADER
	@GetMapping("/me")
	public Object getUsuario(@RequestHeader String authorization) {
		ErrorMessage erro = null;
		
		if(authorization == null || authorization.isEmpty()) {
			erro = new ErrorMessage(Constantes.UNAUTHORIZED, HttpStatus.FORBIDDEN.value());
		} else {
			//simulando um token com sessão expirada
			 erro = new ErrorMessage(Constantes.UNAUTHORIZED_INVALID_SESSION, HttpStatus.FORBIDDEN.value());
		}
		
		// //TODO NÃO IMPLEMENTADO COM TOKEN
//		else {
//		//	 faz parse do token
//			String email = Jwts.parser()
//					.setSigningKey(Constantes.SECRET)
//					.parseClaimsJws(authorization.replace(Constantes.TOKEN_PREFIX, ""))
//					.getBody()
//					.getSubject();
//			
//			if (email != null) {
//				Usuario usuario = usuarioRepository.findByEmail(email);
//				
//				return usuario;
//			}
//		} 
//token expirado
//		ErrorMessage erro = new ErrorMessage(Constantes.UNAUTHORIZED_INVALID_SESSION, HttpStatus.FORBIDDEN.value());
		return erro;
	}

	@GetMapping("/me2")
	public Object getUsuario(@RequestHeader String email, @RequestHeader String password) {

		if (password != null) {
			password = Util.getSenhaCriptografada(password);
		}
		Usuario usuario = usuarioRepository.findByEmailAndPassword(email, password);

		if (usuario == null) {
			ErrorMessage erro = new ErrorMessage(Constantes.UNAUTHORIZED, HttpStatus.FORBIDDEN.value());
			return erro;
		}

		return usuario;
	}
}
