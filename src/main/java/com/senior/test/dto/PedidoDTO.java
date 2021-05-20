package com.senior.test.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.senior.test.domain.ItemPedido;
import com.senior.test.domain.Pedido;

public class PedidoDTO implements Serializable{	
	private static final long serialVersionUID = 1L;

	private UUID id;	
	private Date instante;	
	private Integer situacao;
	private Double desconto;
	private Double total;
	private Set<ItemPedido> itens = new HashSet<>();
	
	public PedidoDTO() {		
	}
	
	public PedidoDTO(Pedido obj) {
		super();
		this.id = obj.getId();
		this.instante = obj.getInstante();
		this.situacao = obj.getSituacao();
		this.desconto = obj.getDesconto();
		this.total = obj.getTotal();
		this.itens = obj.getItens();
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Integer getSituacao() {
		return situacao;
	}

	public void setSituacao(Integer situacao) {
		this.situacao = situacao;
	}	

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
}
