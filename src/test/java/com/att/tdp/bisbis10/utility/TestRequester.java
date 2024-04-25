package com.att.tdp.bisbis10.utility;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.att.tdp.bisbis10.logic.dishes.DishBoundary;
import com.att.tdp.bisbis10.logic.orders.OrderBoundary;
import com.att.tdp.bisbis10.logic.ratings.RatingBoundary;
import com.att.tdp.bisbis10.logic.restaurants.RestaurantBoundary;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class TestRequester {

	private TestRestTemplate template;

	private ObjectMapper mapper;

	@PostConstruct
	private void init() {
		mapper = new ObjectMapper();
	}

	@Autowired
	public TestRequester(Environment environment) {
		LocalHostUriTemplateHandler localhostHandler = new LocalHostUriTemplateHandler(environment);
		template = new TestRestTemplate();
		template.setUriTemplateHandler(localhostHandler);
	}

	public MockHttpServletRequestBuilder getAllRestaurants() {
		return get("/restaurants");
	}

	public MockHttpServletRequestBuilder getAllRestaurantsByCuisine(String cuisine) {
		return get("/restaurants").param("cuisine", cuisine);
	}

	public MockHttpServletRequestBuilder getRestaurant(Long restaurantId) {
		return get("/restaurants/{id}", restaurantId);
	}

	public MockHttpServletRequestBuilder postRestaurant(RestaurantBoundary restaurant) throws JsonProcessingException {
		return post("/restaurants").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(restaurant));
	}

	public MockHttpServletRequestBuilder putRestaurantUpdate(RestaurantBoundary restaurant, Long restaurantId)
			throws JsonProcessingException {
		return put("/restaurants/{id}", restaurantId).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(restaurant));
	}

	public MockHttpServletRequestBuilder deleteRestaurant(Long restaurantId) {
		return delete("/restaurants/{id}", restaurantId);
	}

	public MockHttpServletRequestBuilder postRating(RatingBoundary rating) throws JsonProcessingException {
		return post("/ratings").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(rating));
	}

	public MockHttpServletRequestBuilder postOrder(OrderBoundary order) throws JsonProcessingException {
		return post("/order").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(order));
	}

	public MockHttpServletRequestBuilder postDish(Long restaurantId, DishBoundary dish) throws JsonProcessingException {
		return post("/restaurants/{id}/dishes", restaurantId).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dish));
	}

	public MockHttpServletRequestBuilder putDishUpdate(Long restaurantId, Long dishId, DishBoundary dish)
			throws JsonProcessingException {
		return put("/restaurants/{id}/dishes/{dishId}", restaurantId, dishId).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dish));
	}

	public MockHttpServletRequestBuilder deleteDish(Long restaurantId, Long dishId) {
		return delete("/restaurants/{id}/dishes/{dishId}", restaurantId, dishId);
	}

	public MockHttpServletRequestBuilder getAllRestaurantDishes(Long restaurantId) {
		return get("/restaurants/{id}/dishes", restaurantId);
	}
}
