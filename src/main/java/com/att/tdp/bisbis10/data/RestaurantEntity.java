package com.att.tdp.bisbis10.data;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant", orphanRemoval = true)
	private Set<RatingEntity> ratings;

	@Column(name = "is_kosher") // for naming conventions
	private Boolean isKosher;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinTable(name = "restaurant_cuisines", joinColumns = @JoinColumn(name = "restaurant_id"), inverseJoinColumns = @JoinColumn(name = "cuisine_id"))
	private Set<CuisineEntity> cuisines;

	public RestaurantEntity() {
	}

	public RestaurantEntity(Long id, String name, BigDecimal rating, Boolean isKosher) {
		this.id = id;
		this.name = name;
		this.averageRating = rating;
		this.isKosher = isKosher;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(BigDecimal averageRating) {
		this.averageRating = averageRating;
	}

	public Boolean isKosher() {
		return isKosher;
	}

	public void setKosher(Boolean isKosher) {
		this.isKosher = isKosher;
	}

	public Set<CuisineEntity> getCuisines() {
		return cuisines;
	}

	public void setCuisines(Set<CuisineEntity> cuisines) {
		this.cuisines = cuisines;
	}
}