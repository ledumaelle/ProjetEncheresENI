package com.eni.encheres.servlets.utilisateurs;



import com.eni.encheres.bll.utilisateurs.UtilisateurManager;
import com.eni.encheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/utilisateur/*")
public class AfficheServlet extends HttpServlet  {

    public AfficheServlet(){super();}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        String[] pathParts = pathInfo.split("/");


        if(pathParts.length==2 && pathParts[1].matches("\\d+") && request.getSession().getAttribute("unUtilisateur") != null) {

            String parram = pathParts[1];
            try {
                int id = Integer.parseInt(parram);

                UtilisateurManager utilisateurManager= UtilisateurManager.getInstance();

                Utilisateur user = utilisateurManager.getUtilisateurById(id);

                if(user!=null) {
                    request.setAttribute("userAffiche", user);
                    request.setAttribute("hasArticleOrEnchere", utilisateurManager.hasArticleOrEnchere(user.getNoUtilisateur()));

                    this.getServletContext().getRequestDispatcher("/WEB-INF/utilisateurs/afficheUtilisateur.jsp").forward(request, response);
                }



            }catch (Exception e){
                e.printStackTrace();
            }



        }else{
            response.sendRedirect(request.getContextPath());
        }




    }




}
