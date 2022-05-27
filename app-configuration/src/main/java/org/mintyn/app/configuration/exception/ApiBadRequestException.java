package org.mintyn.app.configuration.exception;

public class ApiBadRequestException extends RuntimeException{

//    @Override
//    public synchronized Throwable fillInStackTrace() {
//        return this;
//    }
    public ApiBadRequestException(String message) {
        super(message);
    }

}
