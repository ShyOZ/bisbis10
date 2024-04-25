package com.att.tdp.bisbis10.logic;

import com.att.tdp.bisbis10.logic.orders.OrderBoundary;

public interface OrdersService {
	public OrderBoundary makeOrder(OrderBoundary orderDetails);
}