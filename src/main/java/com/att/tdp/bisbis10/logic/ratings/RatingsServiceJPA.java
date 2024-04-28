package com.att.tdp.bisbis10.logic.ratings;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.att.tdp.bisbis10.data.RatingEntity;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import com.att.tdp.bisbis10.logic.RatingsService;
import com.att.tdp.bisbis10.repositories.RatingsRepository;
import com.att.tdp.bisbis10.repositories.RestaurantsRepository;

@Service
public class RatingsServiceJPA implements RatingsService {

	private @Autowired RatingsRepository ratingsRepository;
	private @Autowired RestaurantsRepository restaurantsRepository;
	private @Autowired RatingConverter ratingConverter;

	@Override
	public void addRestaurantRating(RatingBoundary rating) {
		RestaurantEntity restaurantInQuestion = restaurantsRepository.findById(rating.getRestaurantId())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant Not Found"));

		RatingEntity newRating = ratingConverter.toEntity(rating);
		newRating.setRestaurant(restaurantInQuestion);

		ratingsRepository.save(newRating);

		List<RatingEntity> restaurantRatings = ratingsRepository.findAllByRestaurantId(rating.getRestaurantId());
		BigDecimal newAverageRating = restaurantRatings.stream().reduce(BigDecimal.valueOf(0),
				(subtotalRatings, ratingEntity) -> subtotalRatings.add(ratingEntity.getRating()), BigDecimal::add)
				.divide(BigDecimal.valueOf(restaurantRatings.size()));

		restaurantInQuestion.setAverageRating(newAverageRating);

		restaurantsRepository.save(restaurantInQuestion);
	}
}