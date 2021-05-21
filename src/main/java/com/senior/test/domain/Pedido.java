package com.senior.test.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.senior.test.domain.enums.SituacaoPedido;
import com.senior.test.dto.PedidoUpdateDTO;

@Entity
public class Pedido implements Serializable{	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private UUID id;	
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private Date instante;
	
	private Integer situacao;	
	private Double desconto;
	private Double totalServico;
	private Double totalProduto;
	private Double total;
	private String observacao;
	
	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();

	public Pedido() {
	}		
	
	public Pedido(UUID id, Date instante, Integer situacao, Double desconto, Double totalServico, Double totalProduto, Double total, String observacao) {		
		this.id = id;
		this.instante = instante;
		this.situacao = situacao;
		this.desconto = desconto;
		this.totalServico = totalServico;		
		this.totalProduto = totalProduto;
		this.total = total;
		this.observacao = observacao;
	}
	
	public Pedido(PedidoUpdateDTO obj) {
		this.situacao = obj.getSituacao();
		this.desconto = obj.getDesconto();
		this.observacao = obj.getObservacao();
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

	public SituacaoPedido getSituacao() {
		return SituacaoPedido.toEnum(situacao);
	}

	public void setSituacao(SituacaoPedido situacao) {
		this.situacao = situacao.getCod();
	}	

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}	

	public Double getTotalServico() {
		return totalServico;
	}

	public void setTotalServico(Double totalServico) {
		this.totalServico = totalServico;
	}

	public Double getTotalProduto() {
		return totalProduto;
	}

	public void setTotalProduto(Double totalProduto) {
		this.totalProduto = totalProduto;
	}
	
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	
	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void addItem(ItemPedido itemPedido) {
		this.itens.add(itemPedido);
	}
	
	public void removeItem(ItemPedido itemPedido) {
		this.itens.remove(itemPedido);
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	

}
