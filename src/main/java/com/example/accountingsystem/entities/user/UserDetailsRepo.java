package com.example.accountingsystem.entities.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailsRepo extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
