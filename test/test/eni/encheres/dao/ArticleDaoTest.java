package test.eni.encheres.dao;

import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.DAOFactory;
import com.eni.encheres.dal.articles.ArticleDAO;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.time.LocalDate;

public
class ArticleDaoTest {
    ArticleVendu article;
    ArticleDAO articleDAO;

    @Before
    public void setUp() {
        try{
            // Create initial context
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES,
                    "org.apache.naming");

            //initialise un nouveau context
            InitialContext ic = new InitialContext();
            ic.createSubcontext("java:");
            ic.createSubcontext("java:comp");
            ic.createSubcontext("java:comp/env");
            ic.createSubcontext("java:comp/env/jdbc");

            // Construct DataSource
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setURL("jdbc:sqlserver://localhost;databasename=encheres;User=userEncheres;Password=Pa$$w0rd");
            //utilise le properties user et password de l'url
            ds.setAuthentication("SqlPassword");
            //indique au server de ne pas valider le certificat SSL
            ds.setTrustServerCertificate(true);

            //on ajoute la ds au context
            ic.bind("java:comp/env/jdbc/pool_cnx",ds);

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