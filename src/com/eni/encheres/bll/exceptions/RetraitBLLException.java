package com.eni.encheres.bll.exceptions;

public class RetraitBLLException extends BLLException {
    public RetraitBLLException() {
    }

    public RetraitBLLException(String message) {
        super("RETRAIT ----- " + message);
    }

    public RetraitBLLException(String message, Throwable exception) {
        super("RETRAIT ----- " + message, exception);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
