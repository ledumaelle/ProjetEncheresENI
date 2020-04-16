package com.eni.encheres.servlets.categories;


import com.eni.encheres.bll.categories.CategorieManager;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class EditServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        String idStr=request.getParameter("selectCat").trim();
        if (idStr.matches("\\d+") && request.getSession().getAttribute("unUtilisateur")!=null &&  ((Utilisateur)request.getSession().getAttribute("unUtilisateur")).isAdministrateur()){
            int id = Integer.parseInt(idStr);



            CategorieManager categorieManager = CategorieManager.getInstance();
            Categorie categorie = categorieManager.getCategorieById(id);

            if (categorie!=null) {

                String newName = request.getParameter("nom");

                categorie.setLibelle(newName);
                categorieManager.update(categorie);

            }




        }else {
            //TODO LOGGER

            response.sendRedirect(request.getContextPath());

        }


        doGet(request,response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(request.getSession().getAttribute("unUtilisateur")!=null && ((Utilisateur)request.getSession().getAttribute("unUtilisateur")).isAdministrateur()) {

            CategorieManager categorieManager = CategorieManager.getInstance();

            List<Categorie> liCat = categorieManager.getLesCategories();

            request.setAttribute("liCat", liCat);

            this.getServletContext().getRequestDispatcher("/WEB-INF/categories/categories.jsp").forward(request, response);
        }else {
            response.sendRedirect(request.getContextPath());
        }

    }

}
