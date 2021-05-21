package com.senior.test.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.senior.test.domain.Item;
import com.senior.test.domain.ItemPedido;
import com.senior.test.domain.Pedido;
import com.senior.test.domain.enums.TipoItem;
import com.senior.test.repositories.PedidoRepository;

@ExtendWith(SpringExtension.class)
class PedidoServiceTest {

	@Autowired
	private PedidoService pedidoService;	
	
	@MockBean
	private PedidoRepository pedidoRepository;	
	
	@TestConfiguration
	static class PedidoServiceTestConfiguration{
		@Bean
		public PedidoService pedidoService() {
			return new PedidoService();
		}		
	}
	
	private Pedido pedido1 = new Pedido();
	
	@BeforeEach
	void setUp() throws Exception {
		Item item1 = new Item(UUID.randomUUID(),"Item 1", 10.0, TipoItem.PRODUTO, true);
		Item item2 = new Item(UUID.randomUUID(),"Item 2", 20.0, TipoItem.SERVICO, true);		
		
		pedido1 = new Pedido(UUID.randomUUID(), new Date(), 1, 10.0, 0.0, 0.0, 0.0, null);				
		
		ItemPedido itemPedido1 = new ItemPedido(pedido1, item1, 2, 10.0, 0.0);		
		ItemPedido itemPedido2 = new ItemPedido(pedido1, item2, 3, 20.0, 0.0);				
		
		pedido1.addItem(itemPedido1);
		pedido1.addItem(itemPedido2);
		
		Mockito.when(pedidoRepository.findById(pedido1.getId())).thenReturn(Optional.of(pedido1));		
		Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class)))
        	.thenAnswer(i -> i.getArguments()[0]);
		
	}
	
	@Test
	void updateTotaisTest() {		
		pedidoService.updateOnlyTotais(pedido1);
		assertEquals(78, pedido1.getTotal());
		assertEquals(18, pedido1.getTotalProduto());
		assertEquals(60, pedido1.getTotalServico());
	}
	
	
	

}
