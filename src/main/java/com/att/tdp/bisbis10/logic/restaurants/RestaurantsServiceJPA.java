package com.att.tdp.bisbis10.logic.restaurants;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.att.tdp.bisbis10.data.CuisineEntity;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import com.att.tdp.bisbis10.logic.DishesService;
import com.att.tdp.bisbis10.logic.RestaurantsService;
import com.att.tdp.bisbis10.repositories.CuisinesRepository;
import com.att.tdp.bisbis10.repositories.RestaurantsRepository;

@Service
public class RestaurantsServiceJPA implements RestaurantsService {
	private @Autowired RestaurantConverter converter;
	private @Autowired RestaurantsRepository restaurantsRepository;
	private @Autowired CuisinesRepository cuisinesRepository;
	private @Autowired DishesService dishService;

	@Override
	public List<RestaurantBoundary> getAllRestaurants() {
		return restaurantsRepository.findAll().stream().map(converter::toBoundary).toList();
	}

	@Override
	public List<RestaurantBoundary> getRestaurantsByCuisine(String cuisine) {
		return cuisinesRepository.findByNameIgnoreCase(cuisine).orElse(new CuisineEntity().setRestaurants(Set.of()))
				.getRestaurants().stream().map(converter::toBoundary).toList();
	}

	private RestaurantEntity getRestaurantEntity(Long id) {
		return restaurantsRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant Not Found"));
	}

	private Set<CuisineEntity> getCuisineSetFromNameList(List<String> cuisines) {
		return cuisines.stream().map(cuisineName -> {
			return cuisinesRepository.findByNameIgnoreCase(cuisineName)
					.orElse(new CuisineEntity().setName(cuisineName));
		}).collect(Collectors.toSet());
	}

	@Override
	public RestaurantBoundary getRestaurant(Long id) {
		return converter.toBoundary(getRestaurantEntity(id)).setDishes(dishService.getAllRestaurantDishes(id));
	}

	@Override
	public void addRestaurant(RestaurantBoundary newRestaurant) {
		restaurantsRepository.save(converter.toEntity(newRestaurant).setCuisines(
				getCuisineSetFromNameList(Optional.ofNullable(newRestaurant.getCuisines()).orElse(List.of()))));
	}

	@Override
	public void updateRestaurant(Long id, RestaurantBoundary restaurant) {
		RestaurantEntity entity = getRestaurantEntity(id);

		Optional.ofNullable(restaurant.getCuisines()).ifPresent(c -> entity.setCuisines(getCuisineSetFromNameList(c)));

		restaurantsRepository.save(entity);
	}

	@Override
	public void deleteRestaurant(Long id) {
		restaurantsRepository.deleteById(id);
	}
}