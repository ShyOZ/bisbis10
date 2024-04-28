package com.att.tdp.bisbis10.logic.dishes;

import org.springframework.stereotype.Component;

import com.att.tdp.bisbis10.data.DishEntity;

@Component
public class DishConverter {
	public DishEntity toEntity(DishBoundary boundary) {
		return new DishEntity().setName(boundary.getName()).setDescription(boundary.getDescription())
				.setPrice(boundary.getPrice());
	}

	public DishBoundary toBoundary(DishEntity entity) {
		return new DishBoundary().setId(entity.getDishId()).setName(entity.getName())
				.setDescription(entity.getDescription()).setPrice(entity.getPrice());
	}
}