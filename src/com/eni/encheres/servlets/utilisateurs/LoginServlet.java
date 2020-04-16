package com.eni.encheres.servlets.utilisateurs;

import com.eni.encheres.bll.utilisateurs.UtilisateurManager;
import com.eni.encheres.bo.Utilisateur;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	public static final int     COOKIE_MAX_AGE            = 60 * 60 * 24 * 365;  // 1 an

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
			if(request.getParameter("memoire")!=null){
				setCookie(response,"MDP",password,COOKIE_MAX_AGE);
				setCookie(response,"LOGIN",email,COOKIE_MAX_AGE);
			}else {
				setCookie(response,"MDP",null,COOKIE_MAX_AGE);
				setCookie(response,"LOGIN",null,COOKIE_MAX_AGE);
			}


			response.sendRedirect(request.getContextPath());
		} else {
			String message = "Mauvais email ou login / Mot de passe";
			request.setAttribute("message", message);
			doGet(request,response);
		}



	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Cookie[] cookies= request.getCookies();

		for(Cookie c : cookies){
			if((c.getName().equals("LOGIN") || c.getName().equals("MDP")) && c.getValue()!=null && !c.getValue().isBlank() ){

				request.setAttribute("checkMemoire","checked");
			}
		}



		this.getServletContext().getRequestDispatcher("/WEB-INF/utilisateurs/login.jsp").forward(request,response);
	}

	private static void setCookie( HttpServletResponse response, String nom, String valeur, int maxAge ) {
		Cookie cookie = new Cookie( nom, valeur );
		cookie.setMaxAge( maxAge );
		response.addCookie( cookie );
	}

}
