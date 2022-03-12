package com.isur.backend.app.exception;

public class UserNotExistsException extends RuntimeException{
    public UserNotExistsException() {
        super("Usuario no existe");
    }
}
