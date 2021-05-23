package com.senior.test.dto;

import java.io.Serializable;
import java.util.UUID;

import javax.validation.constraints.Min;

import com.senior.test.services.validation.ItemPedidoUpdate;

@ItemPedidoUpdate
public class ItemPedidoUpdateDTO implements Serializable{
	private static final long serialVersionUID = 1L;	
		
	private UUID idPedido;	
	private UUID idItem;
		
	@Min(value = 1, message = "Quantidade deve ser maior que zero")
	private Integer quantidade;	

	public ItemPedidoUpdateDTO() {
	}

	public ItemPedidoUpdateDTO(UUID idPedido, UUID idItem, Integer quantidade) {
		super();
		this.idPedido = idPedido;
		this.idItem = idItem;
		this.quantidade = quantidade;
	}

	public UUID getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(UUID idPedido) {
		this.idPedido = idPedido;
	}

	public UUID getIdItem() {
		return idItem;
	}

	public void setIdItem(UUID idItem) {
		this.idItem = idItem;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}			
}
