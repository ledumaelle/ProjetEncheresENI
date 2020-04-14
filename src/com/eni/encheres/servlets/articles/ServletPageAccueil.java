package com.eni.encheres.servlets.articles;

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
import java.lang.*;

/**
 * Class ServletPageAccueil
 */
@WebServlet(name="ServletPageAccueil",urlPatterns ={""})
public class ServletPageAccueil extends HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {

        Utilisateur unUtilisateur = new Utilisateur(3,"Kamicasis","LE DU","Maëlle","ledu.maelle98@gmail.com","0606060606","Impasse du clos des quilles","22120","HILLION","201298",200,true);
        HttpSession session = request.getSession();
        session.setAttribute("unUtilisateur", unUtilisateur);

        request.setAttribute("lesCategories", getLesCategories());

        String idCategorie="";
        if(null != request.getParameter("idCategorie") && (!request.getParameter("idCategorie").equals("")))
        {
            idCategorie = request.getParameter("idCategorie");
        }
        String nomArticle="";
        if(null != request.getParameter("txtFiltreNom") && (!request.getParameter("txtFiltreNom").equals("")))
        {
            nomArticle = request.getParameter("txtFiltreNom");
        }

        String groupeRadio="";
        if(null != request.getParameter("groupeRadio") && (!request.getParameter("groupeRadio").equals("")))
        {
            boolean encheresOuvertes;
            boolean encheresEnCours;
            boolean encheresRemportees;
            boolean ventesEnCours;
            boolean ventesNonDebutees;
            boolean ventesTerminees;

            switch(request.getParameter("groupeRadio"))
            {
                case "achats":
                    if(null != request.getParameter("encheres_ouvertes"))
                    {
                        encheresOuvertes = request.getParameter("encheres_ouvertes").equals("on");
                    }
                    if(null != request.getParameter("encheres_en_cours"))
                    {
                        encheresEnCours = request.getParameter("encheres_ouvertes").equals("on");
                    }
                    if(null != request.getParameter("encheres_remportees"))
                    {
                        encheresRemportees = request.getParameter("encheres_ouvertes").equals("on");
                    }
                    break;
                case "ventes":
                    if(null != request.getParameter("ventes_en_cours"))
                    {
                        ventesEnCours = request.getParameter("ventes_en_cours").equals("on");
                    }
                    if(null != request.getParameter("ventes_non_debutees"))
                    {
                        ventesNonDebutees = request.getParameter("ventes_non_debutees").equals("on");
                    }
                    if(null != request.getParameter("ventes_terminees"))
                    {
                        ventesTerminees = request.getParameter("ventes_terminees").equals("on");
                    }
                    break;
                default:
                    break;
            }
        }

        List<ArticleVendu> lesArticles = getLesArticles(idCategorie,nomArticle);
        request.setAttribute("lesArticles",lesArticles);

        request.setAttribute("idCategorie",idCategorie);
        request.setAttribute("txtFiltreNom",nomArticle);
        request.setAttribute("nbSides",calculSides(lesArticles));

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/index.jsp");
        rd.forward(request, response);
    }

    private int calculSides(List<ArticleVendu> lesArticles)
    {
        float value = (float) lesArticles.size() / 6;
        int nbSides = (int) Math.ceil(value);
        return nbSides;
    }

    private List<Categorie> getLesCategories()
    {
        List<Categorie> lesCategories = new ArrayList<>();

        try {
            CategorieManager managerCategorie = CategorieManager.getInstance();
            lesCategories = managerCategorie.getLesCategories();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return lesCategories;
    }


    private List<ArticleVendu> getLesArticles(String idCategorie, String nomArticle)
    {
        List<ArticleVendu> lesArticles = new ArrayList<>();
        try {
            ArticleManager managerArticle = ArticleManager.getInstance();

            if(!idCategorie.equals("") && !nomArticle.equals(""))
            {
                lesArticles = managerArticle.getLesArticlesByParams(Integer.parseInt(idCategorie),nomArticle);
            }
            else if(!idCategorie.equals(""))
            {
                lesArticles = managerArticle.getLesArticlesByCategorieID(Integer.parseInt(idCategorie));
            }
            else if(!nomArticle.equals(""))
            {
                lesArticles = managerArticle.getLesArticlesByNom(nomArticle);
            }
            else
            {
                lesArticles = managerArticle.getLesArticles();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lesArticles;
    }

}
