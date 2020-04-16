package com.eni.encheres.servlets.utilisateurs;

import com.eni.encheres.bll.utilisateurs.UtilisateurManager;
import com.eni.encheres.bo.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

	public RegisterServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		String pseudo = request.getParameter("inputPseudo");
		String nom =request.getParameter("inputNom"); 
		String prenom =request.getParameter("inputPrenom");
		String rue =request.getParameter("inputAdress");
		String ville =request.getParameter("inputVille");
		String codePostal =request.getParameter("inputCodePostal");
		String tel =request.getParameter("inputPhone");

		String email = request.getParameter("inputEmail");
		String password = request.getParameter("inputPassword");
		
		
		
		UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();


		Utilisateur user = new Utilisateur(0, pseudo,  nom,  prenom,  email,  tel,  rue,  codePostal,  ville,  password,  0,  false);

		if(utilisateurManager.isUnique(user)) {

			int id = utilisateurManager.creerUtilisateur(user);
			if(id>0) {
				user.setNoUtilisateur(id);
				request.getSession().setAttribute("unUtilisateur",user);
				response.sendRedirect(request.getContextPath());

			}else {
				request.setAttribute("message","une erreur est survenue lors de la création de votre compte merci de verifier vos informations.");
				doGet(request,response);
			}





		}else {
			request.setAttribute("message","Email ou Pseudo déjà utilisé");
			doGet(request,response);
		}



		



	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().getAttribute("unUtilisateur")==null) {
			this.getServletContext().getRequestDispatcher("/WEB-INF/utilisateurs/register.jsp").forward(request, response);
		}else{
			this.getServletContext().getRequestDispatcher(request.getContextPath());
		}
	}

}
