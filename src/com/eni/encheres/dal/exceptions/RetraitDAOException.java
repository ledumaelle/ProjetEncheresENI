package com.eni.encheres.dal.exceptions;

public class RetraitDAOException extends DAOException {
    public RetraitDAOException() {
    }

    public RetraitDAOException(String message) {
        super("RETRAIT ----- " + message);
    }

    public RetraitDAOException(String message, Throwable exception) {
        super("RETRAIT ----- " + message, exception);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
