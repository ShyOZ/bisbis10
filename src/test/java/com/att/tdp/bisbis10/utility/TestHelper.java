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
import com.att.tdp.bisbis10.repositories.CuisinesRepository;
import com.att.tdp.bisbis10.repositories.DishesRepository;
import com.att.tdp.bisbis10.repositories.OrdersRepository;
import com.att.tdp.bisbis10.repositories.RatingsRepository;
import com.att.tdp.bisbis10.repositories.RestaurantsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class TestHelper {
	public final CuisinesRepository cuisinesRepository;
	public final DishesRepository dishesRepository;
	public final OrdersRepository ordersRepository;
	public final RatingsRepository ratingsRepository;
	public final RestaurantsRepository restaurantsRepository;

	public final TestRequester requester;
	private Statement statement;
	public ObjectMapper mapper;

	@Autowired
	public TestHelper(CuisinesRepository cuisinesRepository, DishesRepository dishesRepository,
			OrdersRepository ordersRepository, RatingsRepository ratingsRepository,
			RestaurantsRepository restaurantsRepository, TestRequester requester) throws SQLException {
		this.cuisinesRepository = cuisinesRepository;
		this.dishesRepository = dishesRepository;
		this.ordersRepository = ordersRepository;
		this.ratingsRepository = ratingsRepository;
		this.requester = requester;
		this.restaurantsRepository = restaurantsRepository;

		Connection conn = DriverManager.getConnection("jdbc:h2:mem:test_db", "bisbis10", "bisbis10");
		statement = conn.createStatement();
		mapper = new ObjectMapper();
	}

	public void reset() throws SQLException {
		cuisinesRepository.deleteAll();
		dishesRepository.deleteAll();
		ordersRepository.deleteAll();
		ratingsRepository.deleteAll();
		restaurantsRepository.deleteAll();

		statement.execute("ALTER SEQUENCE cuisines_seq RESTART with 1;");
		statement.execute("ALTER SEQUENCE ratings_seq RESTART with 1;");
		statement.execute("ALTER SEQUENCE restaurants_seq RESTART with 1;");
	}

	public Set<CuisineEntity> getCuisineSetFromNameList(List<String> cuisines) {
		return cuisines.stream().map(cuisineName -> {
			return cuisinesRepository.findByNameIgnoreCase(cuisineName).orElse(new CuisineEntity().setName(cuisineName));
		}).collect(Collectors.toSet());
	}
}
