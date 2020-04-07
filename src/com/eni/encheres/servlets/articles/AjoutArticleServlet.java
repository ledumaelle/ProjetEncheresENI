package com.eni.encheres.servlets.articles;

import com.eni.encheres.bll.articles.ArticleManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name= "AjoutArticleServlet", urlPatterns = "/article/ajout")
public class AjoutArticleServlet extends HttpServlet {
    private ArticleManager articleManager;

    public AjoutArticleServlet() {
        super();
        articleManager = new ArticleManager();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        articleManager.insert("TEST");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
