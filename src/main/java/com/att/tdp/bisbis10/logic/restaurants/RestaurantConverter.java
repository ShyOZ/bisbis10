package com.att.tdp.bisbis10.logic.restaurants;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.att.tdp.bisbis10.data.RestaurantEntity;

@Component
public class RestaurantConverter {

	public RestaurantEntity toEntity(RestaurantBoundary boundary) {
		RestaurantEntity entity = new RestaurantEntity();
		entity.setName(boundary.getName());
		entity.setKosher(boundary.isKosher());
		entity.setRating(boundary.getRating());
		return entity;
	}

	public RestaurantBoundary toBoundary(RestaurantEntity entity) {
		return new RestaurantBoundary(entity.getId(), entity.getName(), entity.getRating(), entity.isKosher(),
				entity.getCuisines().stream().sorted((c1, c2) -> Long.compare(c1.getId(), c2.getId()))
						.map(cuisineEntity -> cuisineEntity.getName()).collect(Collectors.toList()));
	}
}