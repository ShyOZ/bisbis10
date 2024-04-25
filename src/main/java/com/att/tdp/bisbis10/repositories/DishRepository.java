package com.att.tdp.bisbis10.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.att.tdp.bisbis10.data.DishEntity;
import com.att.tdp.bisbis10.data.DishId;

public interface DishRepository extends ListCrudRepository<DishEntity, DishId> {
	public List<DishEntity> findAllByRestaurantId(@Param("restaurantId") Long restaurantId);
}
