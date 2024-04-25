package com.att.tdp.bisbis10.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.tdp.bisbis10.data.CuisineEntity;
import com.att.tdp.bisbis10.repositories.CuisineRepository;
import com.att.tdp.bisbis10.repositories.DishRepository;
import com.att.tdp.bisbis10.repositories.OrderRepository;
import com.att.tdp.bisbis10.repositories.RatingRepository;
import com.att.tdp.bisbis10.repositories.RestaurantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TestHelper {
	public final CuisineRepository cuisineRepository;
	public final DishRepository dishRepository;
	public final OrderRepository orderRepository;
	public final RatingRepository ratingRepository;
	public final RestaurantRepository restaurantRepository;

	public final TestRequester requester;
	private Statement statement;
	public ObjectMapper mapper;

	@Autowired
	public TestHelper(CuisineRepository cuisineRepository, DishRepository dishRepository,
			OrderRepository orderRepository, RatingRepository ratingRepository,
			RestaurantRepository restaurantRepository, TestRequester requester) throws SQLException {
		this.cuisineRepository = cuisineRepository;
		this.dishRepository = dishRepository;
		this.orderRepository = orderRepository;
		this.ratingRepository = ratingRepository;
		this.requester = requester;
		this.restaurantRepository = restaurantRepository;

		Connection conn = DriverManager.getConnection("jdbc:h2:mem:test_db", "bisbis10", "bisbis10");
		statement = conn.createStatement();
		mapper = new ObjectMapper();
	}

	public void reset() throws SQLException {
		cuisineRepository.deleteAll();
		dishRepository.deleteAll();
		orderRepository.deleteAll();
		ratingRepository.deleteAll();
		restaurantRepository.deleteAll();

		statement.execute("ALTER SEQUENCE cuisines_seq RESTART with 1;");
		statement.execute("ALTER SEQUENCE ratings_seq RESTART with 1;");
		statement.execute("ALTER SEQUENCE restaurants_seq RESTART with 1;");
	}

	public Set<CuisineEntity> getCuisineSetFromNameList(List<String> cuisines) {
		return cuisines.stream().map(cuisine -> {
			return cuisineRepository.findByNameIgnoreCase(cuisine).orElseGet(() -> {
				CuisineEntity newCuisine = new CuisineEntity();
				newCuisine.setName(cuisine);
				return newCuisine;
			});
		}).collect(Collectors.toSet());
	}
}
