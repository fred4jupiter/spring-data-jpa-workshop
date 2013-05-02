package com.opitzconsulting.springdata.jpa;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.opitzconsulting.springdata.jpa.domain.Customer;
import com.opitzconsulting.springdata.jpa.domain.QCustomer;
import com.opitzconsulting.springdata.jpa.repository.CustomerRepository;

public class QueryDslJpaTest extends AbstractTestingBase {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerFactory customerFactory;

	@Test
	public void findCustomerWithBirthdayOn_JPA() {
		final Customer customer = createAndSaveCustomer();

		JPQLQuery query = new JPAQuery(entityManager);
		QCustomer qCustomer = QCustomer.customer;

		Customer michael = query.from(qCustomer).where(qCustomer.birthday.eq(customer.getBirthday())).uniqueResult(qCustomer);
		assertEquals(customer, michael);
	}

	@Ignore("fix me")
	@Test
	public void findCustomerWithBirthdayOn_SpringDataJpa() {
		final Customer customer = createAndSaveCustomer();

		QCustomer qCustomer = QCustomer.customer;

		Customer michael = customerRepository.findOne(qCustomer.birthday.eq(customer.getBirthday()));
		assertEquals(customer, michael);
	}

	private Customer createAndSaveCustomer() {
		LocalDate birthday = new LocalDate(1976, 2, 25);
		Customer customer = customerFactory.createCustomer(birthday, "Fred", "Feuerstein");
		customerRepository.save(customer);
		return customer;
	}

}
