package com.opitzconsulting.springdata.jpa;


import com.opitzconsulting.springdata.jpa.domain.Address;
import com.opitzconsulting.springdata.jpa.repository.AddressRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AddressRepositoryTest extends AbstractTestingBase {

    @Autowired
    private AddressRepository addressRepository;

    private Address address;

    @Before
    public void setup() {
        Address address = new Address("Milchstrasse", "Hamburg", "Deutschland");
        Address savedAddress = addressRepository.save(address);
        assertThat(savedAddress, notNullValue());
        this.address = savedAddress;
    }

    @Test
    public void lookForAddressesWithCity() {
        List<Address> addresses = addressRepository.lookForAddressesWithCity(address.getCity());
        assertThat(addresses.size(), equalTo(1));

        Address address1 = addresses.get(0);
        assertThat(address1, equalTo(address));
    }

    @Test
    public void lookForAddressesWithCityAndCountry() {
        List<Address> addresses = addressRepository.lookForAddressesWithCityAndCountry(address.getCity(), address.getCountry());
        assertThat(addresses.size(), equalTo(1));

        Address address1 = addresses.get(0);
        assertThat(address1, equalTo(address));
    }

    @Test
    public void findAddressByCountry() {
        List<Address> addresses = addressRepository.findAddressByCountry(address.getCountry());
        assertThat(addresses.size(), equalTo(1));

        Address address1 = addresses.get(0);
        assertThat(address1, equalTo(address));
    }
}
