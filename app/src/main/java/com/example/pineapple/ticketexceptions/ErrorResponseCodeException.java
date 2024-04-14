package com.example.pineapple.ticketexceptions;

public class ErrorResponseCodeException extends Exception{
    public ErrorResponseCodeException(String message){
        super(message);
    }
}
