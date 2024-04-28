package com.att.tdp.bisbis10.logic.dishes;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class DishBoundary {
	private Long id;
	private String name;
	private String description;
	private Integer price;

	public DishBoundary() {
	}

	public Long getId() {
		return id;
	}

	public DishBoundary setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public DishBoundary setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public DishBoundary setDescription(String description) {
		this.description = description;
		return this;
	}

	public Integer getPrice() {
		return price;
	}

	public DishBoundary setPrice(Integer price) {
		this.price = price;
		return this;
	}
}