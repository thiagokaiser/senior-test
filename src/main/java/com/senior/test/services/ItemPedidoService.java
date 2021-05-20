package com.senior.test.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.senior.test.domain.Item;
import com.senior.test.domain.ItemPedido;
import com.senior.test.domain.ItemPedidoPK;
import com.senior.test.domain.Pedido;
import com.senior.test.dto.ItemPedidoDTO;
import com.senior.test.dto.ItemPedidoUpdateDTO;
import com.senior.test.repositories.ItemPedidoRepository;
import com.senior.test.services.exceptions.DataIntegrityException;
import com.senior.test.services.exceptions.ObjectNotFoundException;

@Service
public class ItemPedidoService {

	@Autowired
	private ItemPedidoRepository itemPedidoRepo;
	
	@Autowired
	private PedidoService pedidoService;		
	
	@Autowired
	private ItemService itemService;	
	
	
	public ItemPedido find(ItemPedidoPK id) {				
		Optional<ItemPedido> obj = itemPedidoRepo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + ItemPedido.class.getName()));
	}

	public ItemPedido insert(ItemPedido obj) {
		Pedido pedido = pedidoService.find(obj.getPedido().getId());
		obj.setPedido(pedido);
		return itemPedidoRepo.save(obj);		
	}
	
	public ItemPedido update(ItemPedido obj) {
		ItemPedido newObj = find(obj.getId());
		updateData(newObj, obj);		
		return itemPedidoRepo.save(newObj);
	}
	
	public void delete(ItemPedidoPK id) {
		find(id);
		try {
			itemPedidoRepo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir o Item do Pedido.");			
		}		
	}
	
	public List<ItemPedido> findAll(){		
		return itemPedidoRepo.findAll();
	}
	
	public List<ItemPedido> findByPedido(UUID idPedido){		
		return itemPedidoRepo.findById_Pedido_Id(idPedido);
	}
	
	public Page<ItemPedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction, String search){		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);		
		return itemPedidoRepo.findAll(pageRequest);		
	}
	
	public ItemPedido fromDTO(ItemPedidoDTO objDto) {		
		return new ItemPedido(objDto.getPedido(), objDto.getItem(), objDto.getQuantidade());
	}	
	
	public ItemPedido fromDTO(ItemPedidoUpdateDTO objDto) {
		Pedido pedido = pedidoService.find(objDto.getIdPedido());		
		Item item = itemService.find(objDto.getIdItem());		
		return new ItemPedido(pedido, item, objDto.getQuantidade());
	}
	
	public ItemPedidoPK setItemPedidoPK(UUID idPedido, UUID idItem) {
		ItemPedidoPK id = new ItemPedidoPK();
		id.setPedido(pedidoService.find(idPedido));
		id.setItem(itemService.find(idItem));
		return id;		
	}	
	
	private void updateData(ItemPedido newObj, ItemPedido obj) {
		newObj.setQuantidade(obj.getQuantidade());
	}
	
}