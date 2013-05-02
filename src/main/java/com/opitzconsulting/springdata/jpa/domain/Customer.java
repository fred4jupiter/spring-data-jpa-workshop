package com.opitzconsulting.springdata.jpa.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.joda.time.LocalDate;
import org.springframework.util.Assert;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

	@Id
	@GeneratedValue
	private Long id;

	private LocalDate createdAt;

	private Double salesAmount;

	private LocalDate birthday;

	private String firstname;

	private String lastname;
	
	@Column(unique = true)
	private EmailAddress emailAddress;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "customer_id")
	private Set<Address> addresses = new HashSet<Address>();
	
	protected Customer() {
		// for hibernate
	}

	public Customer(Double salesAmount, LocalDate birthday, String firstname, String lastname) {
		Assert.hasText(firstname);
		Assert.hasText(lastname);
		Assert.notNull(birthday);
		Assert.notNull(salesAmount);
		this.salesAmount = salesAmount;
		this.birthday = birthday;
		this.firstname = firstname;
		this.lastname = lastname;
		this.createdAt = new LocalDate();
	}

	public Customer(Double salesAmount, LocalDate birthday, String firstname, String lastname, LocalDate createdAt) {
		this(salesAmount, birthday, firstname, lastname);
		this.createdAt = createdAt;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public Double getSalesAmount() {
		return salesAmount;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public Long getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}
}
