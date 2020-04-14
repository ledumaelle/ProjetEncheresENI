package com.eni.encheres.servlets.utilistateurs;

import com.eni.encheres.bll.utilisateurs.UtilisateurManager;
import com.eni.encheres.bo.Utilisateur;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	public LoginServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		UtilisateurManager utilisateurManager = UtilisateurManager.getInstance();


		Utilisateur user = utilisateurManager.connexionUtilisateur(email, password);



		if (user != null) {

			HttpSession session = request.getSession();
			session.setAttribute("unUtilisateur", user);
			response.sendRedirect(request.getContextPath());
		} else {
			String message = "Mauvais email / Mot de passe";
			request.setAttribute("message", message);
			doGet(request,response);
		}



	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request,response);
	}

}
