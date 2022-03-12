package com.isur.backend.app.exception;

public class CreatedCityException extends RuntimeException{
    public CreatedCityException() {
        super("Ciudad ya creada");
    }
}