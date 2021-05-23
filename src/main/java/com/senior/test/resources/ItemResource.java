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

import com.senior.test.domain.Item;
import com.senior.test.dto.ItemDTO;
import com.senior.test.services.ItemService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value="/item")
public class ItemResource {
	
	@Autowired
	private ItemService service;	
	
	@ApiOperation(value = "Busca por id")	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Item> find(@PathVariable UUID id) {
		
		Item obj = service.find(id);		
		return ResponseEntity.ok().body(obj);		
		
	}
	
	@ApiOperation(value = "Insere Item")	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ItemDTO objDto){		
		Item obj = service.fromDTO(objDto);		
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation(value = "Atualiza Item")	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ItemDTO objDto, @PathVariable UUID id){
		Item obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);		
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Remove Item")
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Não é possível excluir um Item que possui Pedidos"),
			@ApiResponse(code = 404, message = "Código inexistente") })	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable UUID id) {
		
		service.delete(id);		
		return ResponseEntity.noContent().build();		
		
	}
	
	@ApiOperation(value = "Retorna todos Itens")	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<ItemDTO>> findAll() {
		
		List<Item> list = service.findAll();
		List<ItemDTO> listDto = list.stream().map(obj -> new ItemDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);		
		
	}
	
	@ApiOperation(value = "Retorna todos itens com paginação")	
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public ResponseEntity<Page<ItemDTO>> findPage(
			@RequestParam(value="page", defaultValue = "0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue = "descricao") String orderBy, 
			@RequestParam(value="direction", defaultValue = "DESC") String direction,
			@RequestParam(value="search", defaultValue = "") String search) {
		
		Page<Item> list = service.findPage(page, linesPerPage, orderBy, direction, search);
		Page<ItemDTO> listDto = list.map(obj -> new ItemDTO(obj));
		return ResponseEntity.ok().body(listDto);		
		
	}
	
	

}
