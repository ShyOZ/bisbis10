package com.att.tdp.bisbis10.data;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "dishes")
public class DishEntity {

	@GeneratedValue(strategy = GenerationType.UUID)
	private @Id UUID id;

	@ManyToOne
	@JoinColumn(name = "restaurant_id")
	private RestaurantEntity restaurant;

	private Long dishId;

	private String name;
	private String description;
	private Integer price;

	public DishEntity() {
	}

	public UUID getId() {
		return id;
	}

	public DishEntity setId(UUID id) {
		this.id = id;
		return this;
	}

	public RestaurantEntity getRestaurant() {
		return restaurant;
	}

	public DishEntity setRestaurant(RestaurantEntity restaurant) {
		this.restaurant = restaurant;
		return this;
	}

	public Long getDishId() {
		return dishId;
	}

	public DishEntity setDishId(Long dishId) {
		this.dishId = dishId;
		return this;
	}

	public String getName() {
		return name;
	}

	public DishEntity setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public DishEntity setDescription(String description) {
		this.description = description;
		return this;
	}

	public Integer getPrice() {
		return price;
	}

	public DishEntity setPrice(Integer price) {
		this.price = price;
		return this;
	}
}