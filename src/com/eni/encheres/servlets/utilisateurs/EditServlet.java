package com.eni.encheres.servlets.utilisateurs;


import com.eni.encheres.bll.utilisateurs.UtilisateurManager;
import com.eni.encheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/editUser/*")
public class EditServlet extends HttpServlet {

    public EditServlet(){super();}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String pathInfo = request.getPathInfo();

        String[] pathParts = pathInfo.split("/");


        if(pathParts.length==3 && pathParts[1].matches("\\d+") && pathParts[2].matches("\\d+") && request.getSession().getAttribute("unUtilisateur") != null) {

            String parram = pathParts[1];
            String parram2 = pathParts[2];
            try {
                int id = Integer.parseInt(parram);
                int method = Integer.parseInt(parram2);
                
                UtilisateurManager utilisateurManager= UtilisateurManager.getInstance();

                Utilisateur user = utilisateurManager.getUtilisateurById(id);

                Utilisateur userConnected = (Utilisateur) request.getSession().getAttribute("unUtilisateur");

                if(user!=null && userConnected.equals(user)) {



                    
                    switch (method){
                        
                        case 1 : majUser(user,request,response);
                        break;
                        case 2 : majPassword(user,request,response);
                        break;
                        default: doGet(request,response);
                        break;
                    }
                    
                    
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

    private void majUser(Utilisateur user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        request.setAttribute("homeActive","active show");
        request.setAttribute("mdpActive","fade");
        request.setAttribute("homeNavActive","active show");

        String pseudo = request.getParameter("pseudo");
        String nom =request.getParameter("nom");
        String prenom =request.getParameter("prenom");
        String rue =request.getParameter("rue");
        String ville =request.getParameter("ville");
        String codePostal =request.getParameter("codePostal");
        String tel =request.getParameter("telephone");
        String email = request.getParameter("email");

        user.setPseudo(pseudo);
        user.setNom(nom);
        user.setPrenom(prenom);
        user.setRue(rue);
        user.setVille(ville);
        user.setCodePostal(codePostal);
        user.setTelephone(tel);
        user.setEmail(email);

        UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();

        utilisateurManager.majUtilisateur(user);

        request.setAttribute("userEdit", user);
        request.getSession().setAttribute("unUtilisateur",user);

        request.setAttribute("message","Informations enregistrées");
        this.getServletContext().getRequestDispatcher("/WEB-INF/utilisateurs/editUser.jsp").forward(request,response);
    }

    private void majPassword(Utilisateur user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("userEdit", user);
        request.setAttribute("homeActive","fade");
        request.setAttribute("mdpActive","active show");
        request.setAttribute("mdpNavActive","active show");


        String oldMdp = request.getParameter("old");
        String newMdp = request.getParameter("mdp");

        if(oldMdp.equals(user.getMotDePasse())){

            user.setMotDePasse(newMdp);
            UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
            utilisateurManager.majUtilisateur(user);
            request.setAttribute("userEdit", user);
            request.getSession().setAttribute("unUtilisateur",user);

            request.setAttribute("messageSucces","Mot de passe modifié avec succès");
            this.getServletContext().getRequestDispatcher("/WEB-INF/utilisateurs/editUser.jsp").forward(request,response);




        }else {
            request.setAttribute("messageErreur","Ancien mot de passe invalide");
            this.getServletContext().getRequestDispatcher("/WEB-INF/utilisateurs/editUser.jsp").forward(request,response);

        }



    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        String[] pathParts = pathInfo.split("/");


        if(pathParts.length>=2 && pathParts[1].matches("\\d+") && request.getSession().getAttribute("unUtilisateur") != null) {

            String parram = pathParts[1];
            try {
                int id = Integer.parseInt(parram);

                UtilisateurManager utilisateurManager= UtilisateurManager.getInstance();

                Utilisateur user = utilisateurManager.getUtilisateurById(id);

                Utilisateur userConnected = (Utilisateur) request.getSession().getAttribute("unUtilisateur");

                if(user!=null && userConnected.equals(user)) {



                    request.setAttribute("userEdit", user);
                    request.setAttribute("homeActive","active show");
                    request.setAttribute("mdpActive","fade");

                    request.setAttribute("homeNavActive","active show");



                    this.getServletContext().getRequestDispatcher("/WEB-INF/utilisateurs/editUser.jsp").forward(request, response);
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
