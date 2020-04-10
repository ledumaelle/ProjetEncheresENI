package test.eni.encheres.dao;

import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.DAOFactory;
import com.eni.encheres.dal.articles.ArticleDAO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class ArticleDaoTest {
    ArticleVendu article;
    ArticleDAO articleDAO;

    @Before
    public void setUp() {
        try{
            article = new ArticleVendu("nomArticle", "description", LocalDate.of(2020, 4, 10),
                    LocalDate.of(2020, 4, 30), 0, new Categorie(1), new Utilisateur(22));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() {
        try{
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void createArticle() {
        try {
            articleDAO = DAOFactory.getArticleDAO();
            int nbArticlesAvantAjout = articleDAO.countArticles();
            articleDAO.insert(article);
            int nbArticlesApresAjout = articleDAO.countArticles();
            assert(nbArticlesAvantAjout + 1 == nbArticlesApresAjout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}