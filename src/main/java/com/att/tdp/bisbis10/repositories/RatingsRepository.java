package com.att.tdp.bisbis10.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.att.tdp.bisbis10.data.RatingEntity;

public interface RatingsRepository extends ListCrudRepository<RatingEntity, Long> {
	List<RatingEntity> findAllByRestaurantId(Long restaurantId);
}