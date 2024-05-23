package com.lm2a.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lm2a.data.IngredientRepository;
import com.lm2a.data.TacoRepository;
import com.lm2a.model.Ingredient;
import com.lm2a.model.Ingredient.Type;
import com.lm2a.model.Taco;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
	
	@Autowired
	private IngredientRepository ingredientRepo;
	
	@Autowired
	private TacoRepository tacoRepo;
	
	@GetMapping
	 public String showDesignForm(Model model) {
	        populateIngredients(model);
	        model.addAttribute("tktn", new Taco());
	        return "design";
	 }

	private List <Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream()
				.filter(i -> i.getType().equals(type))
				.collect(Collectors.toList());
	}
	@PostMapping
	public String processDesign(@Valid @ModelAttribute (name="tktn") Taco design, Errors errors, Model model ) {
		if(errors.hasErrors()) {
			populateIngredients(model);
			return "design";
		}
		Taco saved= tacoRepo.save(design);
		log.info("Designed Taco: "+saved);
		return "redirect:/orders/current";
		
	}
	public void populateIngredients(Model model) {
		
		List<Ingredient> ingredients = new ArrayList<>();
		
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));
		
		
      Type[] types = Ingredient.Type.values();
      for (Type type:types) {
      	model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
      }
	}
	
}
