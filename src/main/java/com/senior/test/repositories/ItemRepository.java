package com.senior.test.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.senior.test.domain.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	Optional<Item> findById(UUID id);

	void deleteById(UUID id);
	
	@Transactional(readOnly = true)
	Page<Item> findByDescricaoContaining(String search, Pageable pageRequest);

}
