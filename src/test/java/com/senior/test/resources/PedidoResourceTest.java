package com.senior.test.resources;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senior.test.domain.Item;
import com.senior.test.domain.ItemPedido;
import com.senior.test.domain.Pedido;
import com.senior.test.domain.enums.TipoItem;
import com.senior.test.dto.PedidoUpdateDTO;
import com.senior.test.services.PedidoService;
import com.senior.test.services.exceptions.ObjectNotFoundException;

import net.bytebuddy.utility.RandomString;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoResourceTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private PedidoService pedidoService;
	
	private Pedido pedido;

	@BeforeEach
	void setUp() throws Exception {
		
		Item item1 = new Item(UUID.randomUUID(),"Item 1", 10.0, TipoItem.PRODUTO, true);
		Item item2 = new Item(UUID.randomUUID(),"Item 2", 20.0, TipoItem.SERVICO, true);		
		
		pedido = new Pedido(UUID.randomUUID(), new Date(), 1, 10.0, 0.0, 0.0, 0.0, null);				
		
		ItemPedido itemPedido1 = new ItemPedido(pedido, item1, 2, 10.0, 0.0);		
		ItemPedido itemPedido2 = new ItemPedido(pedido, item2, 3, 20.0, 0.0);				
		
		pedido.addItem(itemPedido1);
		pedido.addItem(itemPedido2);
		
		when(pedidoService.find(pedido.getId())).thenReturn(pedido);		
		when(pedidoService.fromDTO(Mockito.any(PedidoUpdateDTO.class))).thenReturn(pedido);
		when(pedidoService.insert(Mockito.any(PedidoUpdateDTO.class))).thenReturn(pedido);				
	}

	@Test
	void findById_Success() throws Exception {		
		mockMvc.perform(get("/pedido/{id}", pedido.getId()))
			.andExpect(status().isOk());
	}
	
	@Test
	void findById_NotFound() throws Exception {
		
		UUID id = UUID.randomUUID();
		
		when(pedidoService.find(id)).thenThrow(new ObjectNotFoundException(""));
		mockMvc.perform(get("/pedido/{id}", id))
			.andExpect(status().isNotFound());
	}
	
	@Test
	void insertPedido_Success() throws Exception {		
		
		PedidoUpdateDTO dto = new PedidoUpdateDTO(1, 0.0, "");
		
		mockMvc.perform(post("/pedido")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isCreated());
	}
	
	@Test
	void insertPedido_DescontoMenorQueZero_Error() throws Exception {
		
		PedidoUpdateDTO dto = new PedidoUpdateDTO(1, -10.0, "");
		
		mockMvc.perform(post("/item")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity());
		
	}
	
	@Test
	void insertPedido_DescontoMaiorQueNoventa_Error() throws Exception {
		
		PedidoUpdateDTO dto = new PedidoUpdateDTO(1, 91.0, "");
		
		mockMvc.perform(post("/item")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity());
		
	}
	
	@Test
	void insertPedido_ObservacaoMaiorQueSessenta_Error() throws Exception {
		String obs = RandomString.make(61);
		PedidoUpdateDTO dto = new PedidoUpdateDTO(1, 91.0, obs);		
		
		mockMvc.perform(post("/item")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity());
		
	}
}