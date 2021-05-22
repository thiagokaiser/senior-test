package com.senior.test.resources;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
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
import com.senior.test.domain.enums.TipoItem;
import com.senior.test.dto.ItemDTO;
import com.senior.test.services.ItemService;
import com.senior.test.services.exceptions.ObjectNotFoundException;

@SpringBootTest
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
		
		mockMvc.perform(post("/item")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity());
		
	}
	
	@Test
	void insertItem_DescricaoMaiorQueOitentaCaracteres_Error() throws Exception {		
		String desc = RandomString.make(81);
		ItemDTO dto = new ItemDTO(UUID.randomUUID(), desc, 1.0, 1, true);
		
		mockMvc.perform(post("/item")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity());
		
	}
	
	@Test
	void insertItem_PrecoMenorQueZero_Error() throws Exception {		
		ItemDTO dto = new ItemDTO(UUID.randomUUID(), "aaaaa", -1.0, 1, true);
		
		mockMvc.perform(post("/item")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity());
		
	}
	
	@Test
	void insertItem_PrecoIgualZero_Error() throws Exception {		
		ItemDTO dto = new ItemDTO(UUID.randomUUID(), "aaaaa", 0.0, 1, true);
		
		mockMvc.perform(post("/item")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isUnprocessableEntity());
		
	}
}
