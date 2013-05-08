package com.opitzconsulting.springdata.jpa;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.opitzconsulting.springdata.jpa.domain.Customer;
import com.opitzconsulting.springdata.jpa.domain.QCustomer;
import com.opitzconsulting.springdata.jpa.repository.CustomerRepository;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.*;

public class QueryDslJpaTest extends AbstractTestingBase {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findCustomerWithBirthdayOn_using_QueryDSL_with_JPA() {
        final Customer customer = createAndSaveCustomer();

        JPQLQuery query = new JPAQuery(entityManager);
        QCustomer qCustomer = QCustomer.customer;

        List<Customer> customerList = query.from(qCustomer).where(qCustomer.birthday.eq(customer.getBirthday())).list(qCustomer);
        assertThat(customerList, hasItem(customer));
    }

    @Test
    public void findCustomerWithBirthdayOn_using_QueryDSL_with_SpringDataJpa() {
        final Customer customer = createAndSaveCustomer();

        QCustomer qCustomer = QCustomer.customer;

        Iterable<Customer> customers = customerRepository.findAll(qCustomer.birthday.eq(customer.getBirthday()));
        assertThat(customers, hasItem(customer));
    }

    private Customer createAndSaveCustomer() {
        LocalDate birthday = new LocalDate(1976, 2, 25);
        Customer customer = new Customer(birthday, "Fred", "Feuerstein");
        customerRepository.save(customer);
        return customer;
    }

}
