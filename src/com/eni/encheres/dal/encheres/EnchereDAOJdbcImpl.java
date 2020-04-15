package com.eni.encheres.dal.encheres;

import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.bo.Enchere;
import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnchereDAOJdbcImpl implements EnchereDAO {

    private static final String SELECT_MAX_ENCHERE_BY_ARTICLEID="SELECT montant_enchere, date_enchere, U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur \n" +
            "FROM ENCHERES as E " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = E.no_utilisateur " +
            "INNER JOIN ARTICLES_VENDUS as A on A.no_article = E.no_article " +
            "WHERE E.no_article = ? " +
            "AND E.montant_enchere = A.prix_vente";

    private static final String SELECT_ALL_BY_ARTICLEID="SELECT montant_enchere, date_enchere, U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur \n" +
            "FROM ENCHERES as E " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = E.no_utilisateur " +
            "WHERE E.no_article = ? ";

    private Enchere construireEnchere(ResultSet res) throws SQLException {
        Enchere uneEnchere = itemBuilder(res);
        Utilisateur unUtilisateur = itemBuilderUtilisateur(res);
        uneEnchere.setUnUtilisateur(unUtilisateur);
        return uneEnchere;
    }

    private static Utilisateur itemBuilderUtilisateur(ResultSet res) throws SQLException
    {
        return new Utilisateur(res.getInt("no_utilisateur"),res.getString("pseudo"),res.getString("nom"),res.getString("prenom"),res.getString("email"),res.getString("telephone"),res.getString("rue"),res.getString("code_postal"),res.getString("ville"),res.getString("mot_de_passe"),res.getInt("credit"),res.getBoolean("administrateur"));
    }
    private static Enchere itemBuilder(ResultSet res) throws SQLException
    {
        return new Enchere(res.getDate("date_enchere").toLocalDate(),res.getInt("montant_enchere"));
    }

    @Override
    public Enchere getMaxEnchereByArticleID(int noArticle)
    {
        Enchere uneEnchere = null;

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_MAX_ENCHERE_BY_ARTICLEID);
            requete.setInt(1,noArticle);
            ResultSet res = requete.executeQuery();
            if(res.next())
            {
                uneEnchere = construireEnchere(res);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return uneEnchere;
    }

    @Override
    public List<Enchere> getLesEncheresByArticleID(int noArticle)
    {
        List<Enchere> lesEncheres = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ALL_BY_ARTICLEID);
            requete.setInt(1,noArticle);
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                Enchere uneEnchere = construireEnchere(res);

                if(!lesEncheres.contains(uneEnchere)) {
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
}
