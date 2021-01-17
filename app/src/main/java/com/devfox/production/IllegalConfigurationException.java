package com.devfox.production;

public class IllegalConfigurationException extends RuntimeException{
    public IllegalConfigurationException(String message){
        super(message);
    }
}
