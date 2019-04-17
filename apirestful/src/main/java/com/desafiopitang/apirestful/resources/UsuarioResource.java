package com.desafiopitang.apirestful.resources;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping
public class UsuarioResource {

	@Autowired
	UsuarioRepository usuarioRepository;

	@GetMapping("/all")
	public Object listaUsuarios() {
		return usuarioRepository.findAll();
	}

	/*
	 * { "firstName": "Teste", "lastName": "JWT", "email": "jwt2@teste.com",
	 * "password": "password", "phones": [ { "number": 988887888, "area_code": 81,
	 * "country_code": "+55" } ] }
	 */
	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.OK)
	public Object salvarUsuario(@RequestBody Usuario usuario) {
		try {

			if (usuario.getPassword() != null) {
				usuario.setPassword(Util.getSenhaCriptografada(usuario.getPassword()));
			}
			usuario.setCreated_at(new Date(System.currentTimeMillis()));

			usuarioRepository.save(usuario);

			String token = Jwts.builder().setSubject(usuario.getEmail()).claim("usuario", usuario)
					.claim("password", usuario.getPassword()).signWith(SignatureAlgorithm.HS512, Constantes.SECRET_KEY)
					.setExpiration(Util.getExpirationToken(Constantes.QTD_MIN_EXPIRAR_TOKEN)).compact();

			return token;
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

	}

	/*
	 * { "email": "jwt2@teste.com", "password": "password"}
	 */
	@PostMapping("/signin")
	@ResponseStatus(HttpStatus.OK)
	public Object efetuarLogin(@RequestBody UsuarioSignin usuarioSignin) {

		if (usuarioSignin.getPassword() != null) {
			usuarioSignin.setPassword(Util.getSenhaCriptografada(usuarioSignin.getPassword()));
		}
		Usuario usuario = usuarioRepository.findByEmailAndPassword(usuarioSignin.getEmail(),
				usuarioSignin.getPassword());
		if (usuario == null) {
			return new ErrorMessage(Constantes.INVALID_EMAIL_PASSWORD, HttpStatus.FORBIDDEN.value());
		} else if ((usuarioSignin.getEmail() == null || usuarioSignin.getEmail().isEmpty())
				|| (usuarioSignin.getPassword() == null || usuarioSignin.getPassword().isEmpty())) {
			return new ErrorMessage(Constantes.MISSING_FIELDS, HttpStatus.BAD_REQUEST.value());
		} else {
			usuario.setUpdate_at(new Date(System.currentTimeMillis()));
			usuarioRepository.save(usuario);
			String token = Jwts.builder().setSubject(usuario.getEmail()).claim("usuario", usuario)
					.claim("password", usuarioSignin.getPassword())
					.signWith(SignatureAlgorithm.HS512, Constantes.SECRET_KEY)
					.setExpiration(Util.getExpirationToken(Constantes.QTD_MIN_EXPIRAR_TOKEN)).compact();

			return token;
		}

	}

	@GetMapping("/me")
	public Object getUsuario(@RequestHeader String authorization) {

		if (authorization == null || authorization.isEmpty()) {
			return new ErrorMessage(Constantes.UNAUTHORIZED, HttpStatus.FORBIDDEN.value());
		} else {
			try {
				Claims claims = Jwts.parser().setSigningKey(Constantes.SECRET_KEY).parseClaimsJws(authorization).getBody();
			
				Usuario usuario = usuarioRepository.findByEmailAndPassword(claims.getSubject(),
						claims.get("password").toString());

				return usuario;
			}catch (ExpiredJwtException ex){
				return new ErrorMessage(Constantes.UNAUTHORIZED_INVALID_SESSION, HttpStatus.FORBIDDEN.value());
			}

				
			}

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
