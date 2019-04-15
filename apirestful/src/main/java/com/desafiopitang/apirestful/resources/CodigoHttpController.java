package com.desafiopitang.apirestful.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.desafiopitang.apirestful.models.Usuario;
import com.desafiopitang.apirestful.models.UsuarioSignin;

public class CodigoHttpController {

	
	
//	@RestController
//	//Mapeia as requisições de localhost:8080/api/
//	@RequestMapping("/api/")
//	public class PersonController {
//	     
//	    @Autowired
//	    private UsuarioResource personService;
//	    
//	    @ResponseStatus(HttpStatus.OK) //Por padrão responde com o status code 200 success
//	    @RequestMapping(value = "/me/{id}",
//	        //Mapeia as requisições GET para localhost:8080/person/
//	        //recebendo um ID como @PathVariable
//	        method = RequestMethod.GET, 
//	        produces = MediaType.APPLICATION_JSON_VALUE)
//	        // Produz JSON como retorno
//	    public Usuario getUsuario(@PathVariable(value = "id") long id){
//	        return personService.getUsuario(id);
//	    }
//	     
////	    @ResponseStatus(HttpStatus.OK)
////	    //Por padrão responde com o status code 200 success
////	    @RequestMapping(value = "/findAll",
////	        //Mapeia as requisições GET para localhost:8080/person/findAll
////	        method = RequestMethod.GET,
////	        produces = MediaType.APPLICATION_JSON_VALUE)
////	        // Produz JSON como retorno
////	    public List<Person> findAll(){
////	        return personService.findAll();
////	    }
////	     
////	    @ResponseStatus(HttpStatus.OK)
////	    //Por padrão responde com o status code 200 success
////	    @RequestMapping(method = RequestMethod.PUT,
////	    //Mapeia as requisições PUT para localhost:8080/person/
////	        consumes = MediaType.APPLICATION_JSON_VALUE,
////	        // Consome JSON enviado no corpo da requisição
////	        produces = MediaType.APPLICATION_JSON_VALUE)
////	        // Produz JSON como retorno
////	    public Person create(@RequestBody Person person){
////	        return personService.create(person);
////	    }
//	     
//	    @ResponseStatus(HttpStatus.OK)
//	    //Por padrão responde com o status code 200 success
//	    @RequestMapping(method = RequestMethod.POST,
//	    //Mapeia as requisições POST para localhost:8080/person/
//	        consumes = MediaType.APPLICATION_JSON_VALUE)
//	        // Consome JSON enviado no corpo da requisição
//	    public Usuario update(@RequestBody UsuarioSignin usuarioSignin){
//	        return personService.efetuarLogin(usuarioSignin);
//	    }
//	 
////	    @ResponseStatus(HttpStatus.OK)
////	    //Por padrão responde com o status code 200 success
////	    @RequestMapping(value = "/{personId}",
////	        method = RequestMethod.DELETE)
////	        //Mapeia as requisições DELETE para localhost:8080/person/ 
////	        //recebendo um ID como @PathVariable
////	    public void delete(@PathVariable(value = "personId") String personId){
////	        personService.delete(personId);
////	    }
////	
//	}
}
