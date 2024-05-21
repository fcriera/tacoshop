package com.lm2a.model;

import java.util.List;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Taco {
	@Size(min = 5, message = "El nombre debe tener al menos 5 caracteres")
	private String name;
	private List<String> ingredients;
}