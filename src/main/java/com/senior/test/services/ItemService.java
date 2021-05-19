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
import com.senior.test.dto.ItemDTO;
import com.senior.test.repositories.ItemRepository;
import com.senior.test.services.exceptions.DataIntegrityException;
import com.senior.test.services.exceptions.ObjectNotFoundException;

@Service
public class ItemService {

	@Autowired
	private ItemRepository repo;		
	
	public Item find(UUID id) {		
		Optional<Item> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Item.class.getName()));
	}

	public Item insert(Item obj) {
		obj.setId(null);
		return repo.save(obj);		
	}
	
	public Item update(Item obj) {
		Item newObj = find(obj.getId());
		updateData(newObj, obj);		
		return repo.save(newObj);
	}
	
	public void delete(UUID id) {
		find(id);
		try {
			repo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um Item que possui Pedidos.");			
		}		
	}
	
	public List<Item> findAll(){		
		return repo.findAll();
	}
	
	public Page<Item> findPage(Integer page, Integer linesPerPage, String orderBy, String direction, String search){		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);		
		return repo.findByDescricaoContaining(search, pageRequest);		
	}	
	
	public Item fromDTO(ItemDTO objDto) {		
		return new Item(objDto.getId(), objDto.getDescricao(), objDto.getPreco());
	}
	
	private void updateData(Item newObj, Item obj) {
		newObj.setDescricao(obj.getDescricao());
	}
	
	
}
