package com.att.tdp.bisbis10.logic.restaurants;

import java.math.BigDecimal;
import java.util.List;

import com.att.tdp.bisbis10.logic.dishes.DishBoundary;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class RestaurantBoundary {
	private Long id;
	private String name;
	private BigDecimal averageRating;
	private Boolean isKosher;
	private List<String> cuisines;
	private List<DishBoundary> dishes;

	public RestaurantBoundary() {
	}

	public Long getId() {
		return id;
	}

	public RestaurantBoundary setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public RestaurantBoundary setName(String name) {
		this.name = name;
		return this;
	}

	public BigDecimal getAverageRating() {
		return averageRating;
	}

	public RestaurantBoundary setAverageRating(BigDecimal averageRating) {
		this.averageRating = averageRating;
		return this;
	}

	@JsonProperty("isKosher")
	public Boolean isKosher() {
		return isKosher;
	}

	@JsonProperty("isKosher")
	public RestaurantBoundary setKosher(Boolean isKosher) {
		this.isKosher = isKosher;
		return this;
	}

	public List<String> getCuisines() {
		return cuisines;
	}

	public RestaurantBoundary setCuisines(List<String> cuisines) {
		this.cuisines = cuisines;
		return this;
	}

	public List<DishBoundary> getDishes() {
		return dishes;
	}

	public RestaurantBoundary setDishes(List<DishBoundary> dishes) {
		this.dishes = dishes;
		return this;
	}
}