package com.opitzconsulting.springdata.jpa.specs;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.joda.time.LocalDate;
import org.springframework.data.jpa.domain.Specification;



import com.opitzconsulting.springdata.jpa.domain.Customer;
import com.opitzconsulting.springdata.jpa.domain.Customer_;

public class CustomerSpecs {

    public static Specification<Customer> isLongTermCustomer() {
        return new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                LocalDate twoYearsAgo = new LocalDate().minusYears(2);
                return builder.lessThan(root.get(Customer_.createdAt), twoYearsAgo);
            }
        };
    }

    public static Specification<Customer> hasBirthdayOn(final LocalDate birthday) {
        return new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.equal(root.get(Customer_.birthday), birthday);
            }
        };
    }

    public static Specification<Customer> hasSalesOfMoreThan(final Double salesAmount) {
        return new Specification<Customer>() {
            public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
                return builder.greaterThan(root.get(Customer_.salesAmount), salesAmount);
            }
        };
    }

}
