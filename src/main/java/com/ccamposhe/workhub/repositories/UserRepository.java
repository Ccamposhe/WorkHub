package com.ccamposhe.workhub.repositories;

import com.ccamposhe.workhub.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);
}
