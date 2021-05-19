package com.senior.test.domain.enums;

public enum TipoItem {
	
	PRODUTO(1,"PRODUTO"),
	SERVICO(2,"SERVICO");

	private int cod;
	private String descricao;
	
	private TipoItem(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoItem toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		for (TipoItem x : TipoItem.values()) {
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		
		throw new IllegalArgumentException("Tipo Item inválido: " + cod);
		
	}
}
