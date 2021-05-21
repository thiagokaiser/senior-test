package com.senior.test.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.senior.test.dto.ItemPedidoDTO;

@Entity
public class ItemPedido implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore
	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();

	private Integer quantidade;	
	private Double preco;
	private Double total;

	public ItemPedido() {
	}
	
	public ItemPedido(Pedido pedido, Item item, Integer quantidade, Double preco, Double total) {
		super();
		id.setPedido(pedido);
		id.setItem(item);
		this.quantidade = quantidade;
		this.preco = preco;
		this.total = total;
	}
	
	public ItemPedido(ItemPedidoDTO obj) {
		id.setPedido(obj.getPedido());
		id.setItem(obj.getItem());
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
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
