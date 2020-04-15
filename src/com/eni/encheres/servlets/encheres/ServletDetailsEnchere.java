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
@WebServlet(name="ServletDetailsEnchere",urlPatterns ={"/details_enchere"})
public class ServletDetailsEnchere extends HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {

        int noArticle = Integer.parseInt(request.getParameter("no_article"));
       request.setAttribute("unArticle",getUnArticleByID(noArticle));

        request.setAttribute("lesEncheres",getLesEncheresByArticleID(noArticle));
       request.setAttribute("lastEnchere",maxEnchereByArticleID(noArticle));

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/enchere/details_enchere.jsp");
        rd.forward(request, response);
    }

    private Enchere maxEnchereByArticleID(int noArticle)
    {
       Enchere bestEnchere = null;
        try {
           EnchereManager managerEnchere = EnchereManager.getInstance();
            bestEnchere = managerEnchere.getMaxEnchereByArticleID(noArticle);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bestEnchere;

    }

    private List<Enchere> getLesEncheresByArticleID(int noArticle)
    {
        List<Enchere> lesEncheres = new ArrayList<>();
        try {
            EnchereManager enchereManager = EnchereManager.getInstance();

            lesEncheres = enchereManager.getLesEncheresByArticleID(noArticle);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lesEncheres;
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
