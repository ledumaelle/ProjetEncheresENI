package com.eni.encheres.dal.encheres;

import com.eni.encheres.bo.Enchere;

import java.util.List;

public interface EnchereDAO {

    List<Enchere> getLesEncheresByArticleID(int noAtricle);
}
