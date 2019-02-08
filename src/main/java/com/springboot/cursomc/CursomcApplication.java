package com.springboot.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.springboot.cursomc.domain.Categoria;
import com.springboot.cursomc.domain.Cidade;
import com.springboot.cursomc.domain.Cliente;
import com.springboot.cursomc.domain.Endereco;
import com.springboot.cursomc.domain.Estado;
import com.springboot.cursomc.domain.Produto;
import com.springboot.cursomc.domain.enums.TipoCliente;
import com.springboot.cursomc.repositories.CatergoriaRepository;
import com.springboot.cursomc.repositories.CidadeRepository;
import com.springboot.cursomc.repositories.ClienteRepository;
import com.springboot.cursomc.repositories.EnderecoRepository;
import com.springboot.cursomc.repositories.EstadoRepository;
import com.springboot.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	CatergoriaRepository categoriaRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	EstadoRepository estadoRepository;

	@Autowired
	CidadeRepository cidadeRepository;

	@Autowired
	ClienteRepository clienteRepository;

	@Autowired
	EnderecoRepository enderecoRepository;


	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria c1 = new Categoria(null, "Informatica");
		Categoria c2 = new Categoria(null, "Escritorio");
		
		Produto p1 = new Produto(null, "Informatica", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		c1.getProdutos().addAll(Arrays.asList(p1, p2, p3));		
		c2.getProdutos().add(p2);
		
		p1.getCategorias().add(c1);
		p2.getCategorias().addAll(Arrays.asList(c1, c2));
		p3.getCategorias().add(c1);			
		
		categoriaRepository.saveAll(Arrays.asList(c1, c2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));	
		
		Estado est1 = new Estado(null, "MInas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlandia", est1);
		Cidade cid2 = new Cidade(null, "São Paulo", est2); 	 
		Cidade cid3 = new Cidade(null, "Campinas", est2); 	 
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com","33243454544", TipoCliente.PESSOA_FISICA);
		cli1.getTelefones().addAll(Arrays.asList("21342255","21342266"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim","04533635", cli1, cid1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro","32455334", cli1, cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
	}
}
