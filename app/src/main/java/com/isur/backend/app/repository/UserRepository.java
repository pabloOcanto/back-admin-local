package com.isur.backend.app.repository;

import com.isur.backend.app.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Qualifier("userRepo")
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByDniAndPassword(Long dni, String password);
    Optional<User> findByDni(Long dni);
}
