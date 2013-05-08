package com.opitzconsulting.springdata.jpa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.jpa.domain.AbstractAuditable;
import org.springframework.util.Assert;

import javax.persistence.*;

@Entity
@Table(name = "ADDRESS")
@NamedQuery(name = "address.findAddressByCountry", query = "Select a from Address a where a.country = ?1")
public class Address extends AbstractAuditable<User, Long> {

    @Column(name = "STREET")
    private String street;

    @Column(name = "CITY")
    private String city;

    @Column(name = "COUNTRY")
    private String country;

    public Address(String street, String city, String country) {
        Assert.hasText(street, "Street must not be null or empty!");
        Assert.hasText(city, "City must not be null or empty!");
        Assert.hasText(country, "Country must not be null or empty!");

        this.street = street;
        this.city = city;
        this.country = country;
    }

    protected Address() {
        // for hibernate
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.append("street", street);
        builder.append("city", city);
        builder.append("country", country);
        return builder.toString();
    }

}
