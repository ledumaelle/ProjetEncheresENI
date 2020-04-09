package com.eni.encheres.servlets.encheres;

import com.eni.encheres.bll.categories.CategorieManager;
import com.eni.encheres.bll.encheres.EnchereManager;
import com.eni.encheres.bll.exceptions.EncheresBLLException;
import com.eni.encheres.bo.Utilisateur;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name= "AddEnchereServlet", urlPatterns = "/encheres/add")
public class AddEnchereServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CategorieManager categorieManager = CategorieManager.getInstance();
        request.setAttribute("categories", categorieManager.getLesCategories());
        RequestDispatcher rq = request.getRequestDispatcher("/WEB-INF/enchere/add_enchere.jsp");
        rq.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            EnchereManager enchereManager = EnchereManager.getInstance();
            enchereManager.insert(
                    request.getParameter("article"),
                    request.getParameter("description"),
                    request.getParameter("photo"),
                    Integer.parseInt(request.getParameter("categorie")),
                    Integer.parseInt(request.getParameter("prix")),
                    request.getParameter("date_debut_format"),
                    request.getParameter("date_fin_format"),
                    request.getParameter("rue"),
                    request.getParameter("cp"),
                    request.getParameter("ville")
            );
            System.out.println("TEST");
        }catch (EncheresBLLException e){
            System.err.println(e.getMessage());
        }
    }
}
