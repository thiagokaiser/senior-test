package com.senior.test.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.senior.test.domain.Item;
import com.senior.test.domain.enums.TipoItem;
import com.senior.test.repositories.ItemRepository;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class ItemServiceTest {

	@Autowired
	private ItemService itemService;	
	
	@MockBean
	private ItemRepository itemRepository;	
	
	@TestConfiguration
	static class PedidoServiceTestConfiguration{
		@Bean
		public ItemService itemService() {
			return new ItemService();
		}		
	}
	
	private Item item;	
	
	@BeforeEach
	void setUp() throws Exception {
		item = new Item(UUID.randomUUID(),"Item 1", 10.0, TipoItem.PRODUTO, true);				
		
		when(itemRepository.findById(item.getId()))
			.thenReturn(Optional.of(item));
		
		when(itemRepository.save(Mockito.any(Item.class)))
        	.thenAnswer(i -> i.getArguments()[0]);
		
	}
	
	@Test
	void findItem_Success() {
		Item itemFind = itemService.find(item.getId());
		assertEquals(itemFind, item);		
	}
	
	@Test
	void findItem_NotFount() {
		
		UUID id = UUID.randomUUID();
		when(itemRepository.findById(id))
			.thenReturn(Optional.ofNullable(null));		
		
		Exception exception = assertThrows(RuntimeException.class, () -> {
			itemService.find(id);
	    });	    

	    assertThat(exception.getMessage()).contains("Objeto não encontrado");		
	}
	
	@Test
	void insertItem_Success() {
		Item itemSaved = itemService.insert(item);
		assertThat(item).isEqualTo(itemSaved);
	}
	
	@Test
	void updateItem_Success(){
		Item itemSaved = itemService.update(item);
		assertThat(item).isEqualTo(itemSaved);
		
	}
	
	@Test
	void deleteItem_Success() {
		itemService.delete(item.getId());
		verify(itemRepository).deleteById(item.getId());
	}
	
	@Test
	void deleteItem_ItemPossuiPedido_Error() {
		doThrow(new DataIntegrityViolationException("")).when(itemRepository).deleteById(item.getId());
		Exception exception = assertThrows(RuntimeException.class, () -> {
			itemService.delete(item.getId());
	    });	    

	    assertThat(exception.getMessage()).contains("Não é possivel excluir um Item que possui Pedidos");		
	}
	
	

}
