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


@WebServlet("/addCat")
public class AddServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




        if (request.getSession().getAttribute("unUtilisateur")!=null &&  ((Utilisateur)request.getSession().getAttribute("unUtilisateur")).isAdministrateur()){




            CategorieManager categorieManager = CategorieManager.getInstance();




                String nom = request.getParameter("nom");
                Categorie categorie = new Categorie(0,nom);

                categorieManager.creer(categorie);

                response.sendRedirect(request.getContextPath()+"/admin");



        }else {
            //TODO LOGGER

            response.sendRedirect(request.getContextPath());

        }



    }

}