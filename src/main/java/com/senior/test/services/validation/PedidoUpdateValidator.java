package com.senior.test.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.senior.test.domain.Pedido;
import com.senior.test.domain.enums.SituacaoPedido;
import com.senior.test.dto.PedidoUpdateDTO;
import com.senior.test.resources.exceptions.FieldMessage;
import com.senior.test.services.PedidoService;

public class PedidoUpdateValidator implements ConstraintValidator<PedidoUpdate, PedidoUpdateDTO> {	
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private PedidoService pedidoService;
	
	@Override
	public void initialize(PedidoUpdate ann) {
	}

	@Override
	public boolean isValid(PedidoUpdateDTO objDto, ConstraintValidatorContext context) {		
		
		
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		String uriId = map.get("id");
		if(uriId == null) {
			return true;
		}
		
		List<FieldMessage> list = new ArrayList<>();
				
		Pedido pedido = pedidoService.find(UUID.fromString(uriId));
		
		if (!pedido.getSituacao().equals(SituacaoPedido.ABERTO)) {
			list.add(new FieldMessage("situacao", "Pedido não está ABERTO"));			
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}