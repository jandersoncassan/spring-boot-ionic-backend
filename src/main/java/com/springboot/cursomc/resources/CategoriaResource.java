package com.springboot.cursomc.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.springboot.cursomc.domain.Categoria;
import com.springboot.cursomc.dto.CategoriaDTO;
import com.springboot.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Categoria> find(@PathVariable("id") Integer id) {
		Categoria categoria = categoriaService.find(id);
		return ResponseEntity.ok(categoria);
	}

	@PostMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDTO) {
		Categoria obj = categoriaService.fromDTO(objDTO);
		Categoria categoria = categoriaService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(categoria.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value="/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> update(@PathVariable("id") Integer id, @Valid @RequestBody CategoriaDTO objDTO) {
		Categoria obj = categoriaService.fromDTO(objDTO);
		obj.setId(id); //SÓ PARA GARANTIR QUE ESTAREMOS ATUALIZANDO O ID QUE FOI PASSADO NO PATH
		categoriaService.update(obj);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value="/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Void> update(@PathVariable("id") Integer id) {
		categoriaService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<CategoriaDTO> categorias = categoriaService.findAll();
		return ResponseEntity.ok().body(categorias);
	}

	@GetMapping(value="/pages", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(name="page", defaultValue="0") Integer page, 
			@RequestParam(name="linePerPage", defaultValue="24") Integer linePerPage, 
			@RequestParam(name="direction", defaultValue="ASC") String direction,  
			@RequestParam(name="orderBy", defaultValue="nome") String orderBy) {
		
		Page<CategoriaDTO> pageCategorias = categoriaService.findPage(page, linePerPage, direction, orderBy);
		
		return ResponseEntity.ok().body(pageCategorias);
	}

}
