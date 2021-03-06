package com.senior.test.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.senior.test.domain.Item;
import com.senior.test.domain.enums.TipoItem;
import com.senior.test.dto.ItemDTO;
import com.senior.test.services.ItemService;
import com.senior.test.services.exceptions.DataIntegrityException;
import com.senior.test.services.exceptions.ObjectNotFoundException;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ItemResourceTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ItemService itemService;
	
	private Item item;
	private ItemDTO itemDto;

	@BeforeEach
	void setUp() throws Exception {
		
		UUID id = UUID.randomUUID();
		item = new Item(id, "Item 1", 10.0, TipoItem.PRODUTO, true);
		itemDto = new ItemDTO(item);
		
		when(this.itemService.fromDTO(Mockito.any(ItemDTO.class))).thenReturn(item);
		when(this.itemService.insert(Mockito.any(Item.class))).thenReturn(item);
		when(this.itemService.find(item.getId())).thenReturn(new Item());		
				
	}

	@Test
	void findById_Success() throws Exception {		
		mockMvc.perform(get("/item/{id}", item.getId()))
			.andExpect(status().isOk());
	}
	
	@Test
	void findById_NotFound() throws Exception {
		UUID id = UUID.randomUUID();
		
		when(this.itemService.find(id)).thenThrow(new ObjectNotFoundException(""));
		mockMvc.perform(get("/item/{id}", id))
			.andExpect(status().isNotFound());
	}	
	
	@Test
	void insertItem_Success() throws Exception {		
		mockMvc.perform(post("/item")
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(itemDto)))
		.andExpect(status().isCreated());
	}
	
	@Test
	void insertItem_DescricaoMenorQueTresCaracteres_Error() throws Exception {		
		ItemDTO dto = new ItemDTO(UUID.randomUUID(), "aa", 1.0, 1, true);
		
		MvcResult result = mockMvc.perform(post("/item")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity())
			.andReturn();
		
		String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(response).contains("Campo deve estar entre 3 e 80 caracteres");
		
	}
	
	@Test
	void insertItem_DescricaoMaiorQueOitentaCaracteres_Error() throws Exception {		
		String desc = RandomString.make(81);
		ItemDTO dto = new ItemDTO(UUID.randomUUID(), desc, 1.0, 1, true);
		
		MvcResult result = mockMvc.perform(post("/item")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity())
			.andReturn();
		
		String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(response).contains("Campo deve estar entre 3 e 80 caracteres");
		
	}
	
	@Test
	void insertItem_PrecoMenorQueZero_Error() throws Exception {		
		ItemDTO dto = new ItemDTO(UUID.randomUUID(), "aaaaa", -1.0, 1, true);
		
		MvcResult result = mockMvc.perform(post("/item")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity())
			.andReturn();
		
		String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(response).contains("Pre??o deve ser maior que zero");
		
	}
	
	@Test
	void insertItem_PrecoIgualZero_Error() throws Exception {		
		ItemDTO dto = new ItemDTO(UUID.randomUUID(), "aaaaa", 0.0, 1, true);
		
		MvcResult result = mockMvc.perform(post("/item")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity())
			.andReturn();
		
		String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(response).contains("Pre??o deve ser maior que zero");
		
	}
	
	@Test
	void updateItem_Success() throws Exception {		
		mockMvc.perform(put("/item/{id}", item.getId())
			.contentType("application/json")
			.content(objectMapper.writeValueAsString(itemDto)))
		.andExpect(status().isNoContent());
	}
	
	@Test
	void updateItem_DescricaoMenorQueTresCaracteres_Error() throws Exception {		
		ItemDTO dto = new ItemDTO(UUID.randomUUID(), "aa", 1.0, 1, true);
		
		MvcResult result = mockMvc.perform(put("/item/{id}", item.getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity())
			.andReturn();
		
		String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(response).contains("Campo deve estar entre 3 e 80 caracteres");
		
	}
	
	@Test
	void updateItem_DescricaoMaiorQueOitentaCaracteres_Error() throws Exception {		
		String desc = RandomString.make(81);
		ItemDTO dto = new ItemDTO(UUID.randomUUID(), desc, 1.0, 1, true);
		
		MvcResult result = mockMvc.perform(put("/item/{id}", item.getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity())
			.andReturn();
		
		String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(response).contains("Campo deve estar entre 3 e 80 caracteres");
		
	}
	
	@Test
	void updateItem_PrecoMenorQueZero_Error() throws Exception {		
		ItemDTO dto = new ItemDTO(UUID.randomUUID(), "aaaaa", -1.0, 1, true);
		
		MvcResult result = mockMvc.perform(put("/item/{id}", item.getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity())
			.andReturn();
		
		String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(response).contains("Pre??o deve ser maior que zero");
		
	}
	
	@Test
	void updateItem_PrecoIgualZero_Error() throws Exception {		
		ItemDTO dto = new ItemDTO(UUID.randomUUID(), "aaaaa", 0.0, 1, true);
		
		MvcResult result = mockMvc.perform(put("/item/{id}", item.getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity())
			.andReturn();
		
		String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(response).contains("Pre??o deve ser maior que zero");
		
	}
	
	@Test
	void deleteItem_Success() throws Exception {		
		
		mockMvc.perform(delete("/item/{id}", item.getId()))
				.andExpect(status().isNoContent());			
		
	}
	
	@Test
	void deleteItem_Error() throws Exception {		
		doThrow(new DataIntegrityException("N??o ?? possivel excluir um Item que possui Pedidos")).when(itemService).delete(item.getId());
		MvcResult result = mockMvc.perform(delete("/item/{id}", item.getId()))
			.andExpect(status().isBadRequest())
			.andReturn();
		
		String response = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
		assertThat(response).contains("N??o ?? possivel excluir um Item que possui Pedidos");
	}
}
