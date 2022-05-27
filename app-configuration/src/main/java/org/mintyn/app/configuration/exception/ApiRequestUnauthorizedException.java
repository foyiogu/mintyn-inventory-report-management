package org.mintyn.app.configuration.exception;

public class ApiRequestUnauthorizedException extends RuntimeException{

//    @Override
//    public synchronized Throwable fillInStackTrace() {
//        return this;
//    }

    public ApiRequestUnauthorizedException(String message) {
        super(message);
    }

}
