package com.springboot.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.cursomc.domain.Pedido;
import com.springboot.cursomc.repositories.PedidoRepository;
import com.springboot.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscar(Integer id){
		Optional<Pedido> categoria = pedidoRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Pedido não encontrada ! Id :" + id + ", Tipo  : " + Pedido.class.getName()));
	}
}
