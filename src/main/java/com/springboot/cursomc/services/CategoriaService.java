package com.springboot.cursomc.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		return catergoriaRepository.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			catergoriaRepository.deleteById(id);
			
		}catch(Exception ex) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos", ex);
		}
	}
	
	public Page<CategoriaDTO> findPage(Integer page, Integer linePerPage, String direction,  String orderBy){
		PageRequest pageRequest = PageRequest.of(page, linePerPage, Direction.valueOf(direction), orderBy);
		Page<Categoria> categorias = catergoriaRepository.findAll(pageRequest);
		return categorias.map(categoria -> popularCategoria(categoria));
	}

	
	private CategoriaDTO popularCategoria(Categoria categoria) {
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setId(categoria.getId());
		categoriaDTO.setNome(categoria.getNome());
		return categoriaDTO;
	}

	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome());
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());		
	}

}
