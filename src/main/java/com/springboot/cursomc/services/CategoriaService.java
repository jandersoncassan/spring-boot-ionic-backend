package com.springboot.cursomc.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.cursomc.domain.Categoria;
import com.springboot.cursomc.dto.CategoriaDTO;
import com.springboot.cursomc.repositories.CatergoriaRepository;
import com.springboot.cursomc.services.exceptions.DataIntegrityException;
import com.springboot.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {

	@Autowired
	private CatergoriaRepository catergoriaRepository;
	
	public List<CategoriaDTO> findAll(){
		List<Categoria> categorias = catergoriaRepository.findAll();
		return categorias.stream().map(categoria -> popularCategoria(categoria)).collect(Collectors.toList());
	}

	private CategoriaDTO popularCategoria(Categoria categoria) {
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setId(categoria.getId());
		categoriaDTO.setNome(categoria.getNome());
		return categoriaDTO;
	}

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

	public void delete(Integer id) {
		find(id);
		try {
			catergoriaRepository.deleteById(id);
			
		}catch(Exception ex) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos", ex);
		}
	}

}
