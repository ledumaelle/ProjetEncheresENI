package com.eni.encheres.servlets.utilisateurs;

import com.eni.encheres.bll.utilisateurs.UtilisateurManager;
import com.eni.encheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteUser/*")
public class DeleteServlet extends HttpServlet {



    public DeleteServlet(){super();}

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

                Utilisateur userConnected = (Utilisateur) request.getSession().getAttribute("unUtilisateur");

                if(user!=null && userConnected.equals(user)) {

                    utilisateurManager.deleteUtilisateur(id);
                    request.getSession().removeAttribute("unUtilisateur");

                    this.getServletContext().getRequestDispatcher("/WEB-INF/utilisateurs/deleteUser.jsp").forward(request, response);

                }else {
                    response.sendRedirect(request.getContextPath());
                }



            }catch (Exception e){
                e.printStackTrace();
            }



        }else{
            response.sendRedirect(request.getContextPath());
        }




    }
}


