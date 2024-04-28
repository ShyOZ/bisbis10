package com.att.tdp.bisbis10.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.bisbis10.logic.DishesService;
import com.att.tdp.bisbis10.logic.dishes.DishBoundary;

@RestController
@RequestMapping(path = "/restaurants")
public class DishesController {
	private @Autowired DishesService dishesService;

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping(path = "/{id}/dishes")
	public void addDishToRestaurant(@PathVariable("id") Long restaurantId, @RequestBody DishBoundary dish) {
		dishesService.addDish(restaurantId, dish);
	}

	@PutMapping(path = "/{id}/dishes/{dishId}")
	public void updateDish(@PathVariable("id") Long restaurantId, @PathVariable Long dishId,
			@RequestBody DishBoundary dish) {
		dishesService.updateDish(restaurantId, dishId, dish);
	}

	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping(path = "/{id}/dishes/{dishId}")
	public void deleteDish(@PathVariable("id") Long restaurantId, @PathVariable Long dishId) {
		dishesService.deleteDish(restaurantId, dishId);
	}

	@GetMapping(path = "/{id}/dishes")
	public List<DishBoundary> getAllRestaurantDishes(@PathVariable("id") Long restaurantId) {
		return dishesService.getAllRestaurantDishes(restaurantId);
	}
}