package com.att.tdp.bisbis10.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.bisbis10.logic.RestaurantsService;
import com.att.tdp.bisbis10.logic.restaurants.RestaurantBoundary;

@RestController
@RequestMapping(path = "/restaurants")
public class RestaurantsController {
	private @Autowired RestaurantsService restaurantsService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RestaurantBoundary> getAllRestaurants() {
		return restaurantsService.getAllRestaurants();
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params="cuisine")
	public List<RestaurantBoundary> getRestaurantsByCuisine(@RequestParam String cuisine) {
		return restaurantsService.getRestaurantsByCuisine(cuisine);
	}

	@GetMapping(path = "/{id}")
	public RestaurantBoundary getRestaurant(@PathVariable Long id) {
		return restaurantsService.getRestaurant(id);
	}

	@ResponseStatus(code = HttpStatus.CREATED)
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addRestaurant(@RequestBody RestaurantBoundary newRestaurant) {
		restaurantsService.addRestaurant(newRestaurant);
	}

	@PutMapping(path = "/{id}")
	public void updateRestaurant(@PathVariable Long id, @RequestBody RestaurantBoundary restaurant) {
		restaurantsService.updateRestaurant(id, restaurant);
	}
	
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@DeleteMapping(path = "/{id}")
	public void deleteRestaurant(@PathVariable Long id) {
		restaurantsService.deleteRestaurant(id);
	}
}