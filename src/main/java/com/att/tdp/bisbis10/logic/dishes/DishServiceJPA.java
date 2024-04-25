package com.att.tdp.bisbis10.logic.dishes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.tdp.bisbis10.logic.DishesService;
import com.att.tdp.bisbis10.repositories.DishRepository;

@Service
public class DishServiceJPA implements DishesService {

	private @Autowired DishRepository repository;
	private @Autowired DishConverter converter;

	@Override
	public void addDish(Long restaurantId, DishBoundary dish) {
		// TODO implement addDish
	}

	@Override
	public void updateDish(Long restaurantId, Long dishId, DishBoundary dish) {
		// TODO implement updateDish
	}

	@Override
	public void deleteDish(Long restaurantId, Long dishid) {
		// TODO implement deleteDish
	}

	@Override
	public List<DishBoundary> getAllRestaurantDishes(Long restaurantId) {
		return repository.findAllByRestaurantId(restaurantId).stream().map(converter::toBoundary)
				.collect(Collectors.toList());
	}
}