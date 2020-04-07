package com.eni.encheres.bll.articles;

import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.dal.articles.ArticleDAO;
import com.eni.encheres.dal.articles.ArticleDAOJdbcImpl;
import com.eni.encheres.dal.exceptions.ArticleDAOException;

public class ArticleManager {
    private ArticleDAO articleDAO;

    public ArticleManager(){
        articleDAO = new ArticleDAOJdbcImpl();
    }

    public void insert (String libelle){
        try {
            articleDAO.insert(new ArticleVendu());
        } catch (ArticleDAOException e) {
            e.printStackTrace();
        }
    }
}
