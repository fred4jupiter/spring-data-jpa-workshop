package com.opitzconsulting.springdata.jpa;


import com.opitzconsulting.springdata.jpa.auditing.AuditorAwareImpl;
import com.opitzconsulting.springdata.jpa.domain.Customer;
import com.opitzconsulting.springdata.jpa.repository.CustomerRepository;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

@ContextConfiguration(locations = {"classpath:/test-app-auditing-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AuditingTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerFactory customerFactory;

    @Test
    public void saveCustomerAndCheckIfAuditingPropertieAreSet() {
        LocalDate birthday = new LocalDate(1976, 2, 25);
        Customer customer = customerFactory.createCustomer(birthday, "Michael", "Staehler");
        assertNotNull(customer);

        Customer savedCustomer = this.customerRepository.save(customer);
        assertThat(savedCustomer.getId(), notNullValue());

        assertThat(savedCustomer.getCreatedAt(), notNullValue());
        assertThat(savedCustomer.getCreatedByUsername(), equalTo(AuditorAwareImpl.USERNAME_FOR_SYSTEM_USER));
    }
}
