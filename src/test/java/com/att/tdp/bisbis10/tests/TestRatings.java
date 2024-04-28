package com.att.tdp.bisbis10.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.text.IsEmptyString.emptyOrNullString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;

import com.att.tdp.bisbis10.data.RatingEntity;
import com.att.tdp.bisbis10.data.RestaurantEntity;
import com.att.tdp.bisbis10.logic.ratings.RatingBoundary;
import com.att.tdp.bisbis10.utility.ConfigureComplexTest;
import com.att.tdp.bisbis10.utility.TestHelper;
import com.fasterxml.jackson.core.JsonProcessingException;

@ConfigureComplexTest
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestRatings {
	private @Autowired MockMvc mockMvc;

	@Test
	void testAddRatingsToRestaurant(TestHelper helper) throws JsonProcessingException, Exception {
		RestaurantEntity restaurant = new RestaurantEntity().setName("restaurant").setKosher(false);
		helper.restaurantsRepository.save(restaurant);

		List<BigDecimal> reviews = IntStream.rangeClosed(1, 5).mapToObj(i -> BigDecimal.valueOf(i)).toList();
		BigDecimal[] averageReviews = reviews.toArray(BigDecimal[]::new);

		Arrays.parallelPrefix(averageReviews, BigDecimal::add);

		for (int i = 0; i < averageReviews.length; i++)
			averageReviews[i] = averageReviews[i].divide(BigDecimal.valueOf(i + 1));

		for (int i = 0; i < reviews.size(); i++) {
			mockMvc.perform(
					helper.requester.postRating(new RatingBoundary().setRestaurantId(1l).setRating(reviews.get(i))))
					.andExpectAll(status().isOk(), content().string(emptyOrNullString()));

			assertThat(helper.restaurantsRepository.findById(1l).orElseThrow().getAverageRating())
					.isEqualByComparingTo(averageReviews[i]);
		}
	}

	@Test
	void testDeleteRestaurantAlsoDeletesRatings(TestHelper helper) {

		final RestaurantEntity restaurant = helper.restaurantsRepository
				.save(new RestaurantEntity().setName("restaurant").setKosher(false));

		IntStream.rangeClosed(1, 5)
				.mapToObj(i -> new RatingEntity().setRating(BigDecimal.valueOf(i)).setRestaurant(restaurant))
				.forEach(helper.ratingsRepository::save);

		assertThat(helper.ratingsRepository.findAllByRestaurantId(1l)).hasSize(5);

		helper.restaurantsRepository.deleteById(1l);

		assertThat(helper.ratingsRepository.findAllByRestaurantId(1l)).hasSize(0);
	}

	@Test
	void testAddingRatingToNonexistentRestaurantThrowsNotFoundException(TestHelper helper)
			throws JsonProcessingException, Exception {
		mockMvc.perform(helper.requester
				.postRating(new RatingBoundary().setRestaurantId(1l).setRating(BigDecimal.valueOf(12.3))))
				.andExpect(status().isNotFound());
	}
}