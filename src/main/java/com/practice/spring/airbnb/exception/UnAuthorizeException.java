package com.practice.spring.airbnb.exception;


public class UnAuthorizeException extends Exception{

    public UnAuthorizeException(String message){
        super(message);
    }
}
