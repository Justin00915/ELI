package de.aswggmbh.thymeleafshoppinglist;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ShoppingListItemRepository extends CrudRepository<ShoppingListItem, Long>{

	List<ShoppingListItem> findByName(@Param("name") String name);

}
