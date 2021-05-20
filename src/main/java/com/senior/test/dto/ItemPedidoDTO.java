package com.senior.test.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senior.test.domain.Item;
import com.senior.test.domain.ItemPedido;
import com.senior.test.domain.ItemPedidoPK;
import com.senior.test.domain.Pedido;

public class ItemPedidoDTO implements Serializable{
	private static final long serialVersionUID = 1L;	
	
	private ItemPedidoPK id = new ItemPedidoPK();

	private Integer quantidade;	

	public ItemPedidoDTO() {
	}
	
	public ItemPedidoDTO(Pedido pedido, Item item, Integer quantidade) {
		super();
		id.setPedido(pedido);
		id.setItem(item);
		this.quantidade = quantidade;		
	}	
	
	public ItemPedidoDTO(ItemPedido obj) {
		this.id = obj.getId();
		this.quantidade = obj.getQuantidade();		
	}
	
	@JsonIgnore
	public Pedido getPedido() {
		return id.getPedido();
	}
	
	public void setPedido(Pedido pedido) {
		id.setPedido(pedido);
	}
		
	public Item getItem() {
		return id.getItem();
	}
	
	public void setItem(Item item) {
		id.setItem(item);
	}	

	@JsonIgnore
	public ItemPedidoPK getId() {
		return id;
	}

	public void setId(ItemPedidoPK id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}		
}
