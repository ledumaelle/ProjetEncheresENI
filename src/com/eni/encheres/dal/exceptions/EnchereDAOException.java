package com.eni.encheres.dal.exceptions;

public class EnchereDAOException extends DAOException {
    public EnchereDAOException() {
    }

    public EnchereDAOException(String message) {
        super("ENCHERE ----- " + message);
    }

    public EnchereDAOException(String message, Throwable exception) {
        super("ENCHERE ----- " + message, exception);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
