package com.senior.test.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senior.test.domain.Item;
import com.senior.test.domain.ItemPedido;
import com.senior.test.domain.ItemPedidoPK;
import com.senior.test.domain.Pedido;
import com.senior.test.domain.enums.TipoItem;
import com.senior.test.dto.ItemPedidoUpdateDTO;
import com.senior.test.services.ItemPedidoService;
import com.senior.test.services.ItemService;
import com.senior.test.services.PedidoService;
import com.senior.test.services.exceptions.ObjectNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
class ItemPedidoResourceTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ItemPedidoService itemPedidoService;
	
	@MockBean
	private ItemService itemService;
	
	@MockBean 
	private PedidoService pedidoService;
	
	private ItemPedidoPK id = new ItemPedidoPK();

	@BeforeEach
	void setUp() throws Exception {
		
		Item item = new Item(UUID.randomUUID(),"Item 1", 10.0, TipoItem.PRODUTO, true);		
		Pedido pedido = new Pedido(UUID.randomUUID(), new Date(), 1, 10.0, 0.0, 0.0, 0.0, null);		
		ItemPedido itemPedido1 = new ItemPedido(pedido, item, 2, 10.0, 0.0);					
		
		id.setItem(item);
		id.setPedido(pedido);
		
		when(itemPedidoService.find(id)).thenReturn(itemPedido1);		
		when(itemPedidoService.fromDTO(Mockito.any(ItemPedidoUpdateDTO.class))).thenReturn(itemPedido1);
		when(itemPedidoService.insert(Mockito.any(ItemPedidoUpdateDTO.class))).thenReturn(itemPedido1);
		when(itemPedidoService.setItemPedidoPK(id.getPedido().getId(), id.getItem().getId())).thenReturn(id);
		when(pedidoService.find(pedido.getId())).thenReturn(pedido);
		when(itemService.find(item.getId())).thenReturn(item);
	}

	@Test
	void findById_Success() throws Exception {		
		mockMvc.perform(get("/itemPedido/{idPedido}/{idItem}", id.getPedido().getId(), id.getItem().getId()))
			.andExpect(status().isOk());
	}
	
	@Test
	void findById_NotFound() throws Exception {		
		Item item = new Item(UUID.randomUUID(),"Item 1", 10.0, TipoItem.PRODUTO, true);		
		Pedido pedido = new Pedido(UUID.randomUUID(), new Date(), 1, 10.0, 0.0, 0.0, 0.0, null);							
		
		ItemPedidoPK id = new ItemPedidoPK();		
		id.setItem(item);
		id.setPedido(pedido);		
		
		when(itemPedidoService.setItemPedidoPK(id.getPedido().getId(), id.getItem().getId())).thenReturn(id);
		when(itemPedidoService.find(id)).thenThrow(new ObjectNotFoundException(""));
		
		mockMvc.perform(get("/itemPedido/{idPedido}/{idItem}", id.getPedido().getId(), id.getItem().getId()))
			.andExpect(status().isNotFound());
	}
	
	@Test
	void insertItemPedido_Success() throws Exception {		
		
		ItemPedidoUpdateDTO dto = new ItemPedidoUpdateDTO(id.getPedido().getId(), id.getItem().getId(), 1);
		
		mockMvc.perform(post("/itemPedido")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isCreated());
	}
	
	@Test
	void insertItemPedido_QuantidadeMenorIgualZero_Error() throws Exception {
		
		ItemPedidoUpdateDTO dto = new ItemPedidoUpdateDTO(id.getPedido().getId(), id.getItem().getId(), 0);
		
		MvcResult result = mockMvc.perform(post("/itemPedido")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isUnprocessableEntity())
		.andReturn();
		
		String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(response).contains("Quantidade deve ser maior que zero");
		
	}
}
