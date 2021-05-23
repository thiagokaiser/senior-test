package com.senior.test.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.senior.test.dto.PedidoUpdateDTO;
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
	
	private Pedido pedido = new Pedido();
	
	@BeforeEach
	void setUp() throws Exception {
		Item item1 = new Item(UUID.randomUUID(),"Item 1", 10.0, TipoItem.PRODUTO, true);
		Item item2 = new Item(UUID.randomUUID(),"Item 2", 20.0, TipoItem.SERVICO, true);		
		
		pedido = new Pedido(UUID.randomUUID(), new Date(), 1, 10.0, 0.0, 0.0, 0.0, null);				
		
		ItemPedido itemPedido1 = new ItemPedido(pedido, item1, 2, 10.0, 0.0);		
		ItemPedido itemPedido2 = new ItemPedido(pedido, item2, 3, 20.0, 0.0);				
		
		pedido.addItem(itemPedido1);
		pedido.addItem(itemPedido2);
		
		Mockito.when(pedidoRepository.findById(pedido.getId()))
			.thenReturn(Optional.of(pedido));
		
		Mockito.when(pedidoRepository.save(Mockito.any(Pedido.class)))
        	.thenAnswer(i -> i.getArguments()[0]);
		
	}
	
	@Test
	void updateTotaisTest() {		
		pedidoService.updateAndSaveTotais(pedido);
		assertEquals(78, pedido.getTotal());
		assertEquals(18, pedido.getTotalProduto());
		assertEquals(60, pedido.getTotalServico());
	}
	
	@Test
	void findPedido_Success() {
		Pedido pedidoFind = pedidoService.find(pedido.getId());
		assertEquals(pedidoFind, pedido);		
	}
	
	@Test
	void findPedido_NotFount() {
		
		UUID id = UUID.randomUUID();
		when(pedidoRepository.findById(id))
			.thenReturn(Optional.ofNullable(null));		
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			pedidoService.find(id);
	    });	    

	    assertThat(exception.getMessage()).contains("Objeto não encontrado");		
	}
	
	@Test
	void insertPedido_Success() {
		PedidoUpdateDTO dto = new PedidoUpdateDTO(1, 1.0, "");
		pedidoService.insert(dto);
		verify(pedidoRepository).save(Mockito.any(Pedido.class));
	}
	
	@Test
	void updatePedido_Success(){
		Pedido pedidoSaved = pedidoService.update(pedido);
		assertThat(pedido).isEqualTo(pedidoSaved);		
	}
	
	@Test
	void updatePedido_AlteraDescontoRecalculaTotais_Success(){
		pedido.setDesconto(50.0);
		Pedido pedidoSaved = pedidoService.update(pedido);
		assertThat(pedido).isEqualTo(pedidoSaved);
		assertEquals(70, pedido.getTotal());
		assertEquals(10, pedido.getTotalProduto());
		assertEquals(60, pedido.getTotalServico());
	}
	
	@Test
	void deletePedido_Success() {
		pedidoService.delete(pedido.getId());
		verify(pedidoRepository).deleteById(pedido.getId());
	}
}
