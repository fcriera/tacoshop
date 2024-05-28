package com.lm2a.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Ingredient {
	@Id
	private String id;
	private String name;
	@Enumerated(EnumType.STRING)
	private Type type;
	
	public Ingredient() {
	}
	
	public Ingredient(String id, String name, Type type) {
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public static enum Type{
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}
}
