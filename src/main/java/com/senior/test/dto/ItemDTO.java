package com.senior.test.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.senior.test.domain.Item;

public class ItemDTO implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	
	@NotEmpty(message = "Campo Obrigatário")	
	@Size(min=3, max=80, message = "Campo deve estar entre 3 e 80 caracteres")
	private String descricao;
	
	@Positive(message = "Preço deve ser maior que zero")
	private Double preco;	
	private Integer tipo;
	private Boolean ativo;

	public ItemDTO() {		
	}
	
	public ItemDTO(UUID id,	String descricao, Double preco, Integer tipo, Boolean ativo) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.preco = preco;
		this.tipo = tipo;
		this.ativo = ativo;
	}
	
	public ItemDTO(Item obj) {		
		this.id = obj.getId();
		this.descricao = obj.getDescricao();
		this.preco = obj.getPreco();
		this.tipo = obj.getTipo().getCod();
		this.ativo = obj.getAtivo();
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

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}	
}
