package com.att.tdp.bisbis10.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {
	@GeneratedValue(strategy = GenerationType.UUID)
	private @Id String id;

	private @Lob String orderInfo;

	public OrderEntity() {
	}

	public OrderEntity(String id, String orderInfo) {
		this.id = id;
		this.orderInfo = orderInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderInfo() {
		return orderInfo;
	}

	public void setOrderInfo(String orderInfo) {
		this.orderInfo = orderInfo;
	}
}