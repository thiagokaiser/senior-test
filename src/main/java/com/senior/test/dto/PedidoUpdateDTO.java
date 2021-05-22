package com.senior.test.dto;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.senior.test.services.validation.PedidoUpdate;

@PedidoUpdate
public class PedidoUpdateDTO implements Serializable{	
	private static final long serialVersionUID = 1L;			
	
	private Integer situacao;
	
	@PositiveOrZero(message = "Desconto deve ser positivo")
	@Max(value = 90, message = "Desconto maximo é de 90%")
	private Double desconto;	
	
	@Size(min = 0, max = 60, message = "Observação deve ter no maximo 60 caracteres")
	private String observacao;
	
	public PedidoUpdateDTO(Integer situacao, Double desconto, String observacao) {
		super();		
		this.situacao = situacao;
		this.desconto = desconto;
		this.observacao = observacao;
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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}	
}
