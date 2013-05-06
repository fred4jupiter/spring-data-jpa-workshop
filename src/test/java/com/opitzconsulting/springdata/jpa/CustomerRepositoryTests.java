package com.opitzconsulting.springdata.jpa;

import com.opitzconsulting.springdata.jpa.domain.Customer;
import com.opitzconsulting.springdata.jpa.domain.Customer_;
import com.opitzconsulting.springdata.jpa.repository.CustomerRepository;
import org.hamcrest.Matchers;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

import static com.opitzconsulting.springdata.jpa.specs.CustomerSpecs.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.springframework.data.jpa.domain.Specifications.where;

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

    @Test
    public void findByEmailAddressOrderByLastnameAsc() {
        final Customer customer = new Customer(6483.00, "Teddy", "Baer", "teddy@baer.com");
        populateCustomersWithOneLike(customer);

        List<Customer> list = customerRepository.findByEmailAddressOrderByLastnameAsc(customer.getEmailAddress());
        assertThat("no customer found", list.isEmpty(), equalTo(false));

        assertThat(list, Matchers.<Customer>hasItem(Matchers.<Customer>hasProperty("firstname", equalTo(customer.getFirstname()))));
        assertThat(list, Matchers.<Customer>hasItem(Matchers.<Customer>hasProperty("emailAddress", equalTo(customer.getEmailAddress()))));
    }

    @Test
    public void checkCollectionMatchers() {
        Customer customer = new Customer(500.00, "Fred", "Feuerstein", "fred@feuerstein.de");
        Customer customer2 = new Customer(546.34, "Wilma", "Feuerstein", "wilma@feuerstein.de");
        List<Customer> list = Arrays.asList(customer, customer2);

        assertThat(list, Matchers.<Customer>hasItem(Matchers.<Customer>hasProperty("firstname", equalTo("Fred"))));
    }

    private void populateCustomersWithOneLike(Customer customer) {
        customerRepository.save(customer);
        customerRepository.save(new Customer(500.00, "Fred", "Feuerstein", "fred@feuerstein.de"));
        customerRepository.save(new Customer(546.34, "Wilma", "Feuerstein", "wilma@feuerstein.de"));
        customerRepository.save(new Customer(12345.00, "Karl", "Katze", "karl@katze.de"));
        customerRepository.save(new Customer(250.00, "Bert", "Bernstein", "bert@bernstein.de"));
        customerRepository.save(new Customer(7596.00, "Elke", "Olle", "elke@olle.de"));
    }

    private Customer createLongTermCustomerWithBirthdayOn(LocalDate birthday) {
        final Double salesAmout = 100.0;
        final LocalDate createdAt = new LocalDate().minusYears(2).minusDays(3);
        Customer customer = customerFactory.createCustomer(birthday, salesAmout, createdAt);
        customerRepository.save(customer);
        return customer;
    }
}
