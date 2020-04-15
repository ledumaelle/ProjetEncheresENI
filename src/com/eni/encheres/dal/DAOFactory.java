package com.eni.encheres.dal;

import com.eni.encheres.dal.articles.ArticleDAO;
import com.eni.encheres.dal.articles.ArticleDAOJdbcImpl;
import com.eni.encheres.dal.categories.CategorieDAO;
import com.eni.encheres.dal.categories.CategorieDAOJdbcImpl;
import com.eni.encheres.dal.encheres.EnchereDAO;
import com.eni.encheres.dal.encheres.EnchereDAOJdbcImpl;
import com.eni.encheres.dal.retraits.RetraitDAO;
import com.eni.encheres.dal.retraits.RetraitDAOJdbcImpl;
import com.eni.encheres.dal.utilisateurs.UtilisateurDao;
import com.eni.encheres.dal.utilisateurs.UtilisateurDaoImpl;

public abstract class DAOFactory {

    public static EnchereDAO getEnchereDAO()
    {
        return new EnchereDAOJdbcImpl();
    }

    public static CategorieDAO getCategorieDAO()
    {
        return new CategorieDAOJdbcImpl();
    }

    public static ArticleDAO getArticleDAO()
    {
        return new ArticleDAOJdbcImpl();
    }

    public static UtilisateurDao getUtilisateurDao() { return new UtilisateurDaoImpl();   }

    public static RetraitDAO getRetraitDAO() { return new RetraitDAOJdbcImpl(); }
}
