package com.eni.encheres.dal.encheres;

import com.eni.encheres.bo.Enchere;

import java.util.List;

public interface EnchereDAO {

    public List<Enchere> getLesEncheres();

    public List<Enchere> getLesEncheresByCategorieID(int idCategorie);

    public List<Enchere> getLesEncheresByNomArticle(String nomArticle);

    public List<Enchere> getLesEncheresByParams(int idCategorie,String nomArticle);

    public Enchere getUneEnchereByArticleID(int noArticle);
}
