package com.eni.encheres.servlets;



import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.UtilisateurDao;
import com.eni.encheres.dal.jdbc.UtilisateurDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Test")
public class ServletTest extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        UtilisateurDao utilisateurDao = new UtilisateurDaoImpl();


        Utilisateur user = new Utilisateur(0,"testuser","test","user","user@gmail.com", "06","rue test","35000","test","testmdp",0, false);

        int id=utilisateurDao.creerUtilisateur(user);

        System.out.println(id);



    }


}
