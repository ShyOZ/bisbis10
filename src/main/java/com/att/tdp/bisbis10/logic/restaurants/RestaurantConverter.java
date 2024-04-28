package com.att.tdp.bisbis10.logic.restaurants;

import org.springframework.stereotype.Component;

import com.att.tdp.bisbis10.data.RestaurantEntity;

@Component
public class RestaurantConverter {

	public RestaurantEntity toEntity(RestaurantBoundary boundary) {
		return new RestaurantEntity().setName(boundary.getName()).setKosher(boundary.isKosher());
	}

	public RestaurantBoundary toBoundary(RestaurantEntity entity) {
		return new RestaurantBoundary().setId(entity.getId()).setName(entity.getName()).setKosher(entity.isKosher())
				.setAverageRating(entity.getAverageRating())
				.setCuisines(entity.getCuisines().stream().sorted((c1, c2) -> Long.compare(c1.getId(), c2.getId()))
						.map(cuisineEntity -> cuisineEntity.getName()).toList());
	}
}