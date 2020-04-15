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

    private static final String SELECT_ALL_BY_ARTICLEID="SELECT MAX(montant_enchere) AS 'Montant_enchere', date_enchere, U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur "+
    "FROM ENCHERES as E "+
    "INNER JOIN UTILISATEURS as U on U.no_utilisateur = E.no_utilisateur "+
    "WHERE E.no_article = ? " +
    "GROUP BY date_enchere, U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur";

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
