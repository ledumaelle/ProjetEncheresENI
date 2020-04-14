package com.eni.encheres.servlets;

import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dao.UtilisateurDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	public LoginServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UtilisateurDao userDao = new UtilisateurDao();
		
		try {
			Utilisateur user = userDao.checkLogin(email, password);
			String destPage = "login.jsp";
			
			if (user != null) {
				HttpSession session = request.getSession();
				session.setAttribute("utilisateur", user);
				destPage = "index.jsp";
			} else {
				String message = "Mauvais email / Mot de passe";
				request.setAttribute("message", message);
			}
			
			RequestDispatcher dispatcher = request.getRequestDispatcher(destPage);
			dispatcher.forward(request, response);
			
		} catch (SQLException | ClassNotFoundException ex) {
			throw new ServletException(ex);
		}
	}

}
