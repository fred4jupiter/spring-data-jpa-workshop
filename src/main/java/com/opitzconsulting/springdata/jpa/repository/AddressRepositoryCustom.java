package com.opitzconsulting.springdata.jpa.repository;

import com.opitzconsulting.springdata.jpa.domain.Address;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Custom repository used within spring data JPA.
 *
 * <p>NOTE: The interface has to be named with the postfix <code>Custom</code> to work out of the box.</p>
 */
@NoRepositoryBean
interface AddressRepositoryCustom {

    List<Address> lookForAddressesWithCityLike(String city);
}
