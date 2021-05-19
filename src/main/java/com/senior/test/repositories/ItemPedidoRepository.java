package com.senior.test.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.senior.test.domain.Item;
import com.senior.test.domain.ItemPedido;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

	Optional<Item> findById(UUID id);

	void deleteById(UUID id);	

}
