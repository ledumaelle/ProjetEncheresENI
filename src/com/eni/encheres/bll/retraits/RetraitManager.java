package com.eni.encheres.bll.retraits;

import com.eni.encheres.bll.articles.ArticleManager;
import com.eni.encheres.bll.exceptions.RetraitBLLException;
import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Retrait;
import com.eni.encheres.dal.DAOFactory;
import com.eni.encheres.dal.exceptions.RetraitDAOException;
import com.eni.encheres.dal.retraits.RetraitDAO;

import java.util.regex.Pattern;

public class RetraitManager {
    private static RetraitManager INSTANCE;
    private RetraitDAO retraitDAO;

    private RetraitManager() {
        this.retraitDAO = DAOFactory.getRetraitDAO();
    }

    //synchronized permet d'éviter dans un en environnement multithreadé que deux threads puissent exécuter le test simultanément et créer ainsi chacun une instance du singleton.
    public static synchronized RetraitManager getInstance()
    {
        if(INSTANCE ==null)
        {
            INSTANCE = new RetraitManager();
        }
        return INSTANCE;
    }

    public void insert(String rue, String codePostal, String ville, ArticleVendu article) throws RetraitDAOException, RetraitBLLException {
        if(!checkRetrait(rue, codePostal, ville)){
            throw new RetraitBLLException("Les données saisies sont incorrectes");
        }
        retraitDAO.insert(new Retrait(rue, codePostal, ville, article));
    }

    public void update(String rue, String codePostal, String ville, ArticleVendu article) throws RetraitDAOException, RetraitBLLException {
        if(!checkRetrait(rue, codePostal, ville)){
            throw new RetraitBLLException("Les données saisies sont incorrectes");
        }
        retraitDAO.update(new Retrait(rue, codePostal, ville, article));
    }

    public void delete(int articleId) throws RetraitDAOException {
        retraitDAO.delete(articleId);
    }

    private boolean checkRetrait(String rue, String codePostal, String ville){
        String patternCodePostal = "^(([0-8][0-9])|(9[0-5])|(2[ab]))[0-9]{3}$";
        return rue.length() < 31 && codePostal.length() == 5 && Pattern.matches(patternCodePostal, codePostal) && ville.length() < 30;
    }

    public Retrait getRetraitByArticleId(int id) throws RetraitDAOException {
        return retraitDAO.getRetraitByArticleId(id);
    }

    public void setArticleIsRetire(int articleIsRetire) throws RetraitDAOException{
        retraitDAO.setArticleIsRetire(articleIsRetire);
    }
}
