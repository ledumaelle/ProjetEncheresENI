package com.eni.encheres.servlets.encheres;

import com.eni.encheres.bll.categories.CategorieManager;
import com.eni.encheres.bll.encheres.EnchereManager;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.bo.Enchere;
import com.eni.encheres.bo.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Class ServletPageAccueil
 */
@WebServlet("")
public class ServletPageAccueil extends HttpServlet {

    private List<Enchere> lesEncheres;
    private List<Categorie> lesCategories;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws ServletException, IOException {

        Utilisateur unUtilisateur = new Utilisateur(22,"Kamicasis","LE DU","Maëlle","ledu.maelle98@gmail.com","0606060606","Impasse du clos des quilles","22120","HILLION","201298",200,true);
        HttpSession session = request.getSession();
        session.setAttribute("unUtilisateur", unUtilisateur);

        try {
            CategorieManager managerCategorie = CategorieManager.getInstance();
            lesCategories = managerCategorie.getLesCategories();

            request.setAttribute("lesCategories", lesCategories);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            EnchereManager managerEnchere = EnchereManager.getInstance();
            lesEncheres = managerEnchere.getLesEncheres();

            request.setAttribute("lesEncheres", lesEncheres);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/index.jsp");
        rd.forward(request, response);
    }

}
