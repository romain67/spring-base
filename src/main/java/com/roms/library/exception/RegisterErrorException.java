package com.roms.library.exception;

public class RegisterErrorException extends RuntimeException {

    private static final long serialVersionUID = -6279587616019197174L;

    public RegisterErrorException(String message) {
        super(message);
    }

}
