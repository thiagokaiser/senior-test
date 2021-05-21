package com.senior.test.services;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senior.test.domain.Item;
import com.senior.test.domain.ItemPedido;
import com.senior.test.domain.Pedido;
import com.senior.test.domain.enums.SituacaoPedido;
import com.senior.test.domain.enums.TipoItem;
import com.senior.test.repositories.ItemPedidoRepository;
import com.senior.test.repositories.ItemRepository;
import com.senior.test.repositories.PedidoRepository;

@Service
public class DBService {

	@Autowired
	private ItemRepository itemRepo;
	
	@Autowired
	private PedidoRepository pedidoRepo;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepo;	
	
	public void instantiateTestDatabase() throws ParseException {
		
		Item item1 = new Item(null,"Item 1", 10.0, TipoItem.PRODUTO, true);
		Item item2 = new Item(null,"Item 2", 20.0, TipoItem.SERVICO, true);
		Item item3 = new Item(null,"Item 3", 30.0, TipoItem.PRODUTO, false);		
		itemRepo.saveAll(Arrays.asList(item1, item2, item3));		
		
		
		Pedido pedido1 = new Pedido(null, new Date(), SituacaoPedido.ABERTO.getCod(), 0.0, 0.0, 0.0, 0.0, ""); 		
		Pedido pedido2 = new Pedido(null, new Date(), SituacaoPedido.ABERTO.getCod(), 0.0, 0.0, 0.0, 0.0, "");		
		pedidoRepo.saveAll(Arrays.asList(pedido1, pedido2));
		
		ItemPedido itemPedido1 = new ItemPedido(pedido1, item1, 2, 0.0, 0.0);
		ItemPedido itemPedido2 = new ItemPedido(pedido1, item2, 2, 0.0, 0.0);
		ItemPedido itemPedido3 = new ItemPedido(pedido2, item1, 3, 0.0, 0.0);
		ItemPedido itemPedido4 = new ItemPedido(pedido2, item2, 6, 0.0, 0.0);
		itemPedidoRepo.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3, itemPedido4));		
		
	}	
}
