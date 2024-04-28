package com.att.tdp.bisbis10.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.att.tdp.bisbis10.data.DishEntity;

public interface DishesRepository extends ListCrudRepository<DishEntity, UUID> {
	public List<DishEntity> findAllByRestaurantId(Long restaurantId);

	public Optional<DishEntity> findByRestaurantIdAndDishId(Long restaurantId, Long dishId);
}
