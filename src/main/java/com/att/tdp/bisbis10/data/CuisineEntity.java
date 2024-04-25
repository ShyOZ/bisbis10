package com.att.tdp.bisbis10.data;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuisines")
public class CuisineEntity {
	@SequenceGenerator(initialValue = 1, name = "cuisines_seq", allocationSize = 1)
	@GeneratedValue(generator = "cuisines_seq")
	private @Id Long id;

	private String name;

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	@JoinTable(name = "restaurant_cuisines", joinColumns = @JoinColumn(name = "cuisine_id"), inverseJoinColumns = @JoinColumn(name = "restaurant_id"))
	Set<RestaurantEntity> restaurants;

	public CuisineEntity() {
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

	public Set<RestaurantEntity> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Set<RestaurantEntity> restaurants) {
		this.restaurants = restaurants;
	}
}