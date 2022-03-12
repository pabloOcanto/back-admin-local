package com.isur.backend.app.exception;

public class InactiveUserException extends RuntimeException{
    public InactiveUserException() {
        super("Usuario Inactivo");
    }
}