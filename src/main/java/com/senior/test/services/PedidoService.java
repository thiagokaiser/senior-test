package com.senior.test.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.senior.test.domain.Pedido;
import com.senior.test.domain.enums.SituacaoPedido;
import com.senior.test.dto.PedidoUpdateDTO;
import com.senior.test.repositories.PedidoRepository;
import com.senior.test.services.exceptions.DataIntegrityException;
import com.senior.test.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;		
	
	public Pedido find(UUID id) {		
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setSituacao(SituacaoPedido.ABERTO);
		return repo.save(obj);		
	}
	
	public Pedido update(Pedido obj) {
		Pedido newObj = find(obj.getId());
		updateData(newObj, obj);		
		return repo.save(newObj);
	}
	
	public void delete(UUID id) {
		find(id);
		try {
			repo.deleteById(id);			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir o Pedido.");			
		}		
	}
	
	public List<Pedido> findAll(){		
		return repo.findAll();
	}
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction, String search){		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);		
		return repo.findAll(pageRequest);		
	}	
	
	public Pedido fromDTO(PedidoUpdateDTO objDto) {		
		return new Pedido(objDto);
	}
	
	private void updateData(Pedido newObj, Pedido obj) {
		newObj.setSituacao(obj.getSituacao());
		newObj.setDesconto(obj.getDesconto());
		newObj.setObservacao(obj.getObservacao());
	}	
}
