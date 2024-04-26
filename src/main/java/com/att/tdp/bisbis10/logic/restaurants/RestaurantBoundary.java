package com.att.tdp.bisbis10.logic.restaurants;

import java.math.BigDecimal;
import java.util.List;

import com.att.tdp.bisbis10.logic.dishes.DishBoundary;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestaurantBoundary {

	private Long id;

	private String name;

	private BigDecimal averageRating;

	@JsonProperty("isKosher")
	private Boolean isKosher;

	private List<String> cuisines;

	private List<DishBoundary> dishes;

	public RestaurantBoundary() {
	}

	public RestaurantBoundary(Long id, String name, BigDecimal rating, Boolean isKosher, List<String> cuisines) {
		this.id = id;
		this.name = name;
		this.averageRating = rating;
		this.isKosher = isKosher;
		this.cuisines = cuisines;
		this.dishes = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(BigDecimal averageRating) {
		this.averageRating = averageRating;
	}

	@JsonProperty("isKosher")
	public Boolean isKosher() {
		return isKosher;
	}

	public void setKosher(Boolean isKosher) {
		this.isKosher = isKosher;
	}

	public List<String> getCuisines() {
		return cuisines;
	}

	public void setCuisines(List<String> cuisines) {
		this.cuisines = cuisines;
	}

	public List<DishBoundary> getDishes() {
		return dishes;
	}

	public void setDishes(List<DishBoundary> dishes) {
		this.dishes = dishes;
	}

	@Override
	public String toString() {
		return "RestaurantBoundary [id=" + id + ", name=" + name + ", averageRating=" + averageRating + ", isKosher="
				+ isKosher + ", cuisines=" + cuisines + ", dishes=" + dishes + "]";
	}
}