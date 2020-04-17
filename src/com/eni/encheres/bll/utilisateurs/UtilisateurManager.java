package com.eni.encheres.bll.utilisateurs;

import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.DAOFactory;
import com.eni.encheres.dal.exceptions.UtilisateurDAOException;
import com.eni.encheres.dal.utilisateurs.UtilisateurDao;

import java.util.List;
import java.util.logging.Logger;

/**
 * Class metier utilisateur
 */
public class UtilisateurManager {


    private UtilisateurDao utilisateurDao;
    private static UtilisateurManager INSTANCE;

    private static final Logger LOGGER = Logger.getLogger(UtilisateurDao.class.toString());

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

        if(utilisateur != null && checkUtilisateur(utilisateur) && utilisateurDao.getUtilisateurByMail(utilisateur.getEmail())==null){
            return utilisateurDao.creerUtilisateur(utilisateur);
        }else {
            LOGGER.info("Utilisateur non creer car champs non valide");
        }

        return 0;
    }

    public void majUtilisateur(Utilisateur utilisateur){
        if(checkUtilisateur(utilisateur)){
            utilisateurDao.majUtilisateur(utilisateur);
        }else {
            LOGGER.info("Utilisateur non mis a jour car champs non valide");
        }


    }
    public Utilisateur getUtilisateurById(int id){

        return utilisateurDao.getUtilisateurById(id);
    }
    public Utilisateur getUtilisateurByMail(String mail){

        return utilisateurDao.getUtilisateurByMail(mail);
    }
    public List<Utilisateur> getUtilisateurs(){
        return utilisateurDao.getUtilisateurs();
    }

    public void deleteUtilisateur(int id){
        if(utilisateurDao.getUtilisateurById(id)!=null){
            utilisateurDao.deleteUtilisateur(id);

        }else {
            LOGGER.info("suppression impossible user id invalide , id : "+id);
        }

    }

    public Utilisateur connexionUtilisateur(String identifiant,String mdp){
        Utilisateur user ;

        if(identifiant.matches(".+@\\w+.\\w+")){

             user = utilisateurDao.getUtilisateurByMail(identifiant);

        }else {

            user = utilisateurDao.getUtilisateurByPseudo(identifiant);
        }

        if(user != null && user.getMotDePasse().equals(mdp)){
            return user;
        }
        return null;
    }

    private boolean checkUtilisateur(Utilisateur utilisateur){

        //Verification des contraintes de taille et not null et unicité du mail
        return utilisateur.getPseudo().length()<=30 && !utilisateur.getPseudo().isBlank() &&
                utilisateur.getNom().length()<=30 && !utilisateur.getNom().isBlank() &&
                utilisateur.getPrenom().length()<=30 && !utilisateur.getPrenom().isBlank() &&
                utilisateur.getEmail().length()<=254 && !utilisateur.getEmail().isBlank() &&
                utilisateur.getRue().length()<=30 && !utilisateur.getRue().isBlank() &&
                utilisateur.getCodePostal().length()<=10 && !utilisateur.getCodePostal().isBlank() &&
                utilisateur.getVille().length()<=30 && !utilisateur.getVille().isBlank() &&
                utilisateur.getMotDePasse().length()<=30 && !utilisateur.getMotDePasse().isBlank() &&
                utilisateur.getTelephone().length()<=15  ;
    }

    public void updateCredit(int utilisateur, int credit, String type) throws UtilisateurDAOException {
        utilisateurDao.updateCredit(utilisateur, credit, type);
    }

    public boolean hasArticleOrEnchere(int id){
        return utilisateurDao.hasArticleOrEnchere(id);
    }

    public Utilisateur getUtilisateurByPseudo(String pseudo) {
        return utilisateurDao.getUtilisateurByPseudo(pseudo);
    }


    public boolean isUnique(Utilisateur user){
       return getUtilisateurByMail(user.getEmail())==null && getUtilisateurByPseudo(user.getPseudo())==null;

    }
}
