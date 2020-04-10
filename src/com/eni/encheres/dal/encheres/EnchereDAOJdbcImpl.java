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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnchereDAOJdbcImpl implements EnchereDAO {

    private static final String SELECT_ALL="SELECT date_enchere, montant_enchere," +
            "U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur," +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image," +
            "C.no_categorie, C.libelle " +
            "FROM ENCHERES as E " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = E.no_utilisateur " +
            "INNER JOIN ARTICLES_VENDUS as A on A.no_article = E.no_article " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie ";

    private static final String SELECT_ALL_BY_CATEGORIE="SELECT date_enchere, montant_enchere," +
            "U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur," +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image," +
            "C.no_categorie, C.libelle " +
            "FROM ENCHERES as E " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = E.no_utilisateur " +
            "INNER JOIN ARTICLES_VENDUS as A on A.no_article = E.no_article " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie " +
            "WHERE C.no_categorie = ?";

    private static final String SELECT_ALL_BY_NOMARTICLE="SELECT date_enchere, montant_enchere," +
            "U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur," +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image," +
            "C.no_categorie, C.libelle " +
            "FROM ENCHERES as E " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = E.no_utilisateur " +
            "INNER JOIN ARTICLES_VENDUS as A on A.no_article = E.no_article " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie " +
            "WHERE A.nom_article LIKE ?";

    private static final String SELECT_ALL_BY_PARAMS="SELECT date_enchere, montant_enchere," +
            "U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur," +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image," +
            "C.no_categorie, C.libelle " +
            "FROM ENCHERES as E " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = E.no_utilisateur " +
            "INNER JOIN ARTICLES_VENDUS as A on A.no_article = E.no_article " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie " +
            "WHERE C.no_categorie = ? " +
            "AND A.nom_article LIKE ?";

    private static final String SELECT_ENCHERE_BY_ARTICLEID="SELECT date_enchere, montant_enchere," +
            "U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur," +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image," +
            "C.no_categorie, C.libelle " +
            "FROM ENCHERES as E " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = E.no_utilisateur " +
            "INNER JOIN ARTICLES_VENDUS as A on A.no_article = E.no_article " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie " +
            "WHERE A.no_article = ?";

    private static Utilisateur itemBuilderUtilisateur(ResultSet res) throws SQLException
    {
        return new Utilisateur(res.getInt("no_utilisateur"),res.getString("pseudo"),res.getString("nom"),res.getString("prenom"),res.getString("email"),res.getString("telephone"),res.getString("rue"),res.getString("code_postal"),res.getString("ville"),res.getString("mot_de_passe"),res.getInt("credit"),res.getBoolean("administrateur"));
    }

    private static Categorie itemBuilderCategorie(ResultSet res) throws SQLException
    {
        return new Categorie(res.getInt("no_categorie"),res.getString("libelle"));
    }

    private static ArticleVendu itemBuilderArticleVendu(ResultSet res) throws SQLException
    {
        return new ArticleVendu(res.getInt("no_article"),res.getString("nom_article"),res.getString("description"),res.getDate("date_debut_encheres").toLocalDate(),res.getDate("date_fin_encheres").toLocalDate(),res.getInt("prix_vente"),res.getInt("prix_vente"),res.getString("nom_image"));
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
                unArticleVendu.setUneCategorie(uneCategorie);
                unArticleVendu.setUnUtilisateur(unUtilisateur);
                Enchere uneEnchere = itemBuilder(res);
                uneEnchere.setUnArticleVendu(unArticleVendu);

                lesEncheres.add(uneEnchere);
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

    @Override
    public List<Enchere> getLesEncheresByCategorieID(int idCategorie) {

        List<Enchere> lesEncheres = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ALL_BY_CATEGORIE);
            requete.setInt(1,idCategorie);
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                Categorie uneCategorie = itemBuilderCategorie(res);
                Utilisateur unUtilisateur = itemBuilderUtilisateur(res);
                ArticleVendu unArticleVendu = itemBuilderArticleVendu(res);
                unArticleVendu.setUneCategorie(uneCategorie);
                unArticleVendu.setUnUtilisateur(unUtilisateur);
                Enchere uneEnchere = itemBuilder(res);
                uneEnchere.setUnArticleVendu(unArticleVendu);

                lesEncheres.add(uneEnchere);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lesEncheres;
    }

    @Override
    public List<Enchere> getLesEncheresByNomArticle(String nomArticle) {

        List<Enchere> lesEncheres = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ALL_BY_NOMARTICLE);
            requete.setString(1,"%"+nomArticle+"%");
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                Categorie uneCategorie = itemBuilderCategorie(res);
                Utilisateur unUtilisateur = itemBuilderUtilisateur(res);
                ArticleVendu unArticleVendu = itemBuilderArticleVendu(res);
                unArticleVendu.setUneCategorie(uneCategorie);
                unArticleVendu.setUnUtilisateur(unUtilisateur);
                Enchere uneEnchere = itemBuilder(res);
                uneEnchere.setUnArticleVendu(unArticleVendu);

                lesEncheres.add(uneEnchere);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lesEncheres;
    }

    @Override
    public List<Enchere> getLesEncheresByParams(int idCategorie, String nomArticle) {

        List<Enchere> lesEncheres = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ALL_BY_PARAMS);
            requete.setInt(1,idCategorie);
            requete.setString(2,"%"+nomArticle+"%");
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                Categorie uneCategorie = itemBuilderCategorie(res);
                Utilisateur unUtilisateur = itemBuilderUtilisateur(res);
                ArticleVendu unArticleVendu = itemBuilderArticleVendu(res);
                unArticleVendu.setUneCategorie(uneCategorie);
                unArticleVendu.setUnUtilisateur(unUtilisateur);
                Enchere uneEnchere = itemBuilder(res);
                uneEnchere.setUnArticleVendu(unArticleVendu);

                lesEncheres.add(uneEnchere);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lesEncheres;
    }

    @Override
    public Enchere getUneEnchereByArticleID(int noArticle) {

        Enchere uneEnchere = null;
        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ENCHERE_BY_ARTICLEID);
            requete.setInt(1,noArticle);
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                Categorie uneCategorie = itemBuilderCategorie(res);
                Utilisateur unUtilisateur = itemBuilderUtilisateur(res);
                ArticleVendu unArticleVendu = itemBuilderArticleVendu(res);
                unArticleVendu.setUneCategorie(uneCategorie);
                unArticleVendu.setUnUtilisateur(unUtilisateur);
                uneEnchere = itemBuilder(res);
                uneEnchere.setUnArticleVendu(unArticleVendu);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return uneEnchere;
    }
}
