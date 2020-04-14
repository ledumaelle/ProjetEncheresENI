package com.eni.encheres.servlets.encheres;

import com.eni.encheres.bll.articles.ArticleManager;
import com.eni.encheres.bll.categories.CategorieManager;
import com.eni.encheres.bll.encheres.EnchereManager;
import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.bo.Enchere;
import com.eni.encheres.bo.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class ServletDetailsEnchere
 */
@WebServlet(name="ServletDetailsEnchere",urlPatterns ={"/details_enchere.html"})
public class ServletDetailsEnchere extends HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {

        Utilisateur unUtilisateur = new Utilisateur(1,"Kamicasis","LE DU","Maëlle","ledu.maelle98@gmail.com","0606060606","Impasse du clos des quilles","22120","HILLION","201298",200,true);
        HttpSession session = request.getSession();
        session.setAttribute("unUtilisateur", unUtilisateur);

        int noArticle = Integer.parseInt(request.getParameter("no_article"));
       request.setAttribute("unArticle",getUnArticleByID(noArticle));

       request.setAttribute("lastEnchere",bestEnchereByArticleID(noArticle));

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/enchere/details_enchere.jsp");
        rd.forward(request, response);
    }

    private Enchere bestEnchereByArticleID(int noArticle)
    {
       Enchere bestEnchere = null;
        try {
           EnchereManager managerEnchere = EnchereManager.getInstance();
            bestEnchere = managerEnchere.getBestEnchereByArticleID(noArticle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bestEnchere;

    }

    private ArticleVendu getUnArticleByID(int noArticle)
    {
        ArticleVendu unArticle = new ArticleVendu();
        try {
            ArticleManager articleManager = ArticleManager.getInstance();

            unArticle = articleManager.getUnArticleByID(noArticle);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return unArticle;
    }

}
