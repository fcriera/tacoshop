package com.lm2a.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lm2a.model.Ingredient;
import com.lm2a.model.Ingredient.Type;
import com.lm2a.model.Taco;

import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
public class DesignTacoController {
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
		log.info("Designed Taco: "+design);
		return "redirect:/orders/current";
		
	}
	public void populateIngredients(Model model) {
		List<Ingredient> ingredients =  Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
                new Ingredient("CHED", "Cheddar", Type.CHEESE),
                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
                new Ingredient("SLSA", "Salsa", Type.SAUCE),
                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
              );
      Type[] types = Ingredient.Type.values();
      for (Type type:types) {
      	model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
      }
	}
	
}
