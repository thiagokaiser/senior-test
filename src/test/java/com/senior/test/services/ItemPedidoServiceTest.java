package com.senior.test.services;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.senior.test.repositories.ItemPedidoRepository;

class ItemPedidoServiceTest {

	@Autowired
	private ItemPedidoService itemPedidoService;	
	
	@MockBean
	private ItemPedidoRepository itemPedidoRepository;	
	
	@TestConfiguration
	static class ItemPedidoServiceTestConfiguration{
		@Bean
		public ItemPedidoService itemPedidoService() {
			return new ItemPedidoService();
		}		
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
