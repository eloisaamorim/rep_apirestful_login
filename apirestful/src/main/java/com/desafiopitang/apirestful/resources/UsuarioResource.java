package com.desafiopitang.apirestful.resources;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	//cadastra usuário
	//FIXME - PITANG - RETORNAR TOKEN
	@PostMapping("/signup")
	@ResponseStatus(HttpStatus.OK)
	public Object salvarUsuario(@RequestBody Usuario usuario) {
	try {
		
		usuario.setPassword(Util.getSenhaCriptografada(usuario.getPassword()));
		
		usuario.setCreated_at(new Date());
		
		if((usuario.getEmail() == null || usuario.getEmail().isEmpty() ) || (usuario.getPassword() == null || usuario.getPassword().isEmpty() )){
			ErrorMessage erro = new ErrorMessage("Missing Fields",HttpStatus.BAD_REQUEST.value());
			return erro;
		}
		
	 	Usuario usuario2 = usuarioRepository.save(usuario);
		
		return usuario2;
		}catch (Exception e) {
				ErrorMessage erro = new ErrorMessage("E-mail already exists",HttpStatus.FORBIDDEN.value());
				return erro;
		}
		// TODO deve retornar token
	}

		
		
		/*
		 {
"firstName": "Hello2",
"lastName": "World2",
"email": "teste@teste.com",
"password": "hunter",
"phones": [
{
"number": 988887888,
"area_code": 81,
"country_code": "+55"
}
]
}
		 */
	
	//efetuar login
	//FIXME - PITANG - RETORNAR TOKEN
	@GetMapping("/signin")
	@ResponseStatus(HttpStatus.OK)
	public Object efetuarLogin(@RequestBody UsuarioSignin usuarioSignin) {
	
		usuarioSignin.setPassword(Util.getSenhaCriptografada(usuarioSignin.getPassword()));
		Usuario usuario = usuarioRepository.findByEmailAndPassword(usuarioSignin.getEmail(), usuarioSignin.getPassword());
		if( usuario == null) {
			ErrorMessage erro = new ErrorMessage("Invalid e-mail or password",HttpStatus.FORBIDDEN.value());
			return erro;
		} else if((usuarioSignin.getEmail() == null || usuarioSignin.getEmail().isEmpty() ) || (usuarioSignin.getPassword() == null || usuarioSignin.getPassword().isEmpty() )){
			ErrorMessage erro = new ErrorMessage("Missing Fields",HttpStatus.BAD_REQUEST.value());
			return erro;
		} else {
			usuario.setUpdate_at(new Date());
			usuarioRepository.save(usuario);
			return usuario;
		}
		
		
		// TODO deve retornar token
/*
		 {
"email": "teste@teste.com",
"password": "hunter"
}
		 */
	}

	//consulta informações do usuário
	//FIXME - PITANG - TOKEN NO HEADER
	@GetMapping("/me")
	public Object getUsuario(@RequestHeader String tokenHeader) {
		Usuario usuario = new Usuario();
		
		if( usuario == null) {
			ErrorMessage erro = new ErrorMessage("Unauthorized",HttpStatus.FORBIDDEN.value());
			return erro;
		} else {
			ErrorMessage erro = new ErrorMessage("Unauthorized - invalid session",HttpStatus.FORBIDDEN.value());
			return erro;
		}
		
//		return usuario;
	}
	
	
	@GetMapping("/me/{id}")
	public Object getUsuario(@PathVariable(value = "id") long id) {
		Usuario usuario = usuarioRepository.findById(id);
		if( usuario == null) {
			ErrorMessage erro = new ErrorMessage("Unauthorized",HttpStatus.FORBIDDEN.value());
			return erro;
		}
		
		return usuario;
	}
}
