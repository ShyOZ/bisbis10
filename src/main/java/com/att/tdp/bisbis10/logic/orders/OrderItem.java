package com.att.tdp.bisbis10.logic.orders;

public class OrderItem {
	private Long dishId;
	private int amount;

	public OrderItem() {
	}

	public OrderItem(Long dishId, int amount) {
		this.dishId = dishId;
		this.amount = amount;
	}

	public Long getDishId() {
		return dishId;
	}

	public void setDishId(Long dishId) {
		this.dishId = dishId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
