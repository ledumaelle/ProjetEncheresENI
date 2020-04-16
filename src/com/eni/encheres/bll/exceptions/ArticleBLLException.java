package com.eni.encheres.bll.exceptions;

public class ArticleBLLException extends BLLException {
    public ArticleBLLException() {
    }

    public ArticleBLLException(String message) {
        super("ARTICLE ----- " + message);
    }

    public ArticleBLLException(String message, Throwable exception) {
        super("ARTICLE ----- " + message, exception);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
