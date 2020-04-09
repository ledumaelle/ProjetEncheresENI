package com.eni.encheres.dal.encheres;

import com.eni.encheres.bo.Enchere;
import com.eni.encheres.dal.exceptions.EnchereDAOException;

import java.util.List;

public interface EnchereDAO {

    public List<Enchere> getLesEncheres();

    Enchere insert(Enchere enchere) throws EnchereDAOException;
}
