package com.eni.encheres.dal.encheres;

import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.dal.exceptions.EnchereDAOException;

public interface EnchereDAO {
    void insert(ArticleVendu article) throws EnchereDAOException;
}
