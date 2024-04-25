package com.att.tdp.bisbis10.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.bisbis10.logic.OrdersService;
import com.att.tdp.bisbis10.logic.orders.OrderBoundary;

@RestController
@RequestMapping(path = "/order")
public class OrdersController {
	private @Autowired OrdersService ordersService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public OrderBoundary order(@RequestBody OrderBoundary orderDetails) {
		return ordersService.makeOrder(orderDetails);
	}
}