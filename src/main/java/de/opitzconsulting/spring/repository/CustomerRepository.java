package de.opitzconsulting.spring.repository;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.*;

import de.opitzconsulting.spring.domain.*;

public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer>,
        QueryDslPredicateExecutor<Customer> {

    Customer findByBirthdayAndSalesAmountGreaterThan(LocalDate birthday, Double salesAmount);
}
