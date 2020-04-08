package com.eni.encheres.servlets.encheres;

import com.eni.encheres.bll.encheres.EnchereManager;
import com.eni.encheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name= "AddEnchereServlet", urlPatterns = "/encheres/add")
public class AddEnchereServlet extends HttpServlet {
    private EnchereManager enchereManager;

    @Override
    public void init() throws ServletException {
        super.init();
        enchereManager = new EnchereManager();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Utilisateur utilisateur = new Utilisateur(22, "AngeliqueLR", "LE ROUX", "Angelique", "angelique.lr@gmx.fr", "0685797994", "4 impasse de broceliande", "22120", "Quessoy", "Pa$$w0rd", 0, false);
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
