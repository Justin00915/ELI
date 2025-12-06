package de.asw.eli;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ShoppingListController {
	
	private final List<String> items = new ArrayList<>();

	@GetMapping("/")
	public String showShoppingList(Model model) {
		model.addAttribute("items", items);
		return "shopping-list";
	}

	@PostMapping("/add")
	public String addItem(@RequestParam("item") String item) {
		if (item != null && !item.trim().isEmpty()) {
			items.add(item.trim());
		}
		return "redirect:/";
	}
}