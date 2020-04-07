package test.eni.encheres.dao;

import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dao.UtilisateurDao;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurDaoTest {

    UtilisateurDao utilisateurDao= new UtilisateurDao();

    @org.junit.jupiter.api.Test
    void creerUtilisateur() {

        Utilisateur user = new Utilisateur(0,"testuser","test","user","user@gmail.com", "06","rue test","35000","test","testmdp",0, false);

        int id=utilisateurDao.creerUtilisateur(user);

        assertNotEquals(0, id,"erreur creation user echouer : "+id);




    }


}