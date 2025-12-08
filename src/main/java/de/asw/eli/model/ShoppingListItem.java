package de.asw.eli.model;

import java.util.UUID;

public class ShoppingListItem {

	public String id;
	public String name;
	public Priority priority;
	public double amount;
	public Unit unit;

	public ShoppingListItem() {
	}

	public ShoppingListItem(String name, Priority priority, double amount, Unit unit) {
		this.id = UUID.randomUUID().toString();

		this.name = name;
		this.priority = priority;
		this.amount = amount;
		this.unit = unit;
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
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}
}
