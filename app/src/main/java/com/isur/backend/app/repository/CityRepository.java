package com.isur.backend.app.repository;

import com.isur.backend.app.model.City;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Qualifier("cityRepo")
@Repository
public interface CityRepository extends JpaRepository<City, String> {
    Optional<City> findByCity(String city);
}