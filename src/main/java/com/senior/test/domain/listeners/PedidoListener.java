package com.senior.test.domain.listeners;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.springframework.stereotype.Component;

import com.senior.test.domain.Pedido;

@Component
public class PedidoListener {
	
	
	@PostPersist
	@PostUpdate
	@PostRemove
	private void updateTotalPedido(Pedido pedido) {
		pedido.setTotal(999.9);
		//service.update(pedido);
	}
	
}
