package com.senior.test.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.senior.test.domain.enums.TipoItem;

@Entity
public class Item implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private UUID id;	
	private String descricao;
	private Double preco;
	private Integer tipo;
	private Boolean ativo;
		
	public Item() {		
	}

	public Item(UUID id, String descricao, Double preco, TipoItem tipo, Boolean ativo) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.preco = preco;
		this.tipo = tipo.getCod();
		this.ativo = ativo;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}	

	public TipoItem getTipo() {
		return TipoItem.toEnum(tipo);
	}

	public void setTipo(TipoItem tipo) {
		this.tipo = tipo.getCod();
	}	

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
