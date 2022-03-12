package com.isur.backend.app.exception;

public class CreatedUserException extends RuntimeException{
    public CreatedUserException() {
        super("Usuario ya creado");
    }
}