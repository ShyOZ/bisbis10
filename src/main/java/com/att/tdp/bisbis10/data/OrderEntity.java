package com.att.tdp.bisbis10.data;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {
	@GeneratedValue(strategy = GenerationType.UUID)
	private @Id UUID id;

	@Column(columnDefinition = "text")
	private String orderInfo;

	public OrderEntity() {
	}

	public UUID getId() {
		return id;
	}

	public OrderEntity setId(UUID id) {
		this.id = id;
		return this;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public OrderEntity setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
		return this;
	}
}