package de.asw.eli;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import de.asw.eli.model.Priority;
import de.asw.eli.model.ShoppingList;
import de.asw.eli.model.ShoppingListItem;
import de.asw.eli.model.Unit;

@Controller
public class ShoppingListController {

	private final List<ShoppingList> shoppingLists = new ArrayList<>();

	@GetMapping("/")
	public String showShoppingList(@RequestParam(required = false) String editId, Model model) {
		model.addAttribute("shoppingLists", shoppingLists);
		model.addAttribute("priorities", Priority.values());
		model.addAttribute("units", Unit.values());
		model.addAttribute("editId", editId);
		return "shopping-list";
	}

	@PostMapping("/addItem")
	public String addItem(@RequestParam String listId, @RequestParam String itemName, @RequestParam Priority priority,
			@RequestParam double amount, @RequestParam Unit unit) {
		var newItem = new ShoppingListItem(itemName, priority, amount, unit);

		var currentShoppingList = shoppingLists.stream().filter(shoppingList -> shoppingList.getId().equals(listId))
				.findFirst().orElse(null);

		if (currentShoppingList != null) {
			currentShoppingList.addItem(newItem);
		}

		return "redirect:/";
	}

	@PostMapping("/deleteItem")
	public String deleteItem(@RequestParam String listId, @RequestParam String itemId) {
		var list = shoppingLists.stream().filter(shoppingList -> shoppingList.getId().equals(listId)).findFirst()
				.orElse(null);

		list.removeItem(itemId);

		return "redirect:/";
	}
	
	@PostMapping("/editItem")
	public String editItem(@RequestParam String listId, @RequestParam String itemId, @RequestParam String newItemName,
			@RequestParam Priority newPriority, @RequestParam double newAmount, @RequestParam Unit newUnit) {
		var list = shoppingLists.stream().filter(l -> l.getId().equals(listId)).findFirst().orElse(null);

		var item = list.getItem(itemId);

		item.setName(newItemName);
		item.setPriority(newPriority);
		item.setAmount(newAmount);
		item.setUnit(newUnit);

		return "redirect:/";
	}

	@PostMapping("/addList")
	public String addList(@RequestParam String listName) {
		var newList = new ShoppingList(listName);

		shoppingLists.add(newList);

		return "redirect:/";
	}

	@PostMapping("/deleteList")
	public String deleteList(@RequestParam String listId) {
		shoppingLists.removeIf(list -> list.getId().equals(listId));

		return "redirect:/";
	}

	@PostMapping("/changeListName")
	public String changeListName(@RequestParam String newListName, @RequestParam String listId) {
		var list = shoppingLists.stream().filter(l -> l.getId().equals(listId)).findFirst().orElse(null);

		list.setName(newListName);

		return "redirect:/";
	}
}