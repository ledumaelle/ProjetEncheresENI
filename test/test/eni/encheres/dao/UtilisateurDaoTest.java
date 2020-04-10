package test.eni.encheres.dao;

import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.utilisateur.UtilisateurDao;

import com.eni.encheres.dal.utilisateur.UtilisateurDaoImpl;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;


import static org.junit.jupiter.api.Assertions.*;


class UtilisateurDaoTest {

    private static UtilisateurDao utilisateurDao= new UtilisateurDaoImpl();

    private static Utilisateur user;


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


        user= new Utilisateur(0,"testuser","test","user","user@gmail.com", "06","rue test","35000","test","testmdp",0, false);

        int id=utilisateurDao.creerUtilisateur(user);

        if(id!=0){
            user.setNoUtilisateur(id);
        }

    }


    @AfterAll
    static void afterAll() {
        utilisateurDao.deleteUtilisateur(user.getNoUtilisateur());
    }

    @Test
    void creerUtilisateur() throws NamingException {

        Utilisateur user2 = new Utilisateur(0,"testuser2","test","user","user2@gmail.com", "06","rue test","35000","test","testmdp",0, false);

        int id=utilisateurDao.creerUtilisateur(user2);

        assertNotEquals(0, id,"erreur creation user echouer : "+id);

        utilisateurDao.deleteUtilisateur(id);

    }


    @Test
    void majUtilisateur() {

        int id=user.getNoUtilisateur();

        user.setNom("nomchanger");

        utilisateurDao.majUtilisateur(user);

        Utilisateur user2=utilisateurDao.getUtilisateurById(id);

        assertEquals(user,user2,"erreur maj utilisateur");


    }

    @Test
    void getUtilisateurById() {
        Utilisateur user2 = utilisateurDao.getUtilisateurById(user.getNoUtilisateur());

        assertEquals(user,user2,"get utilisateur by id");


    }

    @Test
    void getUtilisateurByMail() {

        Utilisateur user2 = utilisateurDao.getUtilisateurByMail(user.getEmail());

        assertEquals(user,user2,"get utilisateur by mail");
    }

    @Test
    void getUtilisateurs() {


        assertNotNull(utilisateurDao.getUtilisateurs(),"erreru recuparation de tout les utilisateur");

    }

    @Test
    void deleteUtilisateur() {

        int id=utilisateurDao.creerUtilisateur(user);

        utilisateurDao.deleteUtilisateur(id);

        assertNull(utilisateurDao.getUtilisateurById(id),"erreur suppression user ");

    }


}