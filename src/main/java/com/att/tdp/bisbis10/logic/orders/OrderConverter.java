package com.att.tdp.bisbis10.logic.orders;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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
		try {
			return new OrderEntity().setId(UUID.randomUUID())
					.setOrderInfo(mapper.writeValueAsString(boundary.getOrderItems()));
		} catch (JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Processing Error", e);
		}
	}

	public OrderBoundary toBoundary(OrderEntity entity) {
		return new OrderBoundary().setOrderId(entity.getId());
	}
}