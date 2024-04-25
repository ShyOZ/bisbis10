package com.att.tdp.bisbis10.data;

import java.util.Objects;

public class DishId {
	private Long internalDishId;
	private Long restaurantId;

	public DishId(Long internalDishId, Long restaurantId) {
		this.setInternalDishId(internalDishId);
		this.setRestaurantId(restaurantId);
	}

	public DishId() {
	}

	public Long getInternalDishId() {
		return internalDishId;
	}

	public void setInternalDishId(Long internalDishId) {
		this.internalDishId = internalDishId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || this.getClass() != obj.getClass())
			return false;

		if (this == obj)
			return true;

		DishId other = (DishId) obj;

		return internalDishId.equals(other.internalDishId) && restaurantId.equals(other.restaurantId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(internalDishId, restaurantId);
	}
}