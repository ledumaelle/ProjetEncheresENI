package test.eni.encheres.dao;

import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.UtilisateurDao;

import com.eni.encheres.dal.jdbc.UtilisateurDaoImpl;
import com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.sql.DataSource;



import static org.junit.jupiter.api.Assertions.*;


class UtilisateurDaoTest {

    UtilisateurDao utilisateurDao= new UtilisateurDaoImpl();


    @BeforeAll
    static void setUp() {



        try {
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


        } catch (NamingException ex) {
            ex.printStackTrace();
        }

    }

    @Test
    void creerUtilisateur() throws NamingException {

        Utilisateur user = new Utilisateur(0,"testuser","test","user","user@gmail.com", "06","rue test","35000","test","testmdp",0, false);

        int id=utilisateurDao.creerUtilisateur(user);

        assertNotEquals(0, id,"erreur creation user echouer : "+id);

    }


}