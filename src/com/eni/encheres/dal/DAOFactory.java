package com.eni.encheres.dal;

import com.eni.encheres.dal.categories.CategorieDAO;
import com.eni.encheres.dal.categories.CategorieDAOJdbcImpl;
import com.eni.encheres.dal.encheres.EnchereDAO;
import com.eni.encheres.dal.encheres.EnchereDAOJdbcImpl;
import com.eni.encheres.dal.utilisateur.UtilisateurDao;
import com.eni.encheres.dal.utilisateur.UtilisateurDaoImpl;

public abstract class DAOFactory {

    public static EnchereDAO getEnchereDAO()
    {
        return new EnchereDAOJdbcImpl();
    }
    public static CategorieDAO getCategorieDAO()
    {
        return new CategorieDAOJdbcImpl();
    }
    public static UtilisateurDao getUtilisateurDao(){return new UtilisateurDaoImpl();}

}
