package com.att.tdp.bisbis10.tests;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

import com.att.tdp.bisbis10.logic.dishes.DishBoundary;
import com.att.tdp.bisbis10.logic.orders.OrderBoundary;
import com.att.tdp.bisbis10.logic.orders.OrderItem;
import com.att.tdp.bisbis10.logic.restaurants.RestaurantBoundary;
import com.att.tdp.bisbis10.utility.ConfigureComplexTest;
import com.att.tdp.bisbis10.utility.TestHelper;
import com.fasterxml.jackson.core.JsonProcessingException;

@ConfigureComplexTest
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestOrders {
	private @Autowired MockMvc mockMvc;

	@Test
	void testOrder(TestHelper helper) throws JsonProcessingException, Exception {
		mockMvc.perform(
				helper.requester.postRestaurant(new RestaurantBoundary().setName("restaurant").setKosher(true)));
		mockMvc.perform(
				helper.requester.postDish(1l, new DishBoundary().setName("dish").setDescription("wow").setPrice(11)));
		mockMvc.perform(helper.requester
				.postOrder(new OrderBoundary().setRestaurantId(1l).setOrderItems(List.of(new OrderItem(1l, 2)))))
				.andDo(print()).andExpectAll(status().isOk());
	}

	@Test
	void testOrderWithNonexistentRestaurantThrowsNotFoundException(TestHelper helper)
			throws JsonProcessingException, Exception {
		mockMvc.perform(helper.requester
				.postOrder(new OrderBoundary().setRestaurantId(1l).setOrderItems(List.of(new OrderItem(1l, 2)))))
				.andDo(print()).andExpectAll(status().isNotFound());
	}

	@Test
	void testOrderWithNonexistentDishThrowsNotUnprocessableEntity(TestHelper helper)
			throws JsonProcessingException, Exception {
		mockMvc.perform(
				helper.requester.postRestaurant(new RestaurantBoundary().setName("restaurant").setKosher(true)));
		mockMvc.perform(helper.requester
				.postOrder(new OrderBoundary().setRestaurantId(1l).setOrderItems(List.of(new OrderItem(1l, 2)))))
				.andDo(print()).andExpectAll(status().isUnprocessableEntity());
	}
}