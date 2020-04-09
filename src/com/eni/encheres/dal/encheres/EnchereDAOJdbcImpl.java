package com.eni.encheres.dal.encheres;

import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.bo.Enchere;
import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.ConnectionProvider;
import com.eni.encheres.dal.exceptions.EnchereDAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnchereDAOJdbcImpl implements EnchereDAO {

    private static final String SELECT_ALL="SELECT date_enchere, montant_enchere," +
            "U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur," +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres," +
            "C.no_categorie, C.libelle " +
            "FROM ENCHERES as E " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = E.no_utilisateur " +
            "INNER JOIN ARTICLES_VENDUS as A on A.no_article = E.no_article " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie ";

    private static Utilisateur itemBuilderUtilisateur(ResultSet res) throws SQLException
    {
        return new Utilisateur(res.getInt("no_utilisateur"),res.getString("pseudo"),res.getString("nom"),res.getString("prenom"),res.getString("email"),res.getString("telephone"),res.getString("mot_de_passe"),res.getString("rue"),res.getString("code_postal"),res.getString("ville"),res.getInt("credit"),res.getBoolean("administrateur"));
    }

    private static Categorie itemBuilderCategorie(ResultSet res) throws SQLException
    {
        return new Categorie(res.getInt("no_categorie"),res.getString("libelle"));
    }

    private static ArticleVendu itemBuilderArticleVendu(ResultSet res) throws SQLException
    {
        return new ArticleVendu(res.getInt("no_article"),res.getString("nom_article"),res.getString("description"),res.getDate("date_debut_encheres").toLocalDate(),res.getDate("date_fin_encheres").toLocalDate(),res.getInt("prix_vente"),res.getInt("prix_vente"));
    }

    private static Enchere itemBuilder(ResultSet res) throws SQLException
    {
        return new Enchere(res.getDate("date_enchere").toLocalDate(),res.getInt("montant_enchere"));
    }

    @Override
    public List<Enchere> getLesEncheres()
    {
        List<Enchere> lesEncheres = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ALL);
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                Categorie uneCategorie = itemBuilderCategorie(res);
                Utilisateur unUtilisateur = itemBuilderUtilisateur(res);
                ArticleVendu unArticleVendu = itemBuilderArticleVendu(res);
                Enchere uneEnchere = itemBuilder(res);
                uneEnchere.setUnArticleVendu(unArticleVendu);
                uneEnchere.setUnUtilisateur(unUtilisateur);

                if(!lesEncheres.contains(uneEnchere))
                {
                    lesEncheres.add(uneEnchere);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lesEncheres;
    }

    @Override
    public Enchere insert(Enchere enchere) throws EnchereDAOException {
        return null;
    }
}
