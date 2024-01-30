package com.example.bank.exceptions;

public class InsufficientFoundsException extends RuntimeException{
    public InsufficientFoundsException(String message){
        super(message);
    }
}
