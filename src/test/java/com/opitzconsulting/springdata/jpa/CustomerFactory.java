package com.opitzconsulting.springdata.jpa;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import com.opitzconsulting.springdata.jpa.domain.Customer;

@Component
public class CustomerFactory {

	public Customer createCustomer(LocalDate birthday, Double salesAmount, LocalDate createdAt) {
		return new Customer(salesAmount, birthday, "Fred", "Feuerstein", createdAt);
	}

	public Customer createCustomer(LocalDate birthday, String firstname, String lastname) {
		return new Customer(new Double(0), birthday, firstname, lastname, new LocalDate());
	}

	public Customer createCustomer(LocalDate birthday, Double salesAmount) {
		return new Customer(salesAmount, birthday, "Fred", "Feuerstein", new LocalDate());
	}

	public Customer createCustomer(Double salesAmount) {
		return new Customer(salesAmount, new LocalDate(), "Fred", "Feuerstein", new LocalDate());
	}
}
