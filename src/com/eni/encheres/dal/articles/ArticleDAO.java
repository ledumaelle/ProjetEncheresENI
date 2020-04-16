package com.eni.encheres.dal.articles;

import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Enchere;
import com.eni.encheres.dal.exceptions.ArticleDAOException;

import java.util.List;

public interface ArticleDAO {
    void insert(ArticleVendu article) throws ArticleDAOException;

    void updatePrixVente(int id, int montant) throws ArticleDAOException;

    int countArticles() throws ArticleDAOException;

    List<ArticleVendu> getLesArticles();

     List<ArticleVendu> getLesArticlesByCategorieID(int idCategorie);

    List<ArticleVendu> getLesArticlesByNom(String nomArticle);

    List<ArticleVendu> getLesArticlesByParams(int idCategorie,String nomArticle);

    ArticleVendu getUnArticleByID(int noArticle);

    List<ArticleVendu> getLesArticlesByEncheresEnCoursAndUserID(int idEncherisseur);

    List<ArticleVendu> getLesArticlesByEncheresRemporteesAndUserID(int idEncherisseur);

    List<ArticleVendu>  getLesArticlesByVentesEnCoursAndUserID(int idVendeur);

    List<ArticleVendu> getLesArticlesByVentesNonDebuteesAndUserID(int idVendeur);

    List<ArticleVendu> getLesArticlesByVentesTermineesAndUserID(int idVendeur);
}
