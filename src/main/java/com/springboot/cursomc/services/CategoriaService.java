package com.springboot.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.cursomc.domain.Categoria;
import com.springboot.cursomc.repositories.CatergoriaRepository;
import com.springboot.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {

	@Autowired
	private CatergoriaRepository catergoriaRepository;
	
	public Categoria find(Integer id){
		Optional<Categoria> categoria = catergoriaRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Categoria não encontrada ! Id :" + id + ", Tipo  : " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null); // SÓ PARA GARANTIR QUE ESTAMOS PASSANDO UM CARA QUE NÃO EXISTE
		return catergoriaRepository.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		find(obj.getId());
		return catergoriaRepository.save(obj);
	}

}
