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
	private Double preco;
	private Double total;
	

	public ItemPedidoDTO() {
	}
	
	public ItemPedidoDTO(Pedido pedido, Item item, Integer quantidade, Double preco, Double total) {
		super();
		id.setPedido(pedido);
		id.setItem(item);
		this.quantidade = quantidade;
		this.preco = preco;
		this.total = total;
	}	
	
	public ItemPedidoDTO(ItemPedido obj) {
		this.id = obj.getId();
		this.quantidade = obj.getQuantidade();
		this.preco = obj.getPreco();
		this.total = obj.getTotal();
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}	
	
}
