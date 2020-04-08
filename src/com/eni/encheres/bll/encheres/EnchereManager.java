package com.eni.encheres.bll.encheres;

import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.dal.DAOFactory;
import com.eni.encheres.dal.encheres.EnchereDAO;
import com.eni.encheres.dal.encheres.EnchereDAOJdbcImpl;
import com.eni.encheres.dal.exceptions.EnchereDAOException;

public class EnchereManager {
    private EnchereDAO enchereDAO;

    public EnchereManager(){
        enchereDAO = DAOFactory.getEnchereDAO();
    }

    public void insert (String libelle){
        try {
            enchereDAO.insert(new ArticleVendu());
        } catch (EnchereDAOException e) {
            e.printStackTrace();
        }
    }
}
