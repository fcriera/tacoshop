package com.lm2a.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lm2a.model.Order;
import com.lm2a.model.Taco;

@Repository
public class JdbcOrderRepository implements OrderRepository {
	
	private SimpleJdbcInsert orderInserter;
	
	private SimpleJdbcInsert orderTacoInserter;
	
	private ObjectMapper objectMapper;
	
	public JdbcOrderRepository(JdbcTemplate jdbc) {
		this.orderInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order").usingGeneratedKeyColumns("id");
		this.orderTacoInserter = new SimpleJdbcInsert(jdbc).withTableName("Taco_Order_Tacos");
		this.objectMapper = new ObjectMapper();
	}


	@Override
	public Order save(Order order) {
		order.setPlacedAt(new Date());
		long orderId = SaveOrderDetails(order);
		order.setId(orderId);
		for(Taco taco: order.getTacos()) {
			saveTacoToOrder(taco, orderId);
		}
		return order;
	}

	private long SaveOrderDetails(Order order) {
		@SuppressWarnings("unchecked")
		Map<String, Object> values = objectMapper.convertValue(order, Map.class);
		values.put("placedAt", order.getPlacedAt());
		long orderId = orderInserter.executeAndReturnKey(values).longValue();
		return orderId;
	}
	
	private void saveTacoToOrder(Taco taco, long orderId) {
		Map<String, Object> values = new HashMap<>();
		values.put("tacoOrder", orderId);
		values.put("taco", taco.getId());
		orderTacoInserter.execute(values);
		
	}


	

}
