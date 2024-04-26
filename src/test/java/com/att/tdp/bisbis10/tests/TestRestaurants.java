package com.att.tdp.bisbis10.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
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
import com.att.tdp.bisbis10.logic.restaurants.RestaurantBoundary;
import com.att.tdp.bisbis10.utility.ConfigureComplexTest;
import com.att.tdp.bisbis10.utility.TestHelper;
import com.fasterxml.jackson.core.JsonProcessingException;

@ConfigureComplexTest
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestRestaurants {
	private @Autowired MockMvc mockMvc;

	@Test
	void testAddRestaurant(TestHelper helper) throws JsonProcessingException, Exception {
		mockMvc.perform(
				helper.requester.postRestaurant(new RestaurantBoundary(null, "restaurant", null, true, List.of())))
				.andExpectAll(status().isCreated(), content().string(emptyOrNullString()));

		long createdRestaurantId = 1l;

		RestaurantEntity actualRestaurant = helper.restaurantRepository.findById(createdRestaurantId)
				.orElseGet(() -> fail("failed to find restaurant with id %li / restaurant was not created properly!"
						.formatted(createdRestaurantId)));

		RestaurantEntity expectedRestaurant = new RestaurantEntity(createdRestaurantId, "restaurant", null, true);
		expectedRestaurant.setCuisines(Set.of());

		assertThat(actualRestaurant).usingRecursiveComparison().ignoringExpectedNullFields()
				.isEqualTo(expectedRestaurant);
	}

	@Test
	void testGetRestaurantWithNoDishes(TestHelper helper) throws Exception {
		long createdRestaurantId = helper.restaurantRepository
				.save(new RestaurantEntity(null, "restaurant", null, true)).getId();

		RestaurantBoundary expectedRestaurant = new RestaurantBoundary(createdRestaurantId, "restaurant", null, true,
				List.of());
		expectedRestaurant.setDishes(List.of());

		MvcResult result = mockMvc.perform(helper.requester.getRestaurant(createdRestaurantId))
				.andExpect(status().isOk()).andReturn();

		RestaurantBoundary actualRestaurant = helper.mapper.readValue(result.getResponse().getContentAsString(),
				RestaurantBoundary.class);

		assertThat(actualRestaurant).usingRecursiveComparison().isEqualTo(expectedRestaurant);

		mockMvc.perform(helper.requester.getRestaurant(createdRestaurantId + 1)).andExpect(status().isNotFound());

	}

	@Test
	void testUpdateRestaurant(TestHelper helper) throws JsonProcessingException, Exception {
		List<String> cuisines = List.of("a", "b", "c");

		RestaurantBoundary original = new RestaurantBoundary(null, "restaurant", null, true, cuisines);
		mockMvc.perform(helper.requester.postRestaurant(original));

		RestaurantBoundary update = new RestaurantBoundary(null, original.getName(), null, null,
				List.of(cuisines.get(0)));

		long createdRestaurantId = 1l;

		RestaurantEntity expectedUpdatedRestaurant = new RestaurantEntity(createdRestaurantId, "restaurant", null,
				true);
		expectedUpdatedRestaurant.setCuisines(helper.getCuisineSetFromNameList(List.of(cuisines.get(0))));

		mockMvc.perform(helper.requester.putRestaurantUpdate(update, createdRestaurantId)).andExpectAll(status().isOk(),
				content().string(emptyOrNullString()));

		RestaurantEntity actualUpdatedRestaurant = helper.restaurantRepository.findById(createdRestaurantId)
				.orElseGet(() -> fail("failed to find restaurant with id %li / restaurant was not created properly!"
						.formatted(createdRestaurantId)));

		assertThat(actualUpdatedRestaurant)
				.usingRecursiveComparison(RecursiveComparisonConfiguration.builder().withIgnoreCollectionOrder(true)
						.withIgnoredFields("cuisines.restaurants", "ratings").build())
				.isEqualTo(expectedUpdatedRestaurant);
	}

	@Test
	void testDeleteRestaurant(TestHelper helper) throws JsonProcessingException, Exception {

		for (int i = 1; i <= 5; i++)
			mockMvc.perform(helper.requester
					.postRestaurant(new RestaurantBoundary(null, "restaurant" + i, null, true, List.of())));

		long toDelete = 4l;

		mockMvc.perform(helper.requester.deleteRestaurant(toDelete)).andExpectAll(status().isNoContent(),
				content().string(emptyOrNullString()));

		List<RestaurantEntity> expectedRestaurantList = LongStream.of(1, 2, 3, 5).mapToObj(i -> {
			RestaurantEntity rest = new RestaurantEntity(i, "restaurant" + i, null, true);
			rest.setCuisines(Set.of());
			return rest;
		}).toList();

		List<RestaurantEntity> actualRestaurantList = helper.restaurantRepository.findAll();

		assertThat(actualRestaurantList).usingRecursiveFieldByFieldElementComparatorIgnoringFields("ratings")
				.containsExactlyInAnyOrderElementsOf(expectedRestaurantList);
	}

	@Test
	void testGetAllRestaurantsAndGetAllRestaurantsByCuisine(TestHelper helper)
			throws JsonProcessingException, Exception {
		List<String> cuisines = List.of("a", "b", "c");

		RestaurantBoundary restABC = new RestaurantBoundary(1l, "restaurantABC", null, true, cuisines);

		RestaurantBoundary restAC = new RestaurantBoundary(2l, "restaurantAC", null, true,
				List.of(cuisines.get(0), cuisines.get(2)));

		RestaurantBoundary restA = new RestaurantBoundary(3l, "restaurantA", null, true, List.of(cuisines.get(0)));

		mockMvc.perform(helper.requester.postRestaurant(restABC));
		mockMvc.perform(helper.requester.postRestaurant(restAC));
		mockMvc.perform(helper.requester.postRestaurant(restA));

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

			assertThat(actualList)
					.usingRecursiveFieldByFieldElementComparator(
							RecursiveComparisonConfiguration.builder().withIgnoreCollectionOrder(true).build())
					.containsExactlyInAnyOrderElementsOf(entry.getValue());
		}
	}

	@Test
	void testDeleteRestaurantDoesNotDeleteCuisines(TestHelper helper) throws JsonProcessingException, Exception {
		List<String> cuisines = List.of("a", "b", "c");

		RestaurantBoundary restaurant = new RestaurantBoundary(null, "restaurant", null, true, cuisines);

		mockMvc.perform(helper.requester.postRestaurant(restaurant));

		helper.restaurantRepository.deleteAll();

		List<String> actualCuisines = helper.cuisineRepository.findAll().stream()
				.map(cuisineEntity -> cuisineEntity.getName()).toList();

		assertThat(actualCuisines).containsExactlyInAnyOrderElementsOf(cuisines);
	}

	@Test
	void testAddRestaurantAlsoAddsCuisines(TestHelper helper) throws JsonProcessingException, Exception {
		List<String> cuisines = List.of("a", "b", "c");
		RestaurantBoundary restaurant = new RestaurantBoundary(null, "restaurant", null, true, cuisines);
		mockMvc.perform(helper.requester.postRestaurant(restaurant));

		List<String> actualCuisines = helper.cuisineRepository.findAll().stream()
				.map(cuisineEntity -> cuisineEntity.getName()).toList();

		assertThat(actualCuisines).containsExactlyInAnyOrderElementsOf(cuisines);
	}
}