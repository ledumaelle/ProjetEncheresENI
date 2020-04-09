package com.eni.encheres.dal.articles;

import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.dal.exceptions.ArticleDAOException;

public interface ArticleDAO {
    ArticleVendu insert(ArticleVendu article) throws ArticleDAOException;
}
