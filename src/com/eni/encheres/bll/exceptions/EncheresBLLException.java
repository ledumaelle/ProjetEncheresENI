package com.eni.encheres.bll.exceptions;

public class EncheresBLLException extends BLLException {
    public EncheresBLLException() {
    }

    public EncheresBLLException(String message) {
        super("ENCHERE ----- " + message);
    }

    public EncheresBLLException(String message, Throwable exception) {
        super("ENCHERE ----- " + message, exception);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
