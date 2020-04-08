package com.eni.encheres.dal;

import com.eni.encheres.dal.encheres.EnchereDAO;
import com.eni.encheres.dal.encheres.EnchereDAOJdbcImpl;

public abstract class DAOFactory {

    public static EnchereDAO getEnchereDAO()
    {
        return new EnchereDAOJdbcImpl();
    }
}
