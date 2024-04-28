package com.att.tdp.bisbis10.logic.dishes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.att.tdp.bisbis10.data.DishEntity;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import com.att.tdp.bisbis10.logic.DishesService;
import com.att.tdp.bisbis10.repositories.DishesRepository;
import com.att.tdp.bisbis10.repositories.RestaurantsRepository;

@Service
public class DishesServiceJPA implements DishesService {

	private @Autowired DishesRepository dishesRepository;
	private @Autowired DishConverter converter;
	private @Autowired RestaurantsRepository restaurantsRepository;

	@Override
	public void addDish(Long restaurantId, DishBoundary dish) {
		RestaurantEntity restaurantInQuestion = restaurantsRepository.findById(restaurantId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant Not Found"));

		DishEntity entity = converter.toEntity(dish).setDishId(restaurantInQuestion.getAndIncrementNextDishId())
				.setRestaurant(restaurantInQuestion);
		dishesRepository.save(entity);

		restaurantsRepository.save(restaurantInQuestion);
	}

	@Override
	public void updateDish(Long restaurantId, Long dishId, DishBoundary dish) {
		DishEntity entity = dishesRepository.findByRestaurantIdAndDishId(restaurantId, dishId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish Not Found"));

		Optional.ofNullable(dish.getPrice()).ifPresent(entity::setPrice);
		Optional.ofNullable(dish.getDescription()).ifPresent(entity::setDescription);

		dishesRepository.save(entity);
	}

	@Override
	public void deleteDish(Long restaurantId, Long dishId) {
		DishEntity entity = dishesRepository.findByRestaurantIdAndDishId(restaurantId, dishId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish Not Found"));
		dishesRepository.deleteById(entity.getId());
	}

	@Override
	public List<DishBoundary> getAllRestaurantDishes(Long restaurantId) {
		restaurantsRepository.findById(restaurantId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant Not Found"));

		return dishesRepository.findAllByRestaurantId(restaurantId).stream().map(converter::toBoundary).toList();
	}
}