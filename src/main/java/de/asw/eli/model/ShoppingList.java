package de.asw.eli.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class ShoppingList {

	public String id;
	public String name;
	public List<ShoppingListItem> items = new ArrayList<>();
	
	public ShoppingList() {
	}

	public ShoppingList(String name) {
		this.name = name;
		
		this.id = UUID.randomUUID().toString();
	}

	// Utility
	public void addItem(ShoppingListItem item) {
		items.add(item);
	}

	public void removeItem(String itemId) {
		items
			.removeIf(item -> item.getId()
			.equals(itemId));
	}
	
	public ShoppingListItem getItem(String itemId) throws NoSuchElementException {
		return items
			.stream()
			.filter(item -> item.getId().equals(itemId))
			.findFirst()
			.orElseGet(null);
	}

	// Get & Set
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
