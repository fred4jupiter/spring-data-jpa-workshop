package com.opitzconsulting.springdata.jpa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;


@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USERNAME", unique = true)
    private String username;

    protected User() {
        // for hibernate
    }

    public User(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        final ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.append("id", id);
        builder.append("username", username);
        return builder.toString();
    }
}
