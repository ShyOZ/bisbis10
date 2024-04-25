package com.att.tdp.bisbis10.logic.orders;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.att.tdp.bisbis10.data.OrderEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class OrderConverter {

	private ObjectMapper mapper;

	@PostConstruct
	public void init() {
		mapper = new ObjectMapper();
	}

	public OrderEntity toEntity(OrderBoundary boundary) {
		OrderEntity entity = new OrderEntity();

		try {
			entity.setId(UUID.randomUUID().toString());
			entity.setOrderInfo(mapper.writeValueAsString(boundary.getOrderItems()));
		} catch (JsonProcessingException e) {
			throw new RuntimeException();
		}

		return entity;
	}

	public OrderBoundary toBoundary(OrderEntity entity) {
		OrderBoundary boundary = new OrderBoundary();
		boundary.setId(entity.getId());

		return boundary;
	}

}
