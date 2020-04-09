package com.eni.encheres.dal.exceptions;

public class ArticleDAOException extends DAOException {
    public ArticleDAOException() {
    }

    public ArticleDAOException(String message) {
        super("ARTICLE ----- " + message);
    }

    public ArticleDAOException(String message, Throwable exception) {
        super("ARTICLE ----- " + message, exception);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
