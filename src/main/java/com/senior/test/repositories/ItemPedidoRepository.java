package com.senior.test.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.senior.test.domain.ItemPedido;
import com.senior.test.domain.ItemPedidoPK;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPK> {

	@Transactional(readOnly = true)	
	List<ItemPedido> findById_Pedido_Id(UUID idPedido);		

}
