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

	@Column(name = "CREATED_AT")
	private LocalDate createdAt;

	@Column(name = "SALES_AMOUNT")
	private Double salesAmount;

	@Column(name = "BIRTHDAY")
	private LocalDate birthday;

	@Column(name = "FIRST_NAME")
	private String firstname;

	@Column(name = "LAST_NAME")
	private String lastname;

	@Column(name = "EMAIL", unique = true)
	private EmailAddress emailAddress;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "CUSTOMER_ID")
	private Set<Address> addresses = new HashSet<Address>();

	protected Customer() {
		// for hibernate
	}

	public Customer(LocalDate birthday, String firstname, String lastname) {
		Assert.hasText(firstname);
		Assert.hasText(lastname);
		Assert.notNull(birthday);
		Assert.notNull(salesAmount);
		this.salesAmount = new Double(0);
		this.birthday = birthday;
		this.firstname = firstname;
		this.lastname = lastname;
		this.createdAt = new LocalDate();
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
