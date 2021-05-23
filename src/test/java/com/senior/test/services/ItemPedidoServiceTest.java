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
import com.senior.test.domain.ItemPedidoPK;
import com.senior.test.domain.Pedido;
import com.senior.test.domain.enums.TipoItem;
import com.senior.test.dto.ItemPedidoUpdateDTO;
import com.senior.test.repositories.ItemPedidoRepository;

@ExtendWith(SpringExtension.class)
class ItemPedidoServiceTest {

	@Autowired
	private ItemPedidoService itemPedidoService;	
	
	@MockBean
	private ItemPedidoRepository itemPedidoRepository;
	
	@MockBean
	private PedidoService pedidoService;
	
	@MockBean
	private ItemService itemService;
	
	@TestConfiguration
	static class ItemPedidoServiceTestConfiguration{
		@Bean
		public ItemPedidoService itemPedidoService() {
			return new ItemPedidoService();
		}		
	}
	
	private ItemPedidoPK id = new ItemPedidoPK();
	private ItemPedido itemPedido;
	
	@BeforeEach
	void setUp() throws Exception {
		Item item = new Item(UUID.randomUUID(),"Item 1", 10.0, TipoItem.PRODUTO, true);		
		Pedido pedido = new Pedido(UUID.randomUUID(), new Date(), 1, 10.0, 0.0, 0.0, 0.0, null);		
		itemPedido = new ItemPedido(pedido, item, 2, 10.0, 0.0);		
		pedido.addItem(itemPedido);
		
		id.setItem(item);
		id.setPedido(pedido);
		
		Mockito.when(itemPedidoRepository.findById(id))
			.thenReturn(Optional.of(itemPedido));
		
		Mockito.when(itemPedidoRepository.save(Mockito.any(ItemPedido.class)))
        	.thenAnswer(i -> i.getArguments()[0]);
		
		Mockito.when(itemService.find(item.getId())).thenReturn(item);
		Mockito.when(pedidoService.find(pedido.getId())).thenReturn(pedido);
		
	}	
		
	@Test
	void findItemPedido_Success() {
		ItemPedido itemPedidoFind = itemPedidoService.find(id);
		assertEquals(itemPedidoFind, itemPedido);		
	}
	
	@Test
	void findItemPedido_NotFount() {
		
		Item item = new Item(UUID.randomUUID(),"Item 2", 10.0, TipoItem.PRODUTO, true);		
		id.setItem(item);
		
		when(itemPedidoRepository.findById(id))
			.thenReturn(Optional.ofNullable(null));		
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			itemPedidoService.find(id);
	    });	    

	    assertThat(exception.getMessage()).contains("Objeto não encontrado");		
	}
	
	@Test
	void insertItemPedido_Success() {
		ItemPedidoUpdateDTO dto = new ItemPedidoUpdateDTO(id.getPedido().getId(), id.getItem().getId(), 1);
		itemPedidoService.insert(dto);
		verify(itemPedidoRepository).save(Mockito.any(ItemPedido.class));
	}
	
	@Test
	void updateItemPedido_Success(){
		ItemPedido itemPedidoSaved = itemPedidoService.update(itemPedido);
		assertThat(itemPedido).isEqualTo(itemPedidoSaved);		
	}
	
	@Test
	void updateItemPedido_AlteraQuantidadeRecalculaTotal_Success(){
		itemPedido.setQuantidade(6);
		ItemPedido itemPedidoSaved = itemPedidoService.update(itemPedido);
		assertThat(itemPedido).isEqualTo(itemPedidoSaved);
		assertEquals(60, itemPedido.getTotal());		
	}
	
	@Test
	void deleteItemPedido_Success() {
		itemPedidoService.delete(itemPedido.getId());
		verify(itemPedidoRepository).deleteById(itemPedido.getId());
	}
}
