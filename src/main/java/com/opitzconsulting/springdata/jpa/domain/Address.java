package com.opitzconsulting.springdata.jpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.util.Assert;

@Entity
@Table(name = "ADDRESS")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String street;

	private String city;

	private String country;

	/**
	 * Creates a new {@link Address} from the given street, city and country.
	 * 
	 * @param street
	 *            must not be {@literal null} or empty.
	 * @param city
	 *            must not be {@literal null} or empty.
	 * @param country
	 *            must not be {@literal null} or empty.
	 */
	public Address(String street, String city, String country) {
		Assert.hasText(street, "Street must not be null or empty!");
		Assert.hasText(city, "City must not be null or empty!");
		Assert.hasText(country, "Country must not be null or empty!");

		this.street = street;
		this.city = city;
		this.country = country;
	}

	protected Address() {
		// for hibernate
	}

	/**
	 * Returns a copy of the current {@link Address} instance which is a new
	 * entity in terms of persistence.
	 * 
	 * @return
	 */
	public Address getCopy() {
		return new Address(this.street, this.city, this.country);
	}

	/**
	 * Returns the street.
	 * 
	 * @return
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Returns the city.
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Returns the country.
	 * 
	 * @return
	 */
	public String getCountry() {
		return country;
	}
}
