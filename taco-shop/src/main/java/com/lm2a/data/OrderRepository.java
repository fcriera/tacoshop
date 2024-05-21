package com.lm2a.data;

import com.lm2a.model.Order;

public interface OrderRepository {
	Order save(Order order);
}
