package com.eni.encheres.dal.articles;

import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.dal.exceptions.ArticleDAOException;

public interface ArticleDAO {
    void insert(ArticleVendu article) throws ArticleDAOException;

    int countArticles() throws ArticleDAOException;
}
