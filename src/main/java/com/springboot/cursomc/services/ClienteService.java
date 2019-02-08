package com.springboot.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.cursomc.domain.Cliente;
import com.springboot.cursomc.repositories.ClienteRepository;
import com.springboot.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteaRepository;
	
	public Cliente buscar(Integer id){
		Optional<Cliente> cliente = clienteaRepository.findById(id);
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não encontrada ! Id :" + id + ", Tipo  : " + Cliente.class.getName()));
	}
}
