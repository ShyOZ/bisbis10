package com.att.tdp.bisbis10.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.att.tdp.bisbis10.data.OrderEntity;

public interface OrdersRepository extends ListCrudRepository<OrderEntity, UUID> {
}