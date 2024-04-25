package com.att.tdp.bisbis10.logic.ratings;

import org.springframework.stereotype.Component;

import com.att.tdp.bisbis10.data.RatingEntity;

@Component
public class RatingConverter {
	public RatingEntity toEntity(RatingBoundary boundary) {
		RatingEntity entity = new RatingEntity();
		entity.setRating(boundary.getRating());
		return entity;
	}
}