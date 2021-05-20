package com.senior.test.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import com.senior.test.services.validation.PedidoUpdate;

@PedidoUpdate
public class PedidoUpdateDTO implements Serializable{	
	private static final long serialVersionUID = 1L;

	private UUID id;	
	private Date instante;	
	private Integer situacao;
	private Double desconto;
	private Double total;
	
	public PedidoUpdateDTO(UUID id, Date instante, Integer situacao, Double desconto, Double total) {
		super();
		this.id = id;
		this.instante = instante;
		this.situacao = situacao;
		this.desconto = desconto;
		this.total = total;
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
}
