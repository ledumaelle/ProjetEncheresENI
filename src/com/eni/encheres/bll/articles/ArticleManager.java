package com.eni.encheres.bll.articles;

import com.eni.encheres.bll.exceptions.ArticleBLLException;
import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.DAOFactory;
import com.eni.encheres.dal.articles.ArticleDAO;
import com.eni.encheres.dal.exceptions.ArticleDAOException;

import java.io.*;
import java.time.LocalDate;
import java.util.Base64;
import java.util.regex.Pattern;

public class ArticleManager {
    private final ArticleDAO articleDAO;
    private static ArticleManager INSTANCE;

    /**
     * Le constructeur permet d'initialiser la variable membre enchereDAO pour
     * permettre une communication avec la base de données.
     */
    private ArticleManager() {
        this.articleDAO = DAOFactory.getArticleDAO();
    }

    //synchronized permet d'éviter dans un en environnement multithreadé que deux threads puissent exécuter le test simultanément et créer ainsi chacun une instance du singleton.
    public static synchronized ArticleManager getInstance()
    {
        if(INSTANCE ==null)
        {
            INSTANCE = new ArticleManager();
        }
        return INSTANCE;
    }

    public void insert (String libelle, String description, String imageName, String imageEncode, int categorieId, int prix, String debut, String fin, String rue, String codePostal, String ville, Utilisateur user) throws ArticleBLLException, ArticleDAOException {
        LocalDate dateDebut = transformStringToLocalDate(debut);
            LocalDate dateFin = transformStringToLocalDate(fin);
            setImageOnServer(imageName, imageEncode);
            if(!checkDate(dateDebut, dateFin) && !checkAdresse(rue, codePostal, ville) && !checkArticle(libelle, description, prix)){
                throw new ArticleBLLException("Les données saisies sont incorrectes");
            }
            //penser à passer le nom de l'image
            articleDAO.insert(new ArticleVendu(libelle, description, dateDebut, dateFin, prix, new Categorie(categorieId), user));

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

    private void setImageOnServer(String imageName, String imageEncode) throws ArticleBLLException {
        try {
            String[] image = imageEncode.split(",");

            File file = new File(imageName);
            if(file.createNewFile()){
                //convert base64 string to binary data
                byte[] imageBite = Base64.getDecoder().decode(image[1]);
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
                outputStream.write(imageBite);
            }else{
                System.out.println(imageName + " exite déjà");
            }

        } catch (IOException e) {
            throw new ArticleBLLException(e.getMessage());
        }
    }

    private LocalDate transformStringToLocalDate(String date) throws ArticleBLLException {
        try{
            return LocalDate.parse(date);
        }catch (Exception e){
            throw new ArticleBLLException(e.getMessage());
        }
    }
}
