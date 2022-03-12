package com.isur.backend.app.exception;

public class CreatedTopicException extends RuntimeException{
    public CreatedTopicException() {
        super("Topico ya creado");
    }
}