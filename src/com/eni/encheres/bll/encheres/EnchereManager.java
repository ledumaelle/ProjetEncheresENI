package com.eni.encheres.bll.encheres;

import com.eni.encheres.bll.exceptions.EncheresBLLException;
import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.bo.Enchere;
import com.eni.encheres.dal.DAOFactory;
import com.eni.encheres.dal.encheres.EnchereDAO;
import com.eni.encheres.dal.exceptions.EnchereDAOException;

import javax.imageio.ImageIO;
import java.io.*;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.regex.Pattern;

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

    public List<Enchere> getLesEncheres()
    {
        return this.enchereDAO.getLesEncheres();
    }
}
