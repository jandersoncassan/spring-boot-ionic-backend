package com.springboot.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.cursomc.domain.Categoria;
import com.springboot.cursomc.repositories.CatergoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CatergoriaRepository catergoriaRepository;
	
	public Optional<Categoria> buscar(Integer id) {
		return catergoriaRepository.findById(id);
	}
}
