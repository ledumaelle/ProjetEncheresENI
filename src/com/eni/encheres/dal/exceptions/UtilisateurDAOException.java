package com.eni.encheres.dal.exceptions;

public class UtilisateurDAOException extends DAOException {
    public UtilisateurDAOException() {
    }

    public UtilisateurDAOException(String message) {
        super("UTILISATEUR ----- " + message);
    }

    public UtilisateurDAOException(String message, Throwable exception) {
        super("UTILISATEUR ----- " + message, exception);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
