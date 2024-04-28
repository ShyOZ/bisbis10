package com.att.tdp.bisbis10.logic.orders;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.att.tdp.bisbis10.logic.OrdersService;
import com.att.tdp.bisbis10.repositories.DishesRepository;
import com.att.tdp.bisbis10.repositories.OrdersRepository;
import com.att.tdp.bisbis10.repositories.RestaurantsRepository;

@Service
public class OrdersServiceJPA implements OrdersService {

	private @Autowired OrderConverter orderConverter;
	private @Autowired OrdersRepository ordersRepository;
	private @Autowired RestaurantsRepository restaurantsRepository;
	private @Autowired DishesRepository dishesRepository;

	@Override
	public OrderBoundary makeOrder(OrderBoundary orderDetails) {
		restaurantsRepository.findById(orderDetails.getRestaurantId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant Not Found"));

		List<Long> dishIds = getOrderDishIds(orderDetails);

		if (!dishesRepository.findAllByRestaurantId(orderDetails.getRestaurantId()).stream()
				.map(dish -> dish.getDishId()).toList().containsAll(dishIds))
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid Order, Dishes Not Found");

		return orderConverter.toBoundary(ordersRepository.save(orderConverter.toEntity(orderDetails)));
	}

	private List<Long> getOrderDishIds(OrderBoundary orderDetails) {
		return orderDetails.getOrderItems().stream().map(item -> item.getDishId()).toList();
	}
}
