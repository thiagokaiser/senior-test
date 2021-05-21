package com.senior.test.services;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senior.test.domain.Item;
import com.senior.test.domain.Pedido;
import com.senior.test.domain.enums.TipoItem;
import com.senior.test.dto.ItemPedidoUpdateDTO;
import com.senior.test.dto.PedidoUpdateDTO;

@Service
public class DBService {

	@Autowired
	private ItemService itemService;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ItemPedidoService itemPedidoService;	
	
	public void instantiateTestDatabase() throws ParseException {
		
		Item item1 = itemService.insert(new Item(null,"Item 1", 10.0, TipoItem.PRODUTO, true));
		Item item2 = itemService.insert(new Item(null,"Item 2", 20.0, TipoItem.SERVICO, true));
		itemService.insert(new Item(null,"Item 3", 30.0, TipoItem.PRODUTO, false));		
		
		Pedido pedido1 = pedidoService.insert(new PedidoUpdateDTO(null, 0.0, "pedido 1")); 		
		Pedido pedido2 = pedidoService.insert(new PedidoUpdateDTO(null, 0.0, "pedido 2"));		
		
		itemPedidoService.insert(new ItemPedidoUpdateDTO(pedido1.getId(), item1.getId(), 2));		
		itemPedidoService.insert(new ItemPedidoUpdateDTO(pedido1.getId(), item2.getId(), 1));		
		itemPedidoService.insert(new ItemPedidoUpdateDTO(pedido2.getId(), item1.getId(), 4));		
		itemPedidoService.insert(new ItemPedidoUpdateDTO(pedido2.getId(), item2.getId(), 3));				
		
	}	
}
