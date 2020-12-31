package com.cybertek.exception;

import org.springframework.security.core.AuthenticationException;

public class ProjectCodeAlreadyExistException extends AuthenticationException {

    public ProjectCodeAlreadyExistException(final String message) {
        super(message);
    }

}