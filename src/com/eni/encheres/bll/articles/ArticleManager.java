package com.eni.encheres.bll.articles;

import com.eni.encheres.bll.exceptions.ArticleBLLException;
import com.eni.encheres.bll.exceptions.RetraitBLLException;
import com.eni.encheres.bll.retraits.RetraitManager;
import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.bo.Retrait;
import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.DAOFactory;
import com.eni.encheres.dal.articles.ArticleDAO;
import com.eni.encheres.dal.exceptions.ArticleDAOException;
import com.eni.encheres.dal.exceptions.RetraitDAOException;
import com.eni.encheres.dal.retraits.RetraitDAO;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

public class ArticleManager {
    private final ArticleDAO articleDAO;
    private static ArticleManager INSTANCE;

    /**
     * Le constructeur permet d'initialiser la variable membre enchereDAO pour
     * permettre une communication avec la base de données.
     */
    private ArticleManager() {
        this.articleDAO = DAOFactory.getArticleDAO();
    }

    //synchronized permet d'éviter dans un en environnement multithreadé que deux threads puissent exécuter le test simultanément et créer ainsi chacun une instance du singleton.
    public static synchronized ArticleManager getInstance()
    {
        if(INSTANCE ==null)
        {
            INSTANCE = new ArticleManager();
        }
        return INSTANCE;
    }

    public void insert (String libelle, String description, String imagePath, String imageName, String imageEncode, int categorieId, int prix, String debut, String fin, String rue, String codePostal, String ville, Utilisateur user) throws ArticleBLLException, ArticleDAOException {
        LocalDate dateDebut = transformStringToLocalDate(debut);
        LocalDate dateFin = transformStringToLocalDate(fin);
        setImageOnServer(imagePath, imageEncode);
        if(!checkDate(dateDebut, dateFin) && !checkArticle(libelle, description, prix, imageName)){
            throw new ArticleBLLException("Les données saisies sont incorrectes");
        }
        ArticleVendu article = new ArticleVendu(libelle, description, dateDebut, dateFin, prix, new Categorie(categorieId), user, imageName);
        articleDAO.insert(article);
        RetraitManager retraitManager = RetraitManager.getInstance();
        try {
            retraitManager.insert(rue, codePostal, ville, article);
        }catch (RetraitBLLException | RetraitDAOException e){
            throw new ArticleBLLException(e.getMessage());
        }
    }

    public void updatePrixVente (int articleId, int montant) throws ArticleDAOException {
        articleDAO.updatePrixVente(articleId, montant);
    }

    private boolean checkArticle(String libelle, String description, int prix, String imageName) {
        return libelle.length() < 31 && description.length() < 301 && prix > 0 && imageName.length() < 51;
    }

    private boolean checkDate(LocalDate debut, LocalDate fin){
        return debut.isBefore(fin);
    }

    private void setImageOnServer(String imageName, String imageEncode) throws ArticleBLLException {
        try {
            String[] image = imageEncode.split(",");

            File file = new File(imageName);
            if(file.createNewFile()){
                //convert base64 string to binary data
                byte[] imageBite = Base64.getDecoder().decode(image[1]);
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file), 2000000);
                outputStream.write(imageBite);
            }else{
                System.out.println(imageName + " exite déjà");
            }

        } catch (Exception e) {
            throw new ArticleBLLException(e.getMessage());
        }
    }

    private LocalDate transformStringToLocalDate(String date) throws ArticleBLLException {
        try{
            return LocalDate.parse(date);
        }catch (Exception e){
            throw new ArticleBLLException(e.getMessage());
        }
    }

    public List<ArticleVendu> getLesArticles()
    {
        return this.articleDAO.getLesArticles();
    }

    public List<ArticleVendu> getLesArticlesByCategorieID(int idCategorie)
    {
        return this.articleDAO.getLesArticlesByCategorieID(idCategorie);
    }

    public List<ArticleVendu> getLesArticlesByNom(String nomArticle)
    {
        return this.articleDAO.getLesArticlesByNom(nomArticle);
    }

    public List<ArticleVendu> getLesArticlesByParams(int idCategorie, String nomArticle)
    {
        return this.articleDAO.getLesArticlesByParams(idCategorie,nomArticle);
    }

    public ArticleVendu getUnArticleByID(int noArticle)
    {
        return this.articleDAO.getUnArticleByID(noArticle);
    }


    //ENCHERES
    public List<ArticleVendu> getLesArticlesByEncheresEnCoursAndUserID(int idEnrechisseur)
    {
        return this.articleDAO.getLesArticlesByEncheresEnCoursAndUserID(idEnrechisseur);
    }

    public List<ArticleVendu> getLesArticlesByEncheresRemporteesAndUserID(int idEnrechisseur)
    {
        return this.articleDAO.getLesArticlesByEncheresRemporteesAndUserID(idEnrechisseur);
    }

    public List<ArticleVendu> getLesArticlesByEncheresEnCoursAndEncheresRemporteesAndUserID(int idEnrechisseur)
    {
        List<ArticleVendu> lesArticlesEncheresEnCours = this.articleDAO.getLesArticlesByEncheresEnCoursAndUserID(idEnrechisseur);
        List<ArticleVendu> lesArticlesEncheresRemportees = this.articleDAO.getLesArticlesByEncheresRemporteesAndUserID(idEnrechisseur);

        for (ArticleVendu unArticle : lesArticlesEncheresRemportees) {
            if (!lesArticlesEncheresEnCours.contains(unArticle)) {
                lesArticlesEncheresEnCours.add(unArticle);
            }
        }

        return lesArticlesEncheresEnCours;
    }

    public List<ArticleVendu> getLesArticlesByEncheresOuvertesAndEncheresEnCoursAndUserID(int idEnrechisseur)
    {
        List<ArticleVendu> lesArticlesEncheresOuvertes = this.articleDAO.getLesArticles();
        List<ArticleVendu> lesArticlesEncheresEnCours = this.articleDAO.getLesArticlesByEncheresEnCoursAndUserID(idEnrechisseur);

        for (ArticleVendu unArticle : lesArticlesEncheresEnCours) {
            if (!lesArticlesEncheresOuvertes.contains(unArticle)) {
                lesArticlesEncheresOuvertes.add(unArticle);
            }
        }

        return lesArticlesEncheresOuvertes;
    }

    public List<ArticleVendu> getLesArticlesByEncheresOuvertesAndEncheresRemporteesAndUserID(int idEnrechisseur)
    {
        List<ArticleVendu> lesArticlesEncheresOuvertes = this.articleDAO.getLesArticles();
        List<ArticleVendu> lesArticlesEncheresRemportees = this.articleDAO.getLesArticlesByEncheresRemporteesAndUserID(idEnrechisseur);


        for (ArticleVendu unArticle : lesArticlesEncheresRemportees) {
            if (!lesArticlesEncheresOuvertes.contains(unArticle)) {
                lesArticlesEncheresOuvertes.add(unArticle);
            }
        }

        return lesArticlesEncheresOuvertes;
    }

    public List<ArticleVendu> getLesArticlesByEncheresOuvertesAndEncheresEnCoursAndEncheresRemporteesAndUserID(int idEnrechisseur)
    {
        List<ArticleVendu> lesArticlesEncheresOuvertes = this.articleDAO.getLesArticles();
        List<ArticleVendu> lesArticlesEncheresEnCours = this.articleDAO.getLesArticlesByEncheresEnCoursAndUserID(idEnrechisseur);
        List<ArticleVendu> lesArticlesEncheresRemportees = this.articleDAO.getLesArticlesByEncheresRemporteesAndUserID(idEnrechisseur);


        for (ArticleVendu unArticle : lesArticlesEncheresRemportees) {
            if (!lesArticlesEncheresOuvertes.contains(unArticle)) {
                lesArticlesEncheresOuvertes.add(unArticle);
            }
        }

        for (ArticleVendu unArticle : lesArticlesEncheresEnCours) {
            if (!lesArticlesEncheresOuvertes.contains(unArticle)) {
                lesArticlesEncheresOuvertes.add(unArticle);
            }
        }

        return lesArticlesEncheresOuvertes;
    }

    //VENTES
    public List<ArticleVendu> getLesArticlesByVentesEnCoursAndUserID(int idVendeur)
    {
        return this.articleDAO.getLesArticlesByVentesEnCoursAndUserID(idVendeur);
    }

    public List<ArticleVendu> getLesArticlesByVentesNonDebuteesAndUserID(int idVendeur)
    {
        return this.articleDAO.getLesArticlesByVentesNonDebuteesAndUserID(idVendeur);
    }

    public List<ArticleVendu> getLesArticlesByVentesTermineesAndUserID(int idVendeur)
    {
        return this.articleDAO.getLesArticlesByVentesTermineesAndUserID(idVendeur);
    }

    public  List<ArticleVendu> getLesArticlesByVentesEnCoursAndVentesNonDebuteesAndVentesTermineesAndUserID(int idVendeur)
    {
        List<ArticleVendu> lesArticlesVentesEncours = this.articleDAO.getLesArticlesByVentesEnCoursAndUserID(idVendeur);
        List<ArticleVendu> lesArticlesVentesNonDebutees = this.articleDAO.getLesArticlesByVentesNonDebuteesAndUserID(idVendeur);
        List<ArticleVendu> lesArticlesVentesTerminees =this.articleDAO.getLesArticlesByVentesTermineesAndUserID(idVendeur);


        for (ArticleVendu unArticle : lesArticlesVentesNonDebutees) {
            if (!lesArticlesVentesEncours.contains(unArticle)) {
                lesArticlesVentesEncours.add(unArticle);
            }
        }

        for (ArticleVendu unArticle : lesArticlesVentesTerminees) {
            if (!lesArticlesVentesEncours.contains(unArticle)) {
                lesArticlesVentesEncours.add(unArticle);
            }
        }

        return lesArticlesVentesEncours;
    }

    public  List<ArticleVendu> getLesArticlesByVentesEnCoursAndVentesTermineesAndUserID(int idVendeur)
    {
        List<ArticleVendu> lesArticlesVentesEncours = this.articleDAO.getLesArticlesByVentesEnCoursAndUserID(idVendeur);
        List<ArticleVendu> lesArticlesVentesTerminees =this.articleDAO.getLesArticlesByVentesTermineesAndUserID(idVendeur);


        for (ArticleVendu unArticle : lesArticlesVentesTerminees) {
            if (!lesArticlesVentesEncours.contains(unArticle)) {
                lesArticlesVentesEncours.add(unArticle);
            }
        }

        return lesArticlesVentesEncours;
    }

    public  List<ArticleVendu> getLesArticlesByVentesEnCoursAndVentesNonDebuteesAndUserID(int idVendeur)
    {
        List<ArticleVendu> lesArticlesVentesEncours = this.articleDAO.getLesArticlesByVentesEnCoursAndUserID(idVendeur);
        List<ArticleVendu> lesArticlesVentesNonDebutees = this.articleDAO.getLesArticlesByVentesNonDebuteesAndUserID(idVendeur);

        for (ArticleVendu unArticle : lesArticlesVentesNonDebutees) {
            if (!lesArticlesVentesEncours.contains(unArticle)) {
                lesArticlesVentesEncours.add(unArticle);
            }
        }
        return lesArticlesVentesEncours;
    }

    public  List<ArticleVendu> getLesArticlesByVentesNonDebuteesAndVentesTermineesUserID(int idVendeur)
    {
        List<ArticleVendu> lesArticlesVentesNonDebutees = this.articleDAO.getLesArticlesByVentesNonDebuteesAndUserID(idVendeur);
        List<ArticleVendu> lesArticlesVentesTerminees =this.articleDAO.getLesArticlesByVentesTermineesAndUserID(idVendeur);


        for (ArticleVendu unArticle : lesArticlesVentesNonDebutees) {
            if (!lesArticlesVentesTerminees.contains(unArticle)) {
                lesArticlesVentesTerminees.add(unArticle);
            }
        }

        return lesArticlesVentesTerminees;

    }



}
