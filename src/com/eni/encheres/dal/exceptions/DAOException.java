package com.eni.encheres.dal.exceptions;

public abstract class DAOException extends Exception{
    public DAOException() {
        super();
    }

    public DAOException(String message) {
        super(message);
    }

    public DAOException(String message, Throwable exception) {
        super(message, exception);
    }

    @Override
    public String getMessage() {
        StringBuffer sb = new StringBuffer("Couche DAL - ");
        sb.append(super.getMessage());

        return sb.toString() ;
    }
}
