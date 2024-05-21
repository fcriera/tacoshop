package com.lm2a.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lm2a.model.Ingredient;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {
	
	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public Iterable<Ingredient> findAll() {
		return jdbc.query("SELECT id,name,type FROM Ingredient", this::mapRowToIngredient);
	}

	@Override
	public Ingredient findOne(String id) {
		return jdbc.queryForObject("SELECT id,name,type FROM Ingredient WHERE id =?", this::mapRowToIngredient, id);
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		jdbc.update("INSERT INTO Ingredient (id,name,type) VALUES (?,?,?)", ingredient.getId(), ingredient.getName(), ingredient.getType().toString());
		return null;
	}
	
	private Ingredient mapRowToIngredient(ResultSet rs, int rowNumber) throws SQLException {
		return new Ingredient(rs.getString("id"), 
				rs.getString("name"), 
				Ingredient.Type.valueOf(rs.getString("type")));
	}
}
