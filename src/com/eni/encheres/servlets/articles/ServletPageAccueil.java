package com.eni.encheres.servlets.articles;

import com.eni.encheres.bll.articles.ArticleManager;
import com.eni.encheres.bll.categories.CategorieManager;
import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Categorie;
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


    Utilisateur unUtilisateur = null;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        if(null != session.getAttribute("unUtilisateur"))
        {
            unUtilisateur = (Utilisateur) session.getAttribute("unUtilisateur");
        }
      
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

        boolean encheresOuvertes = false;
        boolean encheresEnCours = false;
        boolean encheresRemportees = false;
        boolean ventesEnCours = false;
        boolean ventesNonDebutees = false;
        boolean ventesTerminees = false;
        if(null != request.getParameter("groupeRadio") && (!request.getParameter("groupeRadio").equals("")))
        {
            switch(request.getParameter("groupeRadio"))
            {
                case "achats":
                    if(null != request.getParameter("encheres_ouvertes"))
                    {
                        encheresOuvertes = request.getParameter("encheres_ouvertes").equals("on");
                    }
                    if(null != request.getParameter("encheres_en_cours"))
                    {
                        encheresEnCours = request.getParameter("encheres_en_cours").equals("on");
                    }
                    if(null != request.getParameter("encheres_remportees"))
                    {
                        encheresRemportees = request.getParameter("encheres_remportees").equals("on");
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

        List<ArticleVendu> lesArticles = new ArrayList<>();

        if(null != unUtilisateur)
        {
            lesArticles = getLesArticles(idCategorie,nomArticle,encheresOuvertes,encheresEnCours,encheresRemportees,ventesEnCours,ventesNonDebutees,ventesTerminees);
        }
        else
        {
            lesArticles = getLesArticles(idCategorie,nomArticle);

        }
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
        return (int) Math.ceil(value);
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

    private List<ArticleVendu> filtreNomAndCategorie(String idCategorie, String nomArticle, List<ArticleVendu> lesArticles)
    {
        List<ArticleVendu> lesArticlesFiltres = new ArrayList<>();
        try {

            //Filtre nom + catégorie
            if(!idCategorie.equals("") && !nomArticle.equals(""))
            {
                for(ArticleVendu  unArticle  : lesArticles)
                {
                    if(unArticle.getUneCategorie().getNoCategorie() == Integer.parseInt(idCategorie) && !lesArticlesFiltres.contains(unArticle) && unArticle.getNomArticle().toLowerCase().matches(".*"+nomArticle.toLowerCase()+".*"))
                    {
                        lesArticlesFiltres.add(unArticle);
                    }
                }
            }
            else if(!idCategorie.equals(""))
            {
                for(ArticleVendu  unArticle  : lesArticles)
                {
                    if(unArticle.getUneCategorie().getNoCategorie() == Integer.parseInt(idCategorie) && !lesArticlesFiltres.contains(unArticle))
                    {
                        lesArticlesFiltres.add(unArticle);
                    }
                }
            }
            else if(!nomArticle.equals(""))
            {
                for(ArticleVendu  unArticle  : lesArticles)
                {
                    if(!lesArticlesFiltres.contains(unArticle) && unArticle.getNomArticle().toLowerCase().matches(".*"+nomArticle.toLowerCase()+".*"))
                    {
                        lesArticlesFiltres.add(unArticle);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lesArticlesFiltres;
    }

    private List<ArticleVendu> getLesArticles(String idCategorie, String nomArticle, boolean encheresOuvertes, boolean encheresEnCours, boolean encheresRemportees,boolean ventesEncours, boolean ventesNonDebutees, boolean ventesTerminees)
    {
        List<ArticleVendu> lesArticles = new ArrayList<>();
        try {
            ArticleManager managerArticle = ArticleManager.getInstance();

            //ENCHERES
            if(encheresOuvertes & encheresEnCours &!encheresRemportees)
            {
                lesArticles = managerArticle.getLesArticlesByEncheresOuvertesAndEncheresEnCoursAndUserID(unUtilisateur.getNoUtilisateur());
            }
            else if(encheresOuvertes & encheresRemportees &!encheresEnCours)
            {
                lesArticles = managerArticle.getLesArticlesByEncheresOuvertesAndEncheresRemporteesAndUserID(unUtilisateur.getNoUtilisateur());
            }
            else if(encheresEnCours & encheresRemportees &!encheresOuvertes)
            {
                lesArticles = managerArticle.getLesArticlesByEncheresEnCoursAndEncheresRemporteesAndUserID(unUtilisateur.getNoUtilisateur());
            }
            else if(encheresOuvertes & encheresEnCours & encheresRemportees)
            {
                lesArticles = managerArticle.getLesArticlesByEncheresOuvertesAndEncheresEnCoursAndEncheresRemporteesAndUserID(unUtilisateur.getNoUtilisateur());
            }
            else if(encheresOuvertes)
            {
                lesArticles = managerArticle.getLesArticles();
            }
            else if(encheresEnCours)
            {
                lesArticles = managerArticle.getLesArticlesByEncheresEnCoursAndUserID(unUtilisateur.getNoUtilisateur());
            }
            else if(encheresRemportees)
            {
                lesArticles = managerArticle.getLesArticlesByEncheresRemporteesAndUserID(unUtilisateur.getNoUtilisateur());

            }

            //VENTES
            else if(ventesEncours & ventesNonDebutees & ventesTerminees)
            {
                lesArticles = managerArticle.getLesArticlesByVentesEnCoursAndVentesNonDebuteesAndVentesTermineesAndUserID(unUtilisateur.getNoUtilisateur());
            }
            else if(ventesEncours & ventesNonDebutees & !ventesTerminees)
            {
                lesArticles = managerArticle.getLesArticlesByVentesEnCoursAndVentesNonDebuteesAndUserID(unUtilisateur.getNoUtilisateur());
            }
            else if(ventesEncours &ventesTerminees & !ventesNonDebutees)
            {
                lesArticles = managerArticle.getLesArticlesByVentesEnCoursAndVentesTermineesAndUserID(unUtilisateur.getNoUtilisateur());
            }
            else if(ventesNonDebutees & ventesTerminees & !ventesEncours)
            {
                lesArticles = managerArticle.getLesArticlesByVentesNonDebuteesAndVentesTermineesUserID(unUtilisateur.getNoUtilisateur());
            }
            else if(ventesEncours)
            {
                lesArticles = managerArticle.getLesArticlesByVentesEnCoursAndUserID(unUtilisateur.getNoUtilisateur());
            }
            else if(ventesNonDebutees)
            {
                lesArticles = managerArticle.getLesArticlesByVentesNonDebuteesAndUserID(unUtilisateur.getNoUtilisateur());
            }
            else if(ventesTerminees)
            {
                lesArticles = managerArticle.getLesArticlesByVentesTermineesAndUserID(unUtilisateur.getNoUtilisateur());
            }


            //SANS FILTRE articles où les enchères sont en cours
            else
            {
                lesArticles = managerArticle.getLesArticles();
            }

            if(!nomArticle.isEmpty() || !idCategorie.isEmpty())
            {
                lesArticles = filtreNomAndCategorie(idCategorie, nomArticle, lesArticles);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lesArticles;
    }

}
