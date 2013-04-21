package de.opitz.spring.repository.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;
import org.junit.Test;

import de.opitzconsulting.spring.domain.Customer;
import de.opitzconsulting.spring.domain.Customer_;
import static de.opitzconsulting.spring.specs.CustomerSpecs.*;

import static org.junit.Assert.*;

import static org.springframework.data.jpa.domain.Specifications.*;

public class CustomerRepositoryTests extends AbstractTestingBase {

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

        Customer foundCustomer = entityManager.createQuery(query.select(root)).getSingleResult();

        assertEquals(customer, foundCustomer);
    }

    @Test
    public void findCustomersWithBirthdayTodayAndIsLongTermCustomer_Specifications() {
        LocalDate today = new LocalDate();
        Customer customer = createLongTermCustomerWithBirthdayOn(today);

        Customer foundCustomer = customerRepository.findOne(where(hasBirthdayOn(today)).and(isLongTermCustomer()));
        assertEquals(customer, foundCustomer);
    }

    private Customer createLongTermCustomerWithBirthdayOn(LocalDate birthday) {
        Customer customer = new Customer();
        customer.setBirthday(birthday);
        customer.setCreatedAt(new LocalDate().minusYears(2).minusDays(3));
        customerRepository.save(customer);
        return customer;
    }

    @Test
    public void findCustomerWithBirthdayOnTodayAndSalesAmountGreaterThan() {
        final LocalDate birthdayOfToday = new LocalDate();
        final Double salesAmount = Double.valueOf(5100);

        Customer customer = new Customer();
        customer.setBirthday(birthdayOfToday);
        customer.setSalesAmount(salesAmount);
        customerRepository.save(customer);

        Customer foundCustomer = customerRepository.findByBirthdayAndSalesAmountGreaterThan(birthdayOfToday, salesAmount - 50);
        assertEquals(customer, foundCustomer);
    }

    @Test
    public void findAllLongTermCustomersOrSalesOfMoreThan() {
        final Double salesAmout = 50.0;
        Customer customer = new Customer();
        customer.setCreatedAt(new LocalDate().minusYears(2));
        customer.setSalesAmount(salesAmout);
        customerRepository.save(customer);

        Customer foundCustomer = customerRepository.findOne(where(isLongTermCustomer()).or(hasSalesOfMoreThan(45.4)));
        assertEquals(customer, foundCustomer);
    }
}
