package com.eni.encheres.bll.encheres;

import com.eni.encheres.bo.Enchere;
import com.eni.encheres.dal.DAOFactory;
import com.eni.encheres.dal.EnchereDAO;

import java.util.List;

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

    public List<Enchere> getLesEncheres()
    {
        return this.enchereDAO.getLesEncheres();
    }

}
