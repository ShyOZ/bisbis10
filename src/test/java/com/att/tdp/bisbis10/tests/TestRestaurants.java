package com.att.tdp.bisbis10.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.LongStream;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.att.tdp.bisbis10.data.RestaurantEntity;
import com.att.tdp.bisbis10.logic.dishes.DishBoundary;
import com.att.tdp.bisbis10.logic.restaurants.RestaurantBoundary;
import com.att.tdp.bisbis10.utility.ConfigureComplexTest;
import com.att.tdp.bisbis10.utility.TestHelper;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.annotation.PostConstruct;

@ConfigureComplexTest
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestRestaurants {
	private @Autowired MockMvc mockMvc;
	private RecursiveComparisonConfiguration restaurantBoundaryComparisonConfig;
	private RecursiveComparisonConfiguration restaurantEntityComparisonConfig;

	@PostConstruct
	void init() {
		restaurantBoundaryComparisonConfig = RecursiveComparisonConfiguration.builder().withIgnoreCollectionOrder(true)
				.withIgnoreAllExpectedNullFields(true).build();
		restaurantEntityComparisonConfig = RecursiveComparisonConfiguration.builder().withIgnoreCollectionOrder(true)
				.withIgnoreAllExpectedNullFields(true).withIgnoredFields("cuisines.restaurants").build();
	}

	@Test
	void testAddRestaurant(TestHelper helper) throws JsonProcessingException, Exception {
		mockMvc.perform(helper.requester.postRestaurant(new RestaurantBoundary().setName("restaurant").setKosher(true)))
				.andExpectAll(status().isCreated(), content().string(emptyOrNullString()));

		long createdRestaurantId = 1l;

		RestaurantEntity actualRestaurant = helper.restaurantsRepository.findById(createdRestaurantId).get();

		RestaurantEntity expectedRestaurant = new RestaurantEntity().setId(createdRestaurantId).setName("restaurant")
				.setKosher(true);

		assertThat(actualRestaurant).usingRecursiveComparison(restaurantBoundaryComparisonConfig)
				.isEqualTo(expectedRestaurant);
	}

	@Test
	void testGetRestaurant(TestHelper helper) throws JsonProcessingException, Exception {
		long createdRestaurantId = helper.restaurantsRepository
				.save(new RestaurantEntity().setName("restaurant").setKosher(true)).getId();

		List<DishBoundary> dishes = LongStream.of(1, 2, 3).mapToObj(
				i -> new DishBoundary().setName("dish" + i).setDescription("dish number " + i).setPrice(10 * (int) i))
				.toList();

		for (DishBoundary d : dishes) {
			mockMvc.perform(helper.requester.postDish(1l, d));
		}

		RestaurantBoundary expectedRestaurant = new RestaurantBoundary().setId(createdRestaurantId)
				.setName("restaurant").setKosher(true).setDishes(dishes);

		MvcResult result = mockMvc.perform(helper.requester.getRestaurant(createdRestaurantId))
				.andExpect(status().isOk()).andReturn();

		RestaurantBoundary actualRestaurant = helper.mapper.readValue(result.getResponse().getContentAsString(),
				RestaurantBoundary.class);

		assertThat(actualRestaurant).usingRecursiveComparison(restaurantBoundaryComparisonConfig)
				.isEqualTo(expectedRestaurant);

		mockMvc.perform(helper.requester.getRestaurant(createdRestaurantId + 1)).andExpect(status().isNotFound());
	}

	@Test
	void testUpdateRestaurant(TestHelper helper) throws JsonProcessingException, Exception {
		List<String> cuisines = List.of("a", "b", "c");

		mockMvc.perform(helper.requester
				.postRestaurant(new RestaurantBoundary().setName("restaurant").setKosher(true).setCuisines(cuisines)));

		RestaurantBoundary update = new RestaurantBoundary().setCuisines(List.of(cuisines.get(0)));

		long createdRestaurantId = 1l;

		RestaurantEntity expectedUpdatedRestaurant = new RestaurantEntity().setId(createdRestaurantId)
				.setName("restaurant").setKosher(true)
				.setCuisines(helper.getCuisineSetFromNameList(List.of(cuisines.get(0))));

		mockMvc.perform(helper.requester.putRestaurantUpdate(update, createdRestaurantId)).andExpectAll(status().isOk(),
				content().string(emptyOrNullString()));

		RestaurantEntity actualUpdatedRestaurant = helper.restaurantsRepository.findById(createdRestaurantId).get();

		assertThat(actualUpdatedRestaurant).usingRecursiveComparison(restaurantEntityComparisonConfig)
				.isEqualTo(expectedUpdatedRestaurant);
	}

	@Test
	void testDeleteRestaurant(TestHelper helper) throws JsonProcessingException, Exception {

		for (int i = 1; i <= 5; i++)
			mockMvc.perform(helper.requester
					.postRestaurant(new RestaurantBoundary().setName("restaurant" + i).setKosher(true)));

		long toDelete = 4l;

		mockMvc.perform(helper.requester.deleteRestaurant(toDelete)).andExpectAll(status().isNoContent(),
				content().string(emptyOrNullString()));

		List<RestaurantEntity> expectedRestaurantList = LongStream.of(1, 2, 3, 5)
				.mapToObj(i -> new RestaurantEntity().setId(i).setName("restaurant" + i).setKosher(true)).toList();

		List<RestaurantEntity> actualRestaurantList = helper.restaurantsRepository.findAll();

		assertThat(actualRestaurantList).usingRecursiveFieldByFieldElementComparator(restaurantEntityComparisonConfig)
				.containsExactlyInAnyOrderElementsOf(expectedRestaurantList);
	}

	@Test
	void testGetAllRestaurantsAndGetAllRestaurantsByCuisine(TestHelper helper)
			throws JsonProcessingException, Exception {
		RestaurantBoundary restABC = new RestaurantBoundary().setName("restaurantABC").setKosher(true)
				.setCuisines(List.of("a", "b", "c"));
		RestaurantBoundary restAC = new RestaurantBoundary().setName("restaurantAC").setKosher(true)
				.setCuisines(List.of("a", "c"));
		RestaurantBoundary restA = new RestaurantBoundary().setName("restaurantA").setKosher(true)
				.setCuisines(List.of("a"));

		mockMvc.perform(helper.requester.postRestaurant(restABC));
		restABC.setId(1l);

		mockMvc.perform(helper.requester.postRestaurant(restAC));
		restAC.setId(2l);

		mockMvc.perform(helper.requester.postRestaurant(restA));
		restA.setId(3l);

		Map<MockHttpServletRequestBuilder, List<RestaurantBoundary>> requests = Map.ofEntries(
				entry(helper.requester.getAllRestaurants(), List.of(restABC, restAC, restA)),
				entry(helper.requester.getAllRestaurantsByCuisine("a"), List.of(restABC, restAC, restA)),
				entry(helper.requester.getAllRestaurantsByCuisine("b"), List.of(restABC)),
				entry(helper.requester.getAllRestaurantsByCuisine("c"), List.of(restABC, restAC)),
				entry(helper.requester.getAllRestaurantsByCuisine("d"), List.of()));

		for (Entry<MockHttpServletRequestBuilder, List<RestaurantBoundary>> entry : requests.entrySet()) {
			MvcResult result = mockMvc.perform(entry.getKey()).andExpect(status().isOk()).andReturn();
			RestaurantBoundary[] actualList = helper.mapper.readValue(result.getResponse().getContentAsString(),
					RestaurantBoundary[].class);

			assertThat(actualList).usingRecursiveFieldByFieldElementComparator(restaurantBoundaryComparisonConfig)
					.containsExactlyInAnyOrderElementsOf(entry.getValue());
		}
	}

	@Test
	void testDeleteRestaurantDoesNotDeleteCuisines(TestHelper helper) throws JsonProcessingException, Exception {
		List<String> cuisines = List.of("a", "b", "c");

		RestaurantBoundary restaurant = new RestaurantBoundary().setName("restaurant").setKosher(true)
				.setCuisines(cuisines);

		mockMvc.perform(helper.requester.postRestaurant(restaurant));

		helper.restaurantsRepository.deleteAll();

		List<String> actualCuisines = helper.cuisinesRepository.findAll().stream()
				.map(cuisineEntity -> cuisineEntity.getName()).toList();

		assertThat(actualCuisines).containsExactlyInAnyOrderElementsOf(cuisines);
	}

	@Test
	void testDeleteNonexistentRestaurantFailsSilently(TestHelper helper) throws JsonProcessingException, Exception {
		mockMvc.perform(helper.requester.deleteRestaurant(1l)).andExpectAll(status().isNoContent(),
				content().string(emptyOrNullString()));
	}

	@Test
	void testAddRestaurantAlsoAddsCuisines(TestHelper helper) throws JsonProcessingException, Exception {
		List<String> cuisines = List.of("a", "b", "c");
		RestaurantBoundary restaurant = new RestaurantBoundary().setName("restaurant").setKosher(true)
				.setCuisines(cuisines);
		mockMvc.perform(helper.requester.postRestaurant(restaurant));

		List<String> actualCuisines = helper.cuisinesRepository.findAll().stream()
				.map(cuisineEntity -> cuisineEntity.getName()).toList();

		assertThat(actualCuisines).containsExactlyInAnyOrderElementsOf(cuisines);
	}
}