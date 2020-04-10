package com.eni.encheres.servlets.articles;

import com.eni.encheres.bll.articles.ArticleManager;
import com.eni.encheres.bll.categories.CategorieManager;
import com.eni.encheres.bll.exceptions.ArticleBLLException;
import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.exceptions.ArticleDAOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name= "AddArticleServlet", urlPatterns = "/articles/add")
public class AddArticleServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        CategorieManager categorieManager = CategorieManager.getInstance();
        request.setAttribute("categories", categorieManager.getLesCategories());
        RequestDispatcher rq = request.getRequestDispatcher("/WEB-INF/article/add_article.jsp");
        rq.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getContextPath() + "/";
        try {
            String photoPath =  getServletConfig().getServletContext().getRealPath("");
            photoPath+="/img/articles/";
            if(request.getParameter("photo")!=null){
                photoPath+=request.getParameter("photo");
            }
            photoPath=photoPath.replace("\\","/");

            ArticleManager articleManager = ArticleManager.getInstance();
            articleManager.insert(
                    request.getParameter("article"),
                    request.getParameter("description"),
                    photoPath,
                    request.getParameter("photoB64"),
                    Integer.parseInt(request.getParameter("categorie")),
                    Integer.parseInt(request.getParameter("prix")),
                    request.getParameter("date_debut_format"),
                    request.getParameter("date_fin_format"),
                    request.getParameter("rue"),
                    request.getParameter("cp"),
                    request.getParameter("ville"),
                    (Utilisateur)request.getSession().getAttribute("unUtilisateur")
            );


        }catch (ArticleBLLException | ArticleDAOException e){
            path += "articles/add";
            System.err.println(e.getMessage());
        }
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", path);
    }
}
