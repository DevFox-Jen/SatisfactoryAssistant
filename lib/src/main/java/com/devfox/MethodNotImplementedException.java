package com.devfox;

public class MethodNotImplementedException extends RuntimeException{
    public MethodNotImplementedException(){
        super("Method not yet implemented");
    }
}
