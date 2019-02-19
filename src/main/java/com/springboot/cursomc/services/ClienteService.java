package com.springboot.cursomc.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.springboot.cursomc.domain.Cidade;
import com.springboot.cursomc.domain.Cliente;
import com.springboot.cursomc.domain.Endereco;
import com.springboot.cursomc.domain.enums.TipoCliente;
import com.springboot.cursomc.dto.ClienteDTO;
import com.springboot.cursomc.dto.ClienteNewDTO;
import com.springboot.cursomc.repositories.ClienteRepository;
import com.springboot.cursomc.repositories.EnderecoRepository;
import com.springboot.cursomc.services.exceptions.DataIntegrityException;
import com.springboot.cursomc.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public List<ClienteDTO> findAll(){
		List<Cliente> categorias = clienteRepository.findAll();
		return categorias.stream().map(categoria -> popularCliente(categoria)).collect(Collectors.toList());
	}


	public Cliente find(Integer id){
		Optional<Cliente> categoria = clienteRepository.findById(id);
		return categoria.orElseThrow(() -> new ObjectNotFoundException(
				"Cliente não encontrada ! Id :" + id + ", Tipo  : " + Cliente.class.getName()));
	}
	
	public Cliente insert(Cliente obj) {
		obj.setId(null); // SÓ PARA GARANTIR QUE ESTAMOS PASSANDO UM CARA QUE NÃO EXISTE
		obj =  clienteRepository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return clienteRepository.save(newObj);
	}



	public void delete(Integer id) {
		find(id);
		try {
			clienteRepository.deleteById(id);
			
		}catch(Exception ex) {
			throw new DataIntegrityException("Não é possivel excluir porque há entidades relacionadas", ex);
		}
	}
	
	public Page<ClienteDTO> findPage(Integer page, Integer linePerPage, String direction,  String orderBy){
		PageRequest pageRequest = PageRequest.of(page, linePerPage, Direction.valueOf(direction), orderBy);
		Page<Cliente> categorias = clienteRepository.findAll(pageRequest);
		return categorias.map(categoria -> popularCliente(categoria));
	}

	
	private ClienteDTO popularCliente(Cliente categoria) {
		ClienteDTO categoriaDTO = new ClienteDTO();
		categoriaDTO.setId(categoria.getId());
		categoriaDTO.setNome(categoria.getNome());
		return categoriaDTO;
	}

	public Cliente fromDTO(ClienteDTO objDTO) {
		 return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfCnpj(), TipoCliente.toEnum(objDTO.getTipo()).get());
		Cidade cid = new Cidade(objDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		if(Optional.ofNullable(objDTO.getTelefone2()).isPresent()) {
			cli.getTelefones().add(objDTO.getTelefone2());
		}
		if(Optional.ofNullable(objDTO.getTelefone3()).isPresent()) {
			cli.getTelefones().add(objDTO.getTelefone3());
		}
			
		 return cli;
	}

	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());		
	}


}
