package com.eni.encheres.bll.encheres;

import com.eni.encheres.bll.exceptions.EncheresBLLException;
import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Enchere;
import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.DAOFactory;
import com.eni.encheres.dal.encheres.EnchereDAO;
import com.eni.encheres.dal.exceptions.EnchereDAOException;
import com.eni.encheres.dal.exceptions.UtilisateurDAOException;

import java.time.LocalDate;
import java.util.List;

/**
 * Class EnchereManager
 */
public class EnchereManager {

    private EnchereDAO enchereDAO;
    private static EnchereManager INSTANCE;

    /**
     * Le constructeur permet d'initialiser la variable membre enchereDAO pour
     * permettre une communication avec la base de données.
     */
    private EnchereManager() {
        this.enchereDAO= DAOFactory.getEnchereDAO();
    }

    //synchronized permet d'éviter dans un en environnement multithreadé que deux threads puissent exécuter le test simultanément et créer ainsi chacun une instance du singleton.
    public static synchronized EnchereManager getInstance()
    {
        if(INSTANCE ==null)
        {
            INSTANCE = new EnchereManager();
        }
        return INSTANCE;
    }

    public List<Enchere> getLesEncheresByArticleID(int noArticle)
    {
        return this.enchereDAO.getLesEncheresByArticleID(noArticle);
    }

    public Enchere getMaxEnchereByArticleID(int noArticle)
    {
        return this.enchereDAO.getMaxEnchereByArticleID(noArticle);
    }

    public void makeEnchere(Utilisateur utilisateur, int articleId, int montantEnchere, int derniereEnchere) throws EncheresBLLException, EnchereDAOException, UtilisateurDAOException {
        if(!checkEnchere(utilisateur, articleId, montantEnchere, derniereEnchere)){
            throw new EncheresBLLException("Les données saisies sont incorrectes");
        }
        enchereDAO.makeEnchere(new Enchere(utilisateur, new ArticleVendu(articleId), LocalDate.now(), montantEnchere));
    }

    private boolean checkEnchere(Utilisateur utilisateur, int articleId, int montantEnchere, int derniereEnchere){
        return null != utilisateur && 0 != articleId && montantEnchere > 0 && montantEnchere > derniereEnchere;
    }
}
