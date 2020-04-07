package com.eni.encheres.dal;

import com.eni.encheres.dal.articles.ArticleDAO;
import com.eni.encheres.dal.articles.ArticleDAOJdbcImpl;
import com.eni.encheres.dal.jdbc.EnchereDAOJdbcImpl;

public abstract class DAOFactory {

    public static EnchereDAO getEnchereDAO()
    {
        return new EnchereDAOJdbcImpl();
    }

    public static ArticleDAO getArticleDAO(){
        return new ArticleDAOJdbcImpl();
    }
}
