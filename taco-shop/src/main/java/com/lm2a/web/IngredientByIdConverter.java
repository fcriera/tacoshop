package com.lm2a.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.lm2a.data.IngredientRepository;
import com.lm2a.model.Ingredient;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {
	
	@Autowired
	IngredientRepository ingredientRepo;
	
	@Override
	public Ingredient convert(String source) {
		return ingredientRepo.findOne(source);
	}

}
