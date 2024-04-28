package com.att.tdp.bisbis10.logic.orders;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class OrderBoundary {
	private UUID orderId;
	private Long restaurantId;
	private List<OrderItem> orderItems;

	public OrderBoundary() {
	}

	public UUID getOrderId() {
		return orderId;
	}

	public OrderBoundary setOrderId(UUID orderId) {
		this.orderId = orderId;
		return this;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public OrderBoundary setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
		return this;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public OrderBoundary setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
		return this;
	}
}