package com.att.tdp.bisbis10.data;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "ratings")
public class RatingEntity {
	@SequenceGenerator(initialValue = 1, name = "ratings_seq", allocationSize = 1)
	@GeneratedValue(generator = "ratings_seq")
	private @Id Long id;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private RestaurantEntity restaurant;
	private BigDecimal rating;

	public RatingEntity() {
	}

	public Long getId() {
		return id;
	}

	public RatingEntity setId(Long id) {
		this.id = id;
		return this;
	}

	public RestaurantEntity getRestaurant() {
		return restaurant;
	}

	public RatingEntity setRestaurant(RestaurantEntity restaurant) {
		this.restaurant = restaurant;
		return this;
	}

	public BigDecimal getRating() {
		return rating;
	}

	public RatingEntity setRating(BigDecimal rating) {
		this.rating = rating;
		return this;
	}
}
