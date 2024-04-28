package com.att.tdp.bisbis10.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.att.tdp.bisbis10.data.DishEntity;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import com.att.tdp.bisbis10.logic.dishes.DishBoundary;
import com.att.tdp.bisbis10.logic.restaurants.RestaurantBoundary;
import com.att.tdp.bisbis10.utility.ConfigureComplexTest;
import com.att.tdp.bisbis10.utility.TestHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ConfigureComplexTest
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestDishes {
	private @Autowired MockMvc mockMvc;

	@Test
	void testAddDishToRestaurant(TestHelper helper) throws JsonProcessingException, Exception {
		helper.restaurantsRepository.save(new RestaurantEntity().setName("restaurant").setKosher(true));

		DishBoundary boundary = new DishBoundary().setName("a dishy dish").setDescription("a descriptive description")
				.setPrice(10);

		mockMvc.perform(helper.requester.postDish(1l, boundary)).andExpectAll(status().isCreated(),
				content().string(emptyOrNullString()));

		assertThat(helper.dishesRepository.findByRestaurantIdAndDishId(1l, 1l)).isPresent();
	}

	@Test
	void testAddDishToNonexistentRestaurantThrowsNotFoundException(TestHelper helper)
			throws JsonProcessingException, Exception {
		DishBoundary boundary = new DishBoundary().setName("a dishy dish").setDescription("a descriptive description")
				.setPrice(10);

		mockMvc.perform(helper.requester.postDish(1l, boundary)).andExpect(status().isNotFound());
	}

	@Test
	void testUpdateDish(TestHelper helper) throws JsonProcessingException, Exception {
		helper.restaurantsRepository.save(new RestaurantEntity().setName("restaurant").setKosher(true));

		mockMvc.perform(helper.requester.postDish(1l,
				new DishBoundary().setName("a dishy dish").setDescription("a descriptive description").setPrice(10)));

		mockMvc.perform(helper.requester.putDishUpdate(1l, 1l,
				new DishBoundary().setDescription("another descriptive description").setPrice(11)));

		DishEntity expected = new DishEntity().setDescription("another descriptive description").setPrice(11);

		DishEntity actual = helper.dishesRepository.findByRestaurantIdAndDishId(1l, 1l).get();

		assertThat(actual).usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expected);
	}

	@Test
	void testUpdateNonexistentDishThrowsNotFoundException(TestHelper helper) throws JsonProcessingException, Exception {
		mockMvc.perform(helper.requester.putDishUpdate(1l, 1l,
				new DishBoundary().setDescription("a descriptive description").setPrice(10)))
				.andExpect(status().isNotFound());
	}

	@Test
	void testDeleteDish(TestHelper helper) throws JsonProcessingException, Exception {

		mockMvc.perform(
				helper.requester.postRestaurant(new RestaurantBoundary().setName("restaurant").setKosher(true)));

		mockMvc.perform(helper.requester.postDish(1l,
				new DishBoundary().setName("a dishy dish").setDescription("a descriptive description").setPrice(10)));

		mockMvc.perform(helper.requester.deleteDish(1l, 1l)).andExpectAll(status().isNoContent(),
				content().string(emptyOrNullString()));
	}

	@Test
	void testDeleteNonexistentDishThrowsNotFoundException(TestHelper helper) throws JsonProcessingException, Exception {
		mockMvc.perform(helper.requester.deleteDish(1l, 1l)).andExpect(status().isNotFound());
	}

	@Test
	void testGetAllDishes(TestHelper helper) throws JsonProcessingException, Exception {

		helper.restaurantsRepository.save(new RestaurantEntity().setName("restaurant").setKosher(true));

		DishBoundary dish1 = new DishBoundary().setName("a dishy dish").setDescription("a descriptive description")
				.setPrice(10);

		DishBoundary dish2 = new DishBoundary().setName("another dishy dish")
				.setDescription("a very descriptive description").setPrice(20);

		mockMvc.perform(helper.requester.postDish(1l, dish1));

		mockMvc.perform(helper.requester.postDish(1l, dish2));

		List<DishBoundary> expectedDishes = List.of(
				new DishBoundary().setId(1l).setName(dish1.getName()).setDescription(dish1.getDescription())
						.setPrice(dish1.getPrice()),
				new DishBoundary().setId(2l).setName(dish2.getName()).setDescription(dish2.getDescription())
						.setPrice(dish2.getPrice()));

		MvcResult result = mockMvc.perform(helper.requester.getAllRestaurantDishes(1l)).andExpect(status().isOk())
				.andReturn();

		ObjectMapper mapper = new ObjectMapper();

		DishBoundary[] actualDishes = mapper.readValue(result.getResponse().getContentAsString(), DishBoundary[].class);

		assertThat(actualDishes)
				.usingRecursiveFieldByFieldElementComparator(
						RecursiveComparisonConfiguration.builder().withIgnoreAllExpectedNullFields(true).build())
				.containsExactlyInAnyOrderElementsOf(expectedDishes);
	}

	@Test
	void testGetAllDishesWithNonexistentRestaurantThrowsNonFoundException(TestHelper helper)
			throws JsonProcessingException, Exception {

		mockMvc.perform(helper.requester.getAllRestaurantDishes(1l)).andExpect(status().isNotFound());
	}
}