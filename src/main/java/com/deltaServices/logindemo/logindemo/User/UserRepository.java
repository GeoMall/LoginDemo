package com.deltaServices.logindemo.logindemo.User;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    Optional<User> findByEmail(String email);
}
