package com.springboot.cursomc.domain.enums;

import java.util.Optional;

public enum TipoCliente {

	PESSOA_FISICA(1, "Pessoa Fisica"),
	PESSOA_JURIDICA(2, "Pessoa Juridica");
	
	private Integer codigo;
	private String descricao;
	
	private TipoCliente(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public static Optional<TipoCliente> toEnum(Integer codigo) {
		for(TipoCliente tipo : TipoCliente.values()) {
			if(tipo.getCodigo().equals(codigo)) {
				return Optional.of(tipo);
			}
		}
		throw new IllegalArgumentException("codigo invalido: "+ codigo);
	}

	public Integer getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
