package com.opitzconsulting.springdata.jpa.repository;

import com.opitzconsulting.springdata.jpa.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     * Using query annotation.
     *
     * @param city
     * @return
     */
    @Query("select a from Address a where a.city = ?1")
    List<Address> lookForAddressesWithCity(String city);

    /**
     * Using a named parameter query method.
     *
     * @param city
     * @param country
     * @return
     */
    @Query("select a from Address a where a.city = :city and a.country = :country")
    List<Address> lookForAddressesWithCityAndCountry(@Param("city") String city, @Param("country") String country);

    /**
     * This query uses a <code>NamedQuery</code>.
     *
     * @param country
     * @return
     */
    List<Address> findAddressByCountry(String country);

}
