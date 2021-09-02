package com.mycompany.delivery.exception;

public class NotUniqueEntityException extends RuntimeException {
    public NotUniqueEntityException(String message) {
        super(message);
    }
}
