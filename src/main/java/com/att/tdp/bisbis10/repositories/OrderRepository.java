package com.att.tdp.bisbis10.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.att.tdp.bisbis10.data.OrderEntity;

public interface OrderRepository extends ListCrudRepository<OrderEntity, String> {
}