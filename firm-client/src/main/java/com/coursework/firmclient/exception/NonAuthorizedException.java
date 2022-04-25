package com.coursework.firmclient.exception;

public class NonAuthorizedException extends RuntimeException {

    public NonAuthorizedException(String message) {
        super(message);
    }
}
