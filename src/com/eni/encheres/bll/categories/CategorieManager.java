package com.eni.encheres.bll.categories;

import com.eni.encheres.bll.articles.ArticleManager;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.dal.categories.CategorieDAO;
import com.eni.encheres.dal.DAOFactory;
import com.eni.encheres.dal.utilisateurs.UtilisateurDao;

import java.util.List;
import java.util.logging.Logger;

public class CategorieManager {

    private CategorieDAO categorieDAO;
    private static CategorieManager INSTANCE;

    private static final Logger LOGGER = Logger.getLogger(CategorieManager.class.toString());

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


    public Categorie getCategorieById(int id){


        return categorieDAO.getCategorieById(id);

    }

    public void update(Categorie categorie) {
        if(categorieDAO.getCategorieById(categorie.getNoCategorie())!=null){
            categorieDAO.update(categorie);
        }else{
            LOGGER.severe("categorie inexistante en base donc mise a jour impossible");
        }


    }


    public void creer(Categorie categorie){
        if(categorieDAO.creer(categorie)==0){
            LOGGER.severe("erreur lors de la création de la catégorie");
        }
    }

    public boolean delete(int id) {

        ArticleManager articleManager = ArticleManager.getInstance();
        if (articleManager.getLesArticlesByCategorieID(id).size()==0){
            categorieDAO.delete(id);
            return true;

        }

        return false;



    }
}
