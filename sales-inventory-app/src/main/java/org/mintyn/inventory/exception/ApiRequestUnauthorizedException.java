package org.mintyn.inventory.exception;

public class ApiRequestUnauthorizedException extends RuntimeException{

    public ApiRequestUnauthorizedException(String message) {
        super(message);
    }

}
