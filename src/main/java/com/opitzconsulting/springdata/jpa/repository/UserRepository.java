package com.opitzconsulting.springdata.jpa.repository;

import com.opitzconsulting.springdata.jpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
