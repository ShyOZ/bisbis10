package com.att.tdp.bisbis10.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.att.tdp.bisbis10.logic.RatingsService;
import com.att.tdp.bisbis10.logic.ratings.RatingBoundary;

@RestController
@RequestMapping(path = "/ratings")
public class RatingsController {
	private @Autowired RatingsService ratingsService;

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void addRestaurantRating(@RequestBody RatingBoundary rating) {
		ratingsService.addRestaurantRating(rating);
	}
}