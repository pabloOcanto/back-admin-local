package com.isur.backend.app.service;

import com.github.dozermapper.core.Mapper;
import com.isur.backend.app.dto.CityDTO;
import com.isur.backend.app.exception.CreatedCityException;
import com.isur.backend.app.model.City;
import com.isur.backend.app.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CityService {

    @Autowired
    @Qualifier("cityRepo")
    CityRepository repository;

    @Autowired
    private Mapper mapper;

    public CompletableFuture<City> createCity(CityDTO cityDTO) {
        CompletableFuture<City> completableFuture = CompletableFuture.supplyAsync(
                ()->{
                    Optional<City> cityOpt = repository.findByCity(cityDTO.getCity());
                    if (cityOpt.isPresent()) throw new CreatedCityException();
                    City city = new City();
                    mapper.map(cityDTO,city);
                    return repository.save(city);
                }
        );
        return completableFuture;
    }

    public CompletableFuture<List<City>> getAllCity() {
        CompletableFuture<List<City>> completableFuture = CompletableFuture.supplyAsync(
                ()->{
                    return repository.findAll()
                            .stream()
                            .sorted(Comparator.comparing(City::getCity))
                            .collect(Collectors.toList());
                });
        return completableFuture;
    }
}
