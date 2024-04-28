package com.att.tdp.bisbis10.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import com.att.tdp.bisbis10.data.CuisineEntity;

public interface CuisinesRepository extends ListCrudRepository<CuisineEntity, Long> {
	public Optional<CuisineEntity> findByNameIgnoreCase(@Param("name") String cuisineName);

	public List<CuisineEntity> findAllByNameIgnoreCaseIn(@Param("names") List<String> cuisineNames);

	public List<CuisineEntity> findAllByNameIgnoreCaseNotIn(@Param("names") List<String> cuisineNames);
}