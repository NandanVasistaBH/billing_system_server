package com.telstra.billing_system.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.telstra.billing_system.model.User;
@Qualifier("UserRepository")
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByName(String username);
}
