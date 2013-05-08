package com.opitzconsulting.springdata.jpa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.LocalDate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "CREATED_AT")
    private LocalDate createdAt;

    @Column(name = "SALES_AMOUNT")
    private Double salesAmount;

    @Column(name = "BIRTHDAY")
    private LocalDate birthday;

    @Column(name = "FIRST_NAME")
    private String firstname;

    @Column(name = "LAST_NAME")
    private String lastname;

    @Column(name = "EMAIL", unique = true)
    private EmailAddress emailAddress;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "CUSTOMER_ID")
    private Set<Address> addresses = new HashSet<>();

    // auditable field
    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "CREATED_BY_USER")
    private User createdBy;

    // auditable field
    @CreatedDate
    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    // auditable field
    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "LAST_MODIFIED_BY_USER")
    private User lastModifiedBy;

    // auditable field
    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    protected Customer() {
        // for hibernate
    }

    public Customer(LocalDate birthday, String firstname, String lastname) {
        this(new Double(0), birthday, firstname, lastname);
    }

    public Customer(Double salesAmount, LocalDate birthday, String firstname, String lastname) {
        Assert.hasText(firstname);
        Assert.hasText(lastname);
        Assert.notNull(birthday);
        Assert.notNull(salesAmount);
        this.salesAmount = salesAmount;
        this.birthday = birthday;
        this.firstname = firstname;
        this.lastname = lastname;
        this.createdAt = new LocalDate();
    }

    public Customer(Double salesAmount, String firstname, String lastname, String email) {
        this(salesAmount, new LocalDate(), firstname, lastname);
        this.createdAt = new LocalDate();
        this.emailAddress = new EmailAddress(email);
    }

    public Customer(Double salesAmount, LocalDate birthday, String firstname, String lastname, LocalDate createdAt) {
        this(salesAmount, birthday, firstname, lastname);
        this.createdAt = createdAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public Double getSalesAmount() {
        return salesAmount;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.append("firstname", firstname);
        builder.append("lastname", lastname);
        builder.append("emailAddress", emailAddress);
        builder.append("birthday", birthday);
        builder.append("createdAt", createdAt);
        builder.append("createdByUsername", createdBy);
        builder.append("createdDate", createdDate);
        builder.append("lastModifiedBy", lastModifiedBy);
        builder.append("lastModifiedDate", lastModifiedDate);
        return builder.toString();
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
