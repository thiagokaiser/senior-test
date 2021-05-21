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

import com.senior.test.domain.ItemPedido;
import com.senior.test.domain.Pedido;
import com.senior.test.domain.enums.SituacaoPedido;
import com.senior.test.domain.enums.TipoItem;
import com.senior.test.dto.PedidoUpdateDTO;
import com.senior.test.repositories.PedidoRepository;
import com.senior.test.services.exceptions.DataIntegrityException;
import com.senior.test.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired 
	private ItemPedidoService itemPedidoService;
	
	public Pedido find(UUID id) {		
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	public Pedido insert(PedidoUpdateDTO objDto) {
		Pedido obj = fromDTO(objDto);
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setSituacao(SituacaoPedido.ABERTO);
		obj.setTotal(0.0);
		obj.setTotalServico(0.0);
		obj.setTotalProduto(0.0);
		return repo.save(obj);		
	}
	
	public Pedido update(Pedido obj) {		
		Pedido newObj = find(obj.getId());
		updateData(newObj, obj);
		newObj = repo.save(newObj);
		updateTotais(newObj);
		return newObj;
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
	
	public void updateTotais(Pedido obj) {
		Double totalProduto = 0.0;
		Double totalServico = 0.0;		
		
		List<ItemPedido> itens = itemPedidoService.findByPedido(obj.getId());
		
		for (ItemPedido itemPedido : itens) {
			Double totalItem = itemPedido.getPreco() * itemPedido.getQuantidade();			
			if(itemPedido.getItem().getTipo().equals(TipoItem.SERVICO)) {
				totalServico += totalItem;
			}else if(itemPedido.getItem().getTipo().equals(TipoItem.PRODUTO)) {
				totalProduto += totalItem;
			}			
		}
		
		if(!totalProduto.equals(0.0)) {
			totalProduto = totalProduto * (1 - obj.getDesconto() / 100);
		}
		
		obj.setTotal(totalProduto + totalServico);
		obj.setTotalProduto(totalProduto);
		obj.setTotalServico(totalServico);		
		repo.save(obj);
	}
}
