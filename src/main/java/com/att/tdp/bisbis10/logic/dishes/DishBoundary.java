package com.att.tdp.bisbis10.logic.dishes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class DishBoundary {
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	private String name;
	private String description;
	private Integer price;

	public DishBoundary() {
	}

	public DishBoundary(Long id, String name, String description, Integer price) {
		this();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public long getId() {
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

	@Override
	public String toString() {
		return "DishBoundary [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + "]";
	}
}