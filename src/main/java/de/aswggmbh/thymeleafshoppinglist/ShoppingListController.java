package de.aswggmbh.thymeleafshoppinglist;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/thymeleaf")
public class ShoppingListController {

	private ShoppingListItemRepository repository;
	
	public ShoppingListController(ShoppingListItemRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public String shoppingList(Model model) {
		try {
			model.addAttribute("shoppingList", repository.findAll());
		} catch(Exception e) {
			model.addAttribute("shoppingList", null);
		}
		model.addAttribute("shoppingListItem", new ShoppingListItem());
		return "shoppinglist";
	}
	
	@PostMapping
	public String append(Model model, @ModelAttribute ShoppingListItem item) {
		repository.save(item);
		return "redirect:/thymeleaf";
	}
}
