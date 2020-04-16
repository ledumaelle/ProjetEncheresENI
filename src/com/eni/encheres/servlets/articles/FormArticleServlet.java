package com.eni.encheres.servlets.articles;

import com.eni.encheres.bll.articles.ArticleManager;
import com.eni.encheres.bll.categories.CategorieManager;
import com.eni.encheres.bll.exceptions.ArticleBLLException;
import com.eni.encheres.bll.retraits.RetraitManager;
import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Retrait;
import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.exceptions.ArticleDAOException;
import com.eni.encheres.dal.exceptions.RetraitDAOException;
import jdk.jshell.execution.Util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name= "AddArticleServlet", urlPatterns = "/articles/form")
public class FormArticleServlet extends HttpServlet {
    ArticleManager articleManager;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            int noArticle = request.getParameter("no_article") == null ? 0 : Integer.parseInt(request.getParameter("no_article"));
            ArticleVendu unArticle = new ArticleVendu(0);
            if(0 != noArticle){
                articleManager = ArticleManager.getInstance();
                unArticle = articleManager.getUnArticleByID(noArticle);
                RetraitManager retraitManager = RetraitManager.getInstance();
                Retrait unRetrait = retraitManager.getRetraitByArticleId(noArticle);
                request.setAttribute("unRetrait", unRetrait);
            }
            CategorieManager categorieManager = CategorieManager.getInstance();
            request.setAttribute("categories", categorieManager.getLesCategories());
            request.setAttribute("unArticle", unArticle);
            RequestDispatcher rq = request.getRequestDispatcher("/WEB-INF/article/form_article.jsp");
            rq.forward(request, response);
        }catch (RetraitDAOException e){
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getContextPath() + "/";
        int articleId = Integer.parseInt(request.getParameter("no_article"));
        try {
            String libelle = request.getParameter("article");
            String description = request.getParameter("description");
            String photoName = request.getParameter("photo");
            String photoPath = getImagePath(photoName);
            String photoB64 = request.getParameter("photoB64");
            int categorieId = Integer.parseInt(request.getParameter("categorie"));
            int prix = Integer.parseInt(request.getParameter("prix"));
            String dateDebut = request.getParameter("date_debut_format");
            String dateFin = request.getParameter("date_fin_format");
            String rue = request.getParameter("rue");
            String codePostal = request.getParameter("cp");
            String ville = request.getParameter("ville");
            Utilisateur unUtilisateur = (Utilisateur)request.getSession().getAttribute("unUtilisateur");

            articleManager = ArticleManager.getInstance();

            if(0 == articleId){
                this.insertArticle(libelle, description, photoPath, photoName, photoB64, categorieId, prix, dateDebut, dateFin, rue, codePostal, ville, unUtilisateur);
            }else{
                this.updateArticle(articleId, libelle, description, categorieId, prix, dateDebut, dateFin, rue, codePostal, ville);
            }

        }catch (ArticleBLLException | ArticleDAOException e){
            path += "articles/form?no_article=" + articleId;
            System.err.println(e.getMessage());
        }
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", path);
    }

    private void insertArticle(
            String libelle, String description, String path, String photoName, String photoB64, int categorieId, int prix,
            String dateDebut, String dateFin, String rue, String codePostal, String ville, Utilisateur unUtilisateur) throws ArticleBLLException, ArticleDAOException {
        articleManager.insert(
                libelle, description, path, photoName, photoB64, categorieId, prix,
                dateDebut, dateFin, rue, codePostal, ville, unUtilisateur
        );
    }

    private void updateArticle(
            int noArticle, String libelle, String description, int categorieId, int prix, String dateDebut, String dateFin,
            String rue, String codePostal, String ville) throws ArticleBLLException, ArticleDAOException {
        articleManager.update(noArticle, libelle, description, categorieId, prix, dateDebut, dateFin, rue, codePostal, ville);
    }

    private String getImagePath(String photoName){
        String photoPath =  getServletConfig().getServletContext().getRealPath("");
        photoPath+="img/articles/";
        photoPath+=photoName;

        return photoPath.replace("\\","/");
    }
}
