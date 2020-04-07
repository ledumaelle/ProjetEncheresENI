package com.eni.encheres.dal;

import com.eni.encheres.dal.jdbc.EnchereDAOJdbcImpl;

public abstract class DAOFactory {

    public static EnchereDAO getEnchereDAO()
    {
        return new EnchereDAOJdbcImpl();
    }
}
