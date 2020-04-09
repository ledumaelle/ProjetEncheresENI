package com.eni.encheres.bll.encheres;

import com.eni.encheres.bll.exceptions.EncheresBLLException;
import com.eni.encheres.bo.Enchere;
import com.eni.encheres.dal.DAOFactory;
import com.eni.encheres.dal.encheres.EnchereDAO;

import java.io.File;
import java.net.URLDecoder;
import java.time.LocalDate;
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

    public void insert (String libelle, String description, String imageLocalPath, int categorieId, int prix, String debut, String fin, String rue, String codePostal, String ville) throws EncheresBLLException {
//        try {
        LocalDate dateDebut = transformStringToLocalDate(debut);
        LocalDate dateFin = transformStringToLocalDate(fin);
        setImageOnServer(imageLocalPath, libelle);
        if(!checkDate(dateDebut, dateFin) && !checkAdresse(rue, codePostal, ville) && !checkArticle(libelle, description, prix)){
            throw new EncheresBLLException("Les données saisies sont incorrectes");
        }
//        } catch (EnchereDAOException e) {
//            e.printStackTrace();
//        }
    }

    private boolean checkAdresse(String rue, String codePostal, String ville){
        String patternCodePostal = "^(([0-8][0-9])|(9[0-5])|(2[ab]))[0-9]{3}$";
        return rue.length() < 31 && codePostal.length() == 5 && Pattern.matches(patternCodePostal, codePostal) && ville.length() < 30;
    }

    private boolean checkArticle(String libelle, String description, int prix) {
        return libelle.length() < 31 && description.length() < 301 && prix > 0;
    }

    private boolean checkDate(LocalDate debut, LocalDate fin){
        return debut.isBefore(fin);
    }

    private void setImageOnServer(String imagePath, String libelle) throws EncheresBLLException {
        try{
            File file = new File("/img/encheres/" + libelle + ".jpg", URLDecoder.decode(imagePath, "UTF-8"));
        }catch (Exception e){
            throw new EncheresBLLException(e.getMessage());
        }
    }

    private LocalDate transformStringToLocalDate(String date) throws EncheresBLLException {
        try{
            return LocalDate.parse(date);
        }catch (Exception e){
            throw new EncheresBLLException(e.getMessage());
        }
    }

    public List<Enchere> getLesEncheres()
    {
        return this.enchereDAO.getLesEncheres();
    }

}
