package com.att.tdp.bisbis10.logic.ratings;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.att.tdp.bisbis10.customExceptions.EntityNotFoundException;
import com.att.tdp.bisbis10.data.RatingEntity;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import com.att.tdp.bisbis10.logic.RatingsService;
import com.att.tdp.bisbis10.repositories.RatingRepository;
import com.att.tdp.bisbis10.repositories.RestaurantRepository;

@Service
public class RatingsServiceJPA implements RatingsService {

	private @Autowired RatingRepository ratingRepository;
	private @Autowired RestaurantRepository restaurantRepository;
	private @Autowired RatingConverter ratingConverter;

	@Override
	public void addRestaurantRating(RatingBoundary rating) {
		RestaurantEntity restaurantInQuestion = restaurantRepository.findById(rating.getRestaurantId())
				.orElseThrow(() -> new EntityNotFoundException());

		RatingEntity newRating = ratingConverter.toEntity(rating);
		newRating.setRestaurant(restaurantInQuestion);

		ratingRepository.save(newRating);

		List<RatingEntity> restaurantRatings = ratingRepository.findAllByRestaurantId(rating.getRestaurantId());
		BigDecimal newAverageRating = restaurantRatings.stream().reduce(BigDecimal.valueOf(0),
				(subtotalRatings, ratingEntity) -> subtotalRatings.add(ratingEntity.getRating()), BigDecimal::add)
				.divide(BigDecimal.valueOf(restaurantRatings.size()));

		restaurantInQuestion.setAverageRating(newAverageRating);

		restaurantRepository.save(restaurantInQuestion);
	}
}