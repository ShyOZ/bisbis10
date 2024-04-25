package com.att.tdp.bisbis10.logic;

import java.util.List;

import com.att.tdp.bisbis10.logic.dishes.DishBoundary;

public interface DishesService {
	List<DishBoundary> getAllRestaurantDishes(Long restaurantId);

	void addDish(Long restaurantId, DishBoundary dish);

	void updateDish(Long restaurantId, Long dishId, DishBoundary dish);

	void deleteDish(Long restaurantId, Long dishid);
}