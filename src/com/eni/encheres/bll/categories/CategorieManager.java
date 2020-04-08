package com.eni.encheres.bll.categories;

import com.eni.encheres.bo.Categorie;
import com.eni.encheres.dal.categories.CategorieDAO;
import com.eni.encheres.dal.DAOFactory;

import java.util.List;

public class CategorieManager {

    private CategorieDAO categorieDAO;
    private static CategorieManager INSTANCE;

    /**
     * Le constructeur permet d'initialiser la variable membre categorieDAO pour
     * permettre une communication avec la base de données.
     */
    private CategorieManager() {
        this.categorieDAO= DAOFactory.getCategorieDAO();
    }

    //synchronized permet d'éviter dans un en environnement multithreadé que deux threads puissent exécuter le test simultanément et créer ainsi chacun une instance du singleton.
    public static synchronized CategorieManager getInstance()
    {
        if(INSTANCE ==null)
        {
            INSTANCE = new CategorieManager();
        }
        return INSTANCE;
    }

    public List<Categorie> getLesCategories()
    {
        return this.categorieDAO.getLesCategories();
    }
}
