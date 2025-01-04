package com.practice.spring.airbnb.exception;


public class UnAuthorizeException extends RuntimeException{

    public UnAuthorizeException(String message){
        super(message);
    }
}
