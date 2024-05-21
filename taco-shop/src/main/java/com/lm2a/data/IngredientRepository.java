package com.lm2a.data;

import com.lm2a.model.Ingredient;

public interface IngredientRepository {
	
	Iterable<Ingredient> findAll();
	
	Ingredient findOne(String id);
	
	Ingredient save(Ingredient ingredient);
}
