package com.att.tdp.bisbis10.data;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "restaurants")
public class RestaurantEntity {
	@SequenceGenerator(initialValue = 1, name = "restaurants_seq", allocationSize = 1)
	@GeneratedValue(generator = "restaurants_seq")
	private @Id Long id;

	private String name;

	private BigDecimal averageRating;

	@OneToMany(mappedBy = "restaurant", orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<RatingEntity> ratings;

	private Boolean isKosher;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinTable(name = "restaurant_cuisines", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "cuisine_id"))
	private Set<CuisineEntity> cuisines;

	@OneToMany(mappedBy = "restaurant", orphanRemoval = true, fetch = FetchType.EAGER)
	private Set<DishEntity> dishes;

	private Long nextDishId = 1l;

	public RestaurantEntity() {
	}

	public Long getId() {
		return id;
	}

	public RestaurantEntity setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public RestaurantEntity setName(String name) {
		this.name = name;
		return this;
	}

	public BigDecimal getAverageRating() {
		return averageRating;
	}

	public RestaurantEntity setAverageRating(BigDecimal averageRating) {
		this.averageRating = averageRating;
		return this;
	}

	public Set<RatingEntity> getRatings() {
		return ratings;
	}

	public RestaurantEntity setRatings(Set<RatingEntity> ratings) {
		this.ratings = ratings;
		return this;
	}

	public Boolean isKosher() {
		return isKosher;
	}

	public RestaurantEntity setKosher(Boolean isKosher) {
		this.isKosher = isKosher;
		return this;
	}

	public Set<CuisineEntity> getCuisines() {
		return cuisines;
	}

	public RestaurantEntity setCuisines(Set<CuisineEntity> cuisines) {
		this.cuisines = cuisines;
		return this;
	}

	public Set<DishEntity> getDishes() {
		return dishes;
	}

	public RestaurantEntity setDishes(Set<DishEntity> dishes) {
		this.dishes = dishes;
		return this;
	}

	public Long getNextDishId() {
		return nextDishId;
	}

	public RestaurantEntity setNextDishId(Long nextDishId) {
		this.nextDishId = nextDishId;
		return this;
	}

	public Long getAndIncrementNextDishId() {
		return nextDishId++;
	}
}