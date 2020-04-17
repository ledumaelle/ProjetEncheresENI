package com.eni.encheres.servlets.articles;

import com.eni.encheres.bll.articles.ArticleManager;
import com.eni.encheres.bll.exceptions.RetraitBLLException;
import com.eni.encheres.bll.retraits.RetraitManager;
import com.eni.encheres.dal.exceptions.ArticleDAOException;
import com.eni.encheres.dal.exceptions.RetraitDAOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteArticleServlet", urlPatterns = "/articles/cancel")
public class DeleteArticleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            RetraitManager retraitManager = RetraitManager.getInstance();
            retraitManager.delete(Integer.parseInt(request.getParameter("no_article")));

            ArticleManager articleManager = ArticleManager.getInstance();
            articleManager.delete(Integer.parseInt(request.getParameter("no_article")));

            response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            response.setHeader("Location", request.getContextPath());
        } catch (RetraitDAOException | ArticleDAOException e){
            e.printStackTrace();
        }
    }
}
