package com.opitzconsulting.springdata.jpa.repository;

import java.util.List;

import com.opitzconsulting.springdata.jpa.domain.Address;

/**
 * Custom repository used within spring data JPA. The visibility is set to package visibility, because we only want
 * that the <code>AddressRepository</code> extend this.
 * <p/>
 * <p>NOTE: The interface has to be named with the postfix <code>Custom</code> to work out of the box.</p>
 */
interface AddressRepositoryCustom {

    List<Address> lookForAddressesWithCityLike(String city);
}
