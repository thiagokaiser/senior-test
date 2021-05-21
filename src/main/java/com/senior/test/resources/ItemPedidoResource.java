package com.senior.test.resources;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.senior.test.domain.ItemPedido;
import com.senior.test.domain.ItemPedidoPK;
import com.senior.test.dto.ItemPedidoDTO;
import com.senior.test.dto.ItemPedidoUpdateDTO;
import com.senior.test.services.ItemPedidoService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/itemPedido")
public class ItemPedidoResource {
	
	@Autowired
	private ItemPedidoService service;	
	
	@ApiOperation(value = "Busca por id")	
	@RequestMapping(value="/{idPedido}/{idItem}", method=RequestMethod.GET)
	public ResponseEntity<ItemPedido> find(@PathVariable UUID idPedido, @PathVariable UUID idItem) {
		
		ItemPedidoPK id = service.setItemPedidoPK(idPedido, idItem);
		ItemPedido obj = service.find(id);		
		return ResponseEntity.ok().body(obj);
		
	}
	
	@ApiOperation(value = "Insere ItemPedido")	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ItemPedidoUpdateDTO objDto){				
		ItemPedido obj = service.insert(objDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId().getItem().getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value = "Atualiza ItemPedido")	
	@RequestMapping(value = "/{idPedido}/{idItem}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ItemPedidoUpdateDTO objDto, @PathVariable UUID idPedido, @PathVariable UUID idItem){		
		ItemPedido obj = service.fromDTO(objDto);		
		obj = service.update(obj);		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Remove ItemPedido")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Não é possível excluir o item do pedido"),
			@ApiResponse(code = 404, message = "Código inexistente") })	
	@RequestMapping(value="/{idPedido}/{idItem}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable UUID idPedido, @PathVariable UUID idItem) {
		
		ItemPedidoPK id = service.setItemPedidoPK(idPedido, idItem);
		service.delete(id);		
		return ResponseEntity.noContent().build();		
		
	}
	
	@ApiOperation(value = "Retorna todos Itens do pedido")	
	@RequestMapping(value = "/{idPedido}", method=RequestMethod.GET)
	public ResponseEntity<List<ItemPedidoDTO>> findByPedido(@PathVariable UUID idPedido) {
		
		List<ItemPedido> list = service.findByPedido(idPedido);
		List<ItemPedidoDTO> listDto = list.stream().map(obj -> new ItemPedidoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);		
		
	}
	
	@ApiOperation(value = "Retorna todos itens do pedido com paginação")	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ItemPedidoDTO>> findPage(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "instante") String orderBy, 
			@RequestParam(value="direction", defaultValue = "DESC") String direction,
			@RequestParam(value="search", defaultValue = "") String search) {
		
		Page<ItemPedido> list = service.findPage(page, linesPerPage, orderBy, direction, search);
		Page<ItemPedidoDTO> listDto = list.map(obj -> new ItemPedidoDTO(obj));
		return ResponseEntity.ok().body(listDto);		
		
	}	
	

}
