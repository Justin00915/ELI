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
import jakarta.annotation.PostConstruct;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ShoppingListController {

	private List<ShoppingList> shoppingLists = new ArrayList<>();
	private ObjectMapper mapper = new ObjectMapper();
	private String fileName = "shoppingLists.json";

	@PostConstruct
	public void init() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			Path path = Paths.get(fileName);

			if (!Files.exists(path)) {
				Files.createFile(path);
			}

			if (Files.size(path) == 0) {
				shoppingLists = new ArrayList<>();
				return;
			}

			shoppingLists = mapper.readValue(path.toFile(), new TypeReference<List<ShoppingList>>() {
			});

		} catch (Exception e) {
			e.printStackTrace();
			shoppingLists = new ArrayList<>();
		}
	}

	void saveShoppingLists() {
		mapper.writeValue(new File("shoppingLists.json"), shoppingLists);
	}

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
			saveShoppingLists();
		}

		return "redirect:/";
	}

	@PostMapping("/deleteItem")
	public String deleteItem(@RequestParam String listId, @RequestParam String itemId) {
		var list = shoppingLists.stream().filter(shoppingList -> shoppingList.getId().equals(listId)).findFirst()
				.orElse(null);

		if (list != null) {
			list.removeItem(itemId);
			saveShoppingLists();
		}
		return "redirect:/";
	}

	@PostMapping("/editItem")
	public String editItem(@RequestParam String listId, @RequestParam String itemId, @RequestParam String newItemName,
			@RequestParam Priority newPriority, @RequestParam double newAmount, @RequestParam Unit newUnit) {
		var list = shoppingLists.stream().filter(l -> l.getId().equals(listId)).findFirst().orElse(null);

		if (list != null) {
			var item = list.getItem(itemId);

			if (item != null) {
				item.setName(newItemName);
				item.setPriority(newPriority);
				item.setAmount(newAmount);
				item.setUnit(newUnit);
			}

			saveShoppingLists();
		}
		return "redirect:/";
	}

	@PostMapping("/addList")
	public String addList(@RequestParam String listName) {
		var newList = new ShoppingList(listName);

		shoppingLists.add(newList);

		saveShoppingLists();
		return "redirect:/";
	}

	@PostMapping("/deleteList")
	public String deleteList(@RequestParam String listId) {
		shoppingLists.removeIf(list -> list.getId().equals(listId));

		saveShoppingLists();
		return "redirect:/";
	}

	@PostMapping("/changeListName")
	public String changeListName(@RequestParam String newListName, @RequestParam String listId) {
		var list = shoppingLists.stream().filter(l -> l.getId().equals(listId)).findFirst().orElse(null);

		if (list != null) {
			list.setName(newListName);
		}

		saveShoppingLists();
		return "redirect:/";
	}
}