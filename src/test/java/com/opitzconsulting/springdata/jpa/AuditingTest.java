package com.opitzconsulting.springdata.jpa;


import com.opitzconsulting.springdata.jpa.auditing.AuditorAwareImpl;
import com.opitzconsulting.springdata.jpa.domain.Customer;
import com.opitzconsulting.springdata.jpa.repository.CustomerRepository;
import com.opitzconsulting.springdata.jpa.util.CustomerFactory;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AuditingTest extends AbstractTestingBase {

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
