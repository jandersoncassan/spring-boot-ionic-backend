package com.springboot.cursomc.domain.enums;

import java.util.Optional;

public enum EstadoPagamento {

	PENDENTE(1,"Pendente"), QUITADO(2, "Quitado"), CANCELADO(3, "Cancelado");
	
	private Integer codigo;
	private String descricao;
	
	private EstadoPagamento(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public static Optional<EstadoPagamento> toEnum(Integer codigo) {
		for(EstadoPagamento tipo : EstadoPagamento.values()) {
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
