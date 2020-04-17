package com.eni.encheres.servlets.categories;



import com.eni.encheres.bll.categories.CategorieManager;

import com.eni.encheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dellCat/*")
public class DeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        String[] pathParts = pathInfo.split("/");


        if(pathParts.length==2 && pathParts[1].trim().matches("\\d+") && request.getSession().getAttribute("unUtilisateur")!=null &&  ((Utilisateur)request.getSession().getAttribute("unUtilisateur")).isAdministrateur()){


            int id = Integer.parseInt(pathParts[1].trim());

            CategorieManager categorieManager = CategorieManager.getInstance();
            if(categorieManager.delete(id)) {

                response.sendRedirect(request.getContextPath() + "/admin");
            }else {
                this.getServletContext().getRequestDispatcher("/WEB-INF/categories/erreurDelete.jsp").forward(request,response);
            }



        }else {
            //TODO LOGGER
            response.sendRedirect(request.getContextPath());

        }



    }

}