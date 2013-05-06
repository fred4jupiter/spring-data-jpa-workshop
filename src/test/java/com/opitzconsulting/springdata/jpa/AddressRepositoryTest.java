package com.opitzconsulting.springdata.jpa;


import com.opitzconsulting.springdata.jpa.domain.Address;
import com.opitzconsulting.springdata.jpa.repository.AddressRepository;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class AddressRepositoryTest extends AbstractTestingBase {

    @PersistenceContext
    private EntityManager entityManager;

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

    @Test
    public void callMethodFromCustomRepository() {
        final String city = "Hamburg";
        List<Address> addressList = addressRepository.lookForAddressesWithCityLike(city);
        assertThat(addressList.size(), equalTo(1));

        assertThat(addressList, hasItem(Matchers.<Address>hasProperty("city", equalTo(city))));
    }

    @Test
    public void updateAddress() {
        assertNotNull(address);
        assertNotNull(address.getId());
        assertThat(address.getStreet(), equalTo("Milchstrasse"));

        final String newStreet = "Marsweg";
        int updatedRows = addressRepository.updateStreetOfAddress(newStreet, address.getId());
        assertThat(updatedRows, equalTo(1));

        entityManager.flush();
        entityManager.clear();

        Address updatedAddress = addressRepository.findOne(address.getId());
        assertNotNull(updatedAddress);
        assertThat(updatedAddress.getStreet(), equalTo(newStreet));
    }
}
