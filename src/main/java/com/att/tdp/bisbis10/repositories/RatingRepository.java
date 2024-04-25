package com.att.tdp.bisbis10.repositories;

import org.springframework.data.repository.ListCrudRepository;

import com.att.tdp.bisbis10.data.RatingEntity;

public interface RatingRepository extends ListCrudRepository<RatingEntity, Long> {
}