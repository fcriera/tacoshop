package com.lm2a.data;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.lm2a.model.Ingredient;
import com.lm2a.model.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {

	@Autowired
	private JdbcTemplate jdbc;
	
	@Override
	public Taco save(Taco taco) {
		
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		
		for (Ingredient ingredient: taco.getIngredients()) {
			saveIngredientToTaco(ingredient, tacoId);
		}
		return taco;
	}

	private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
		jdbc.update("INSERT INTO Taco_Ingredients (taco_id, ingredient_id) VALUES (?,?)", tacoId, ingredient.getId());
	}

	private long saveTacoInfo(Taco taco) {
		taco.setCreatedAt(new Date());
		
			PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory("INSERT INTO Taco (name,createdAt) VALUES (?,?)", Types.VARCHAR, Types.TIMESTAMP);
			pscf.setReturnGeneratedKeys(true);
			
			PreparedStatementCreator psc = pscf.newPreparedStatementCreator(Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));
			
			KeyHolder keyholder = new GeneratedKeyHolder();
			jdbc.update(psc,keyholder);
			
			Number n = keyholder.getKey();
		return n.longValue();
	}

}
