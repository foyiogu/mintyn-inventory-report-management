package org.mintyn.app.configuration.exception;

public class ApiResourceNotFoundException extends RuntimeException{

//    @Override
//    public synchronized Throwable fillInStackTrace() {
//        return this;
//    }

    public ApiResourceNotFoundException(String message) {
        super(message);
    }

}
