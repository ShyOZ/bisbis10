package com.att.tdp.bisbis10.repositories;

import java.util.List;

import org.springframework.data.repository.ListCrudRepository;

import com.att.tdp.bisbis10.data.RestaurantEntity;

public interface RestaurantsRepository extends ListCrudRepository<RestaurantEntity, Long> {
	List<RestaurantEntity> findAllByOrderById();
}