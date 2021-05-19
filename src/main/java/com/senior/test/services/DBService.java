package com.senior.test.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.senior.test.domain.Item;
import com.senior.test.repositories.ItemRepository;

@Service
public class DBService {

	@Autowired
	private ItemRepository itemRepo;
	
	public void instantiateTestDatabase() throws ParseException {
		
		Item item1 = new Item(null,"Item 1", 10.0);
		Item item2 = new Item(null,"Item 2", 20.0);
		Item item3 = new Item(null,"Item 3", 30.0);
		
		itemRepo.saveAll(Arrays.asList(item1, item2, item3));
	}	
}
