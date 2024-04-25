package com.att.tdp.bisbis10.logic.ratings;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RatingBoundary {
	private Long restaurantId;
	private BigDecimal rating;

	public RatingBoundary() {
	}

	public RatingBoundary(Long restaurantId, BigDecimal rating) {
		this.restaurantId = restaurantId;
		this.rating = rating;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public void setRating(BigDecimal rating) {
		this.rating = rating;
	}
}