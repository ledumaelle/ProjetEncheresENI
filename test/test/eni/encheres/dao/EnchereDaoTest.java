package test.eni.encheres.dao;

import com.eni.encheres.bll.encheres.EnchereManager;
import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.bo.Enchere;
import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.DAOFactory;
import com.eni.encheres.dal.articles.ArticleDAO;
import com.eni.encheres.dal.encheres.EnchereDAO;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.time.LocalDate;

public class EnchereDaoTest {
    Enchere enchere;
    EnchereDAO enchereDAO;

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

            enchere = new Enchere(new Utilisateur(22), new ArticleVendu(1005), LocalDate.now(), 400);
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
    public void makeEnchere() {
        try {
            enchereDAO = DAOFactory.getEnchereDAO();
            enchereDAO.makeEnchere(enchere);
            EnchereManager enchereManager = EnchereManager.getInstance();
            Enchere meilleureEnchere = enchereManager.getBestEnchereByArticleID(enchere.getUnArticleVendu().getNoArticle());
            meilleureEnchere.setUnArticleVendu(enchere.getUnArticleVendu());
            assert(enchere.equals(meilleureEnchere));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
