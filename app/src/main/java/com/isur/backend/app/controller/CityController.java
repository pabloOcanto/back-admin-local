package com.isur.backend.app.controller;

import com.isur.backend.app.dto.CityDTO;
import com.isur.backend.app.model.City;
import com.isur.backend.app.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/v1/city")
public class CityController {

    @Autowired
    CityService cityService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<City> createCity(@RequestBody CityDTO cityDTO) {
        return cityService.createCity(cityDTO);
    }

    @GetMapping("/getAllCity")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<List<City>>  getAllCity(){
        return cityService.getAllCity();
    }
}
