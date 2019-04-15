package com.desafiopitang.apirestful.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafiopitang.apirestful.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findById(long id);
	
	Usuario findByEmail(String email);
	
	Usuario findByEmailAndPassword(String email, String password);
	
}
