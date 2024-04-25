package com.att.tdp.bisbis10.logic;

import java.util.List;

import com.att.tdp.bisbis10.logic.restaurants.RestaurantBoundary;

public interface RestaurantsService {
	public List<RestaurantBoundary> getAllRestaurants();

	public List<RestaurantBoundary> getRestaurantsByCuisine(String cuisine);

	public RestaurantBoundary getRestaurant(Long id);

	public void addRestaurant(RestaurantBoundary newRestaurant);

	public void updateRestaurant(Long id, RestaurantBoundary restaurant);

	public void deleteRestaurant(Long id);
}