package com.eni.encheres.servlets.encheres;

import com.eni.encheres.bll.articles.ArticleManager;
import com.eni.encheres.bll.categories.CategorieManager;
import com.eni.encheres.bll.encheres.EnchereManager;
import com.eni.encheres.bll.retraits.RetraitManager;
import com.eni.encheres.bll.utilisateurs.UtilisateurManager;
import com.eni.encheres.bo.*;
import com.eni.encheres.dal.retraits.RetraitDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Class ServletDetailsEnchere
 */
@WebServlet(name="ServletDetailsEnchere",urlPatterns ={"/details_enchere"})
public class ServletDetailsEnchere extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            EnchereManager enchereManager = EnchereManager.getInstance();
            Utilisateur utilisateur = (Utilisateur)request.getSession().getAttribute("unUtilisateur");
            int newEnchere = Integer.parseInt(request.getParameter("newEnchere"));
            int lastEnchere = Integer.parseInt(request.getParameter("derniereOffre"));
            int lastUser = Integer.parseInt(request.getParameter("dernierUser"));
            int articleId = Integer.parseInt(request.getParameter("idArticle"));
            enchereManager.makeEnchere(
                    utilisateur,
                    articleId,
                    newEnchere, lastEnchere);

            UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
            if(0 != lastUser){
                utilisateurManager.updateCredit(lastUser, lastEnchere, "plus");
            }
            utilisateurManager.updateCredit(utilisateur.getNoUtilisateur(), newEnchere, "moins");

            ArticleManager articleManager = ArticleManager.getInstance();
            articleManager.updatePrixVente(articleId, newEnchere);

            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", request.getContextPath() + "/details_enchere?no_article=" + articleId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int noArticle = Integer.parseInt(request.getParameter("no_article"));
       request.setAttribute("unArticle",getUnArticleByID(noArticle));

       request.setAttribute("lastEnchere",bestEnchereByArticleID(noArticle));

       request.setAttribute("unRetrait", getRetraitArticle(noArticle));

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

    private Retrait getRetraitArticle(int articleId)
    {
        RetraitManager retraitManager = RetraitManager.getInstance();
        Retrait unRetrait = new Retrait();
        try {
            unRetrait = retraitManager.getRetraitByArticleId(articleId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return unRetrait;
    }

}
