package com.att.tdp.bisbis10.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@IdClass(DishId.class)
@Table(name = "dishes")
public class DishEntity {
	private @Id Long internalDishId;
	private @Id Long restaurantId;
	private String name;
	private String description;
	private Integer price;

	public DishEntity() {

	}

	public DishEntity(Long internalDishId, Long restaurantId, String name, String description, Integer price) {
		this();
		this.internalDishId = internalDishId;
		this.restaurantId = restaurantId;
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public Long getInternalDishId() {
		return internalDishId;
	}

	public void setInternalDishId(Long internalDishId) {
		this.internalDishId = internalDishId;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}

	// getters and setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
}