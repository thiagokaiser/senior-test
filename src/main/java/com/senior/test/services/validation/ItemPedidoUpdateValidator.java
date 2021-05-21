package com.senior.test.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.senior.test.domain.Item;
import com.senior.test.domain.Pedido;
import com.senior.test.domain.enums.SituacaoPedido;
import com.senior.test.dto.ItemPedidoUpdateDTO;
import com.senior.test.resources.exceptions.FieldMessage;
import com.senior.test.services.ItemService;
import com.senior.test.services.PedidoService;

public class ItemPedidoUpdateValidator implements ConstraintValidator<ItemPedidoUpdate, ItemPedidoUpdateDTO> {	
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ItemService itemService;
	
	@Override
	public void initialize(ItemPedidoUpdate ann) {
	}

	@Override
	public boolean isValid(ItemPedidoUpdateDTO objDto, ConstraintValidatorContext context) {		
		
		List<FieldMessage> list = new ArrayList<>();
				
		Pedido pedido = pedidoService.find(objDto.getIdPedido());		
		if (!pedido.getSituacao().equals(SituacaoPedido.ABERTO)) {
			list.add(new FieldMessage("idPedido", "Pedido não está ABERTO"));			
		}
		
		Item item = itemService.find(objDto.getIdItem());
		if(item.getAtivo() == false) {
			list.add(new FieldMessage("idItem", "Item não esta ativo"));
		}		

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}