package com.senior.test.domain.enums;

public enum SituacaoPedido {
	
	ABERTO(1,"ABERTO"),
	FECHADO(2,"FECHADO");

	private int cod;
	private String descricao;
	
	private SituacaoPedido(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static SituacaoPedido toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		for (SituacaoPedido x : SituacaoPedido.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Situação do pedido inválida: " + cod);
		
	}
}
