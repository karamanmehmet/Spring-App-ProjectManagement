package com.cybertek.exception;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(final String message){
        super(message);
    }
}