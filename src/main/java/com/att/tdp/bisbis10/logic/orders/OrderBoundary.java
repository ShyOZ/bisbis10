package com.att.tdp.bisbis10.logic.orders;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderBoundary {
	@JsonProperty(access = Access.READ_ONLY)
	private String id;

	@JsonProperty(access = Access.WRITE_ONLY)
	private Long restaurantId;

	private List<OrderItem> orderItems;

	public OrderBoundary() {
	}

	public OrderBoundary(String id, Long restaurantId, List<OrderItem> orderItems) {
		this.id = id;
		this.restaurantId = restaurantId;
		this.orderItems = orderItems;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}