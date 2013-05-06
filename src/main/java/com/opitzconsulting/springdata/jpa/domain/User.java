package com.opitzconsulting.springdata.jpa.domain;

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
}
