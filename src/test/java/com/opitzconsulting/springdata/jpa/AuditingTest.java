package com.opitzconsulting.springdata.jpa;


import com.opitzconsulting.springdata.jpa.auditing.AuditorAwareImpl;
import com.opitzconsulting.springdata.jpa.domain.Customer;
import com.opitzconsulting.springdata.jpa.repository.CustomerRepository;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * The auditing feature is only configurable (yet) via the XML based applicationContext.
 * <p/>
 * <p>@see <a href="https://jira.springsource.org/browse/DATAJPA-265">Make it possible to configure auditing with Java Config</a></p>
 */
@ContextConfiguration(locations = {"classpath:/META-INF/spring/app-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditingTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditingTest.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void saveCustomerAndCheckIfAuditingPropertieAreSet() {
        LocalDate birthday = new LocalDate(1976, 2, 25);
        Customer customer = new Customer(birthday, "Michael", "Staehler");
        assertNotNull(customer);

        Customer savedCustomer = this.customerRepository.saveAndFlush(customer);
        LOGGER.debug("customer={}", customer);
        assertThat(savedCustomer.getId(), notNullValue());

        assertThat(savedCustomer.getCreatedAt(), notNullValue());
        assertThat(savedCustomer.getCreatedBy(), notNullValue());
        assertThat(savedCustomer.getCreatedBy().getUsername(), equalTo(AuditorAwareImpl.USERNAME_FOR_SYSTEM_USER));
    }
}
