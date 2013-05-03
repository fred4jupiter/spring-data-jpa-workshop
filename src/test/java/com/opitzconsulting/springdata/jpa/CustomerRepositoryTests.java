package com.opitzconsulting.springdata.jpa;

import static com.opitzconsulting.springdata.jpa.specs.CustomerSpecs.hasBirthdayOn;
import static com.opitzconsulting.springdata.jpa.specs.CustomerSpecs.hasSalesOfMoreThan;
import static com.opitzconsulting.springdata.jpa.specs.CustomerSpecs.isLongTermCustomer;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.data.jpa.domain.Specifications.where;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.opitzconsulting.springdata.jpa.domain.Customer;
import com.opitzconsulting.springdata.jpa.domain.Customer_;
import com.opitzconsulting.springdata.jpa.repository.CustomerRepository;

import java.util.List;

public class CustomerRepositoryTests extends AbstractTestingBase {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerFactory customerFactory;

	@Test
	public void findCustomerByName() {
		LocalDate birthday = new LocalDate(1976, 2, 25);
		Customer customer = customerFactory.createCustomer(birthday, "Michael", "Staehler");
		assertNotNull(customer);

		Customer savedCustomer = this.customerRepository.save(customer);
		assertThat(savedCustomer.getId(), notNullValue());

		Customer foundCustomer = this.customerRepository.findOne(savedCustomer.getId());
		assertThat(foundCustomer, equalTo(savedCustomer));
	}

	@Test
	public void findCustomersWithBirthdayTodayAndIsLongTermCustomer_JPA() {
		LocalDate today = new LocalDate();
		Customer customer = createLongTermCustomerWithBirthdayOn(today);

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
		Root<Customer> root = query.from(Customer.class);

		Predicate hasBirthday = builder.equal(root.get(Customer_.birthday), today);
		Predicate isLongTermCustomer = builder.lessThan(root.get(Customer_.createdAt), today.minusYears(2));
		query.where(builder.and(hasBirthday, isLongTermCustomer));

        List<Customer> customers = entityManager.createQuery(query.select(root)).getResultList();

        assertThat(customers.size(), equalTo(1));
        assertThat(customers.get(0), equalTo(customer));
	}

	@Test
	public void findCustomersWithBirthdayTodayAndIsLongTermCustomer_Specifications() {
		LocalDate today = new LocalDate();
		Customer customer = createLongTermCustomerWithBirthdayOn(today);

		Customer foundCustomer = customerRepository.findOne(where(hasBirthdayOn(today)).and(isLongTermCustomer()));
		assertEquals(customer, foundCustomer);
	}

	@Test
	public void findCustomerWithBirthdayOnTodayAndSalesAmountGreaterThan() {
		final LocalDate todayAsBirthday = new LocalDate();
		final Double salesAmount = Double.valueOf(5100);
		final Customer customer = customerFactory.createCustomer(todayAsBirthday, salesAmount);
		customerRepository.save(customer);

		Customer foundCustomer = customerRepository.findByBirthdayAndSalesAmountGreaterThan(customer.getBirthday(), salesAmount - 50);
		assertEquals(customer, foundCustomer);
	}

	@Test
	public void findAllLongTermCustomersOrSalesOfMoreThan() {
		final Double salesAmout = 50.0;
		final Customer customer = customerFactory.createCustomer(salesAmout);
		customerRepository.save(customer);

		Customer foundCustomer = customerRepository.findOne(where(isLongTermCustomer()).or(hasSalesOfMoreThan(45.4)));
		assertEquals(customer, foundCustomer);
	}

	private Customer createLongTermCustomerWithBirthdayOn(LocalDate birthday) {
		final Double salesAmout = 100.0;
		final LocalDate createdAt = new LocalDate().minusYears(2).minusDays(3);
		Customer customer = customerFactory.createCustomer(birthday, salesAmout, createdAt);
		customerRepository.save(customer);
		return customer;
	}
}
