package com.att.tdp.bisbis10.logic.ratings;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class RatingBoundary {
	private Long restaurantId;
	private BigDecimal rating;

	public RatingBoundary() {
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public RatingBoundary setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
		return this;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public RatingBoundary setRating(BigDecimal rating) {
		this.rating = rating;
		return this;
	}
}