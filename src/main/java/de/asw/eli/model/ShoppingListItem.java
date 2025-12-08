package de.asw.eli.model;

import java.util.UUID;

public class ShoppingListItem {

	public String id;
	public String name;
	public Priority priority;

	public ShoppingListItem() {
	}

	public ShoppingListItem(String name, Priority priority) {
		this.name = name;
		this.priority = priority;

		this.id = UUID.randomUUID().toString();
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

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}
}
