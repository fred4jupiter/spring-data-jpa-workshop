package com.opitzconsulting.springdata.jpa.repository;

import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.opitzconsulting.springdata.jpa.domain.Customer;
import com.opitzconsulting.springdata.jpa.domain.EmailAddress;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer>,
		QueryDslPredicateExecutor<Customer> {

	Customer findByBirthdayAndSalesAmountGreaterThan(LocalDate birthday, Double salesAmount);

    List<Customer> findByEmailAddressOrderByLastnameAsc(EmailAddress emailAddress);
}
