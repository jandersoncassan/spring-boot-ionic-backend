package com.springboot.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.cursomc.domain.Cliente;
import com.springboot.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;

	@GetMapping(value="/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cliente> find(@PathVariable("id") Integer id) {		
		
		Cliente cliente = clienteService.buscar(id);		
		return ResponseEntity.ok(cliente);
		
	}
}
