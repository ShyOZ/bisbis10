package com.att.tdp.bisbis10.logic.restaurants;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.tdp.bisbis10.customExceptions.EntityNotFoundException;
import com.att.tdp.bisbis10.data.CuisineEntity;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import com.att.tdp.bisbis10.logic.DishesService;
import com.att.tdp.bisbis10.logic.RestaurantsService;
import com.att.tdp.bisbis10.repositories.CuisineRepository;
import com.att.tdp.bisbis10.repositories.RestaurantRepository;

@Service
public class RestaurantServiceJPA implements RestaurantsService {
	private @Autowired RestaurantConverter converter;
	private @Autowired RestaurantRepository restaurantRepository;
	private @Autowired CuisineRepository cuisineRepository;
	private @Autowired DishesService dishService;

	@Override
	public List<RestaurantBoundary> getAllRestaurants() {
		return restaurantRepository.findAll().stream().map(converter::toBoundary).toList();
	}

	@Override
	public List<RestaurantBoundary> getRestaurantsByCuisine(String cuisine) {
		List<RestaurantBoundary> restaurants = cuisineRepository.findByNameIgnoreCase(cuisine).orElseGet(() -> {
			CuisineEntity emptyEntity = new CuisineEntity();
			emptyEntity.setRestaurants(Set.of());
			return emptyEntity;
		}).getRestaurants().stream().map(converter::toBoundary).toList();

		restaurants.sort((restaurant1, restaurant2) -> Long.compare(restaurant1.getId(), restaurant2.getId()));

		return restaurants;
	}

	@Override
	public RestaurantBoundary getRestaurant(Long id) {
		RestaurantBoundary boundary = converter.toBoundary(getRestaurantEntity(id));
		boundary.setDishes(dishService.getAllRestaurantDishes(id));

		boundary.getDishes().sort((dish1, dish2) -> Long.compare(dish1.getId(), dish2.getId()));
		return boundary;
	}

	private RestaurantEntity getRestaurantEntity(Long id) {
		return restaurantRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("could not find Restaurant by id: " + id));
	}

	private Set<CuisineEntity> getCuisineSetFromNameList(List<String> cuisines) {
		return cuisines.stream().map(cuisine -> {
			return cuisineRepository.findByNameIgnoreCase(cuisine).orElseGet(() -> {
				CuisineEntity newCuisine = new CuisineEntity();
				newCuisine.setName(cuisine);
				return newCuisine;
			});
		}).collect(Collectors.toSet());
	}

	@Override
	public void addRestaurant(RestaurantBoundary newRestaurant) {
		RestaurantEntity entity = converter.toEntity(newRestaurant);
		entity.setCuisines(getCuisineSetFromNameList(newRestaurant.getCuisines()));
		restaurantRepository.save(entity);
	}

	@Override
	public void updateRestaurant(Long id, RestaurantBoundary restaurant) {
		RestaurantEntity entity = getRestaurantEntity(id);

		// TODO: ask if there's a need to add more checks
		if (restaurant.getCuisines() != null && restaurant.getCuisines().isEmpty() == false)
			entity.setCuisines(getCuisineSetFromNameList(restaurant.getCuisines()));

		restaurantRepository.save(entity);
	}

	@Override
	public void deleteRestaurant(Long id) {
		restaurantRepository.deleteById(id);
	}
}