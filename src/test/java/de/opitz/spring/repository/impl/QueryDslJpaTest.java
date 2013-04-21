package de.opitz.spring.repository.impl;

import org.joda.time.LocalDate;
import org.junit.Ignore;
import org.junit.Test;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPAQuery;

import de.opitzconsulting.spring.domain.Customer;
import de.opitzconsulting.spring.domain.QCustomer;
import static org.junit.Assert.assertEquals;

public class QueryDslJpaTest extends AbstractTestingBase {

    @Test
    public void findCustomerWithBirthdayOn_JPA() {
        final String name = "Michael";
        final LocalDate birthday = new LocalDate(1976, 2, 25);
        Customer customer = createAndSaveCustomerWith(name, birthday);

        JPQLQuery query = new JPAQuery(entityManager);
        QCustomer qCustomer = QCustomer.customer;

        Customer michael = query.from(qCustomer).where(qCustomer.birthday.eq(birthday)).uniqueResult(qCustomer);
        assertEquals(customer, michael);
    }

    @Ignore("fix me")
    @Test
    public void findCustomerWithBirthdayOn_SpringDataJpa() {
        final String name = "Michael";
        final LocalDate birthday = new LocalDate(1976, 2, 25);
        Customer customer = createAndSaveCustomerWith(name, birthday);

        QCustomer qCustomer = QCustomer.customer;

        Customer michael = customerRepository.findOne(qCustomer.birthday.eq(birthday));
        assertEquals(customer, michael);
    }

    private Customer createAndSaveCustomerWith(final String name, final LocalDate birthday) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setBirthday(birthday);
        customerRepository.save(customer);
        return customer;
    }

}
