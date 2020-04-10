package com.eni.encheres.servlets;

import com.eni.encheres.bll.CategorieManager;
import com.eni.encheres.bll.EnchereManager;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.bo.Enchere;
import com.microsoft.sqlserver.jdbc.SQLServerDriver;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@WebServlet("/index.jsp")
public class ServletPageAccueil extends HttpServlet {

    private List<Enchere> lesEncheres;
    private List<Categorie> lesCategories;

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        try {
            CategorieManager managerCategorie = CategorieManager.getInstance();
            lesCategories = new ArrayList<>();
            lesCategories = managerCategorie.getLesCategories();

            request.setAttribute("lesCategories", lesCategories);
            System.out.println("ça marche ! ");
            System.out.println(lesCategories);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/index.jsp");
        rd.forward(request,response);
    }


       /*try
        {
            EnchereManager managerEnchere = EnchereManager.getInstance();
            lesEncheres = new ArrayList<>();
            lesEncheres = managerEnchere.getLesListes();

            System.out.println(lesEncheres);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/index.jsp");
        rd.forward(request,response);


    }*/
}
