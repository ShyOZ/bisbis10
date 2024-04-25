package com.att.tdp.bisbis10.logic.orders;

import org.springframework.stereotype.Service;

import com.att.tdp.bisbis10.logic.OrdersService;

@Service
public class OrderServiceJPA implements OrdersService {

	private OrderConverter converter;
	
	
	@Override
	public OrderBoundary makeOrder(OrderBoundary orderDetails) {
		// TODO implement 
		return new OrderBoundary();
	}

}
