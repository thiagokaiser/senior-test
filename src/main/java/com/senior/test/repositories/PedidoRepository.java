package com.senior.test.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senior.test.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
	

}
