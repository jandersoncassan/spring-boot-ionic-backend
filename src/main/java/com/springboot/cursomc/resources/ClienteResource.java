package com.springboot.cursomc.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.cursomc.domain.Cliente;
import com.springboot.cursomc.dto.ClienteDTO;
import com.springboot.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService clienteService;

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Cliente> find(@PathVariable("id") Integer id) {
		Cliente categoria = clienteService.find(id);
		return ResponseEntity.ok(categoria);
	}

	@PutMapping(value="/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> update(@PathVariable("id") Integer id, @Valid @RequestBody ClienteDTO objDTO) {
		Cliente obj = clienteService.fromDTO(objDTO);
		obj.setId(id); //SÓ PARA GARANTIR QUE ESTAREMOS ATUALIZANDO O ID QUE FOI PASSADO NO PATH
		clienteService.update(obj);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value="/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> update(@PathVariable("id") Integer id) {
		clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<ClienteDTO> categorias = clienteService.findAll();
		return ResponseEntity.ok().body(categorias);
	}

	@GetMapping(value="/pages", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(name="page", defaultValue="0") Integer page, 
			@RequestParam(name="linePerPage", defaultValue="24") Integer linePerPage, 
			@RequestParam(name="direction", defaultValue="ASC") String direction,  
			@RequestParam(name="orderBy", defaultValue="nome") String orderBy) {
		
		Page<ClienteDTO> pageClientes = clienteService.findPage(page, linePerPage, direction, orderBy);
		
		return ResponseEntity.ok().body(pageClientes);
	}

}
