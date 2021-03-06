package com.eni.encheres.servlets.retraits;

import com.eni.encheres.bll.exceptions.RetraitBLLException;
import com.eni.encheres.bll.retraits.RetraitManager;
import com.eni.encheres.bll.utilisateurs.UtilisateurManager;
import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.exceptions.RetraitDAOException;
import com.eni.encheres.dal.exceptions.UtilisateurDAOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RetirerArticleServlet", urlPatterns = "/retirer")
public class RetirerArticleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            int articleId = Integer.parseInt(request.getParameter("no_article"));
            String rue = request.getParameter("rue");
            String codePostal = request.getParameter("cp");
            String ville = request.getParameter("ville");
            int credit = Integer.parseInt(request.getParameter("credit"));
            Utilisateur user = ((Utilisateur)request.getSession().getAttribute("unUtilisateur"));
            RetraitManager retraitManager = RetraitManager.getInstance();
            retraitManager.update(rue, codePostal, ville, true, new ArticleVendu(articleId));

            UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();
            utilisateurManager.updateCredit(
                    user.getNoUtilisateur(),
                    credit,
                    "plus");

            user.setCredit(credit);

            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", request.getContextPath() + "/details_enchere?no_article=" + articleId);
        }catch (RetraitBLLException | UtilisateurDAOException | RetraitDAOException e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
