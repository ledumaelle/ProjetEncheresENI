package com.eni.encheres.bll;

import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.DAOFactory;
import com.eni.encheres.dal.utilisateur.UtilisateurDao;

import java.util.List;

/**
 * Class metier utilisateur
 */
public class UtilisateurManager {


    private UtilisateurDao utilisateurDao;
    private static UtilisateurManager INSTANCE;

    private UtilisateurManager(){this.utilisateurDao= DAOFactory.getUtilisateurDao();  }

    //synchronized permet d'éviter dans un en environnement multithreadé que deux threads puissent exécuter le test simultanément et créer ainsi chacun une instance du singleton.
    public static synchronized UtilisateurManager getInstance()
    {
        if(INSTANCE ==null)
        {
            INSTANCE = new UtilisateurManager();
        }
        return INSTANCE;
    }

    public int creerUtilisateur(Utilisateur utilisateur){

        return 0;
    }
    public void majUtilisateur(Utilisateur utilisateur){

    }
    public Utilisateur getUtilisateurById(int id){
        return null;
    }
    public Utilisateur getUtilisateurByMail(String mail){

        return null;
    }
    public List<Utilisateur> getUtilisateurs(){
        return utilisateurDao.getUtilisateurs();
    }
    public void deleteUtilisateur(int id){
        if(utilisateurDao.getUtilisateurById(id)!=null){
            utilisateurDao.deleteUtilisateur(id);

        }else {
            //TODO logger erreur
        }

    }

    private boolean checkUtilisateur(Utilisateur utilisateur){


        return false;
    }



}
