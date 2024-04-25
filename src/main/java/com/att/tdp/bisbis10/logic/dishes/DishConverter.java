package com.att.tdp.bisbis10.logic.dishes;

import org.springframework.stereotype.Component;

import com.att.tdp.bisbis10.data.DishEntity;

@Component
public class DishConverter {
	public DishEntity toEntity(DishBoundary boundary) {
		DishEntity entity = new DishEntity();
		entity.setName(boundary.getName());
		entity.setDescription(boundary.getDescription());
		entity.setPrice(boundary.getPrice());

		return entity;
	}

	public DishBoundary toBoundary(DishEntity entity) {
		DishBoundary boundary = new DishBoundary();
		boundary.setId(entity.getInternalDishId());
		boundary.setName(entity.getName());
		boundary.setDescription(entity.getDescription());
		boundary.setPrice(entity.getPrice());

		return boundary;
	}
}