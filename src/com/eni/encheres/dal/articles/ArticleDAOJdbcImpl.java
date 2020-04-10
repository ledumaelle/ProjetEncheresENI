package com.eni.encheres.dal.articles;

import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.ConnectionProvider;
import com.eni.encheres.dal.encheres.EnchereDAO;
import com.eni.encheres.dal.exceptions.ArticleDAOException;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAOJdbcImpl implements ArticleDAO {

    private final String INSERT_NULL_EXCEPTION = "Un article ne peut pas être null";
    private final String INSERT = "INSERT INTO ARTICLES_VENDUS VALUES (?,?,?,?,?,?,?,?,?)";

    private final String COUNT_ALL = "SELECT COUNT(*) as nbArticles FROM ARTICLES_VENDUS";

    private static final String SELECT_ALL="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image," +
            "C.no_categorie, C.libelle  " +
            "FROM ARTICLES_VENDUS as A  " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie  " +
            "WHERE CURRENT_TIMESTAMP BETWEEN A.date_debut_encheres AND A.date_fin_encheres";

    private static final String SELECT_ALL_BY_CATEGORIE="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image," +
            "C.no_categorie, C.libelle  " +
            "FROM ARTICLES_VENDUS as A  " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie  " +
            "WHERE CURRENT_TIMESTAMP BETWEEN A.date_debut_encheres AND A.date_fin_encheres "+
            "AND C.no_categorie = ?";

    private static final String SELECT_ALL_BY_NOM="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image," +
            "C.no_categorie, C.libelle  " +
            "FROM ARTICLES_VENDUS as A  " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie  " +
            "WHERE CURRENT_TIMESTAMP BETWEEN A.date_debut_encheres AND A.date_fin_encheres "+
            "AND A.nom_article LIKE ?";

    private static final String SELECT_ALL_BY_PARAMS="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image," +
            "C.no_categorie, C.libelle  " +
            "FROM ARTICLES_VENDUS as A  " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie  " +
            "WHERE CURRENT_TIMESTAMP BETWEEN A.date_debut_encheres AND A.date_fin_encheres "+
            "AND C.no_categorie = ? " +
            "AND A.nom_article LIKE ?";

    private static final String SELECT_ARTICLE_BY_ID="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image," +
            "C.no_categorie, C.libelle  " +
            "FROM ARTICLES_VENDUS as A  " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie  " +
            "WHERE CURRENT_TIMESTAMP BETWEEN A.date_debut_encheres AND A.date_fin_encheres "+
            "AND A.no_article = ? ";

    @Override
    public void insert(ArticleVendu article) throws ArticleDAOException {
        if(null == article) {
            throw new ArticleDAOException(INSERT_NULL_EXCEPTION);
        }

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement st = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, article.getNomArticle());
            st.setString(2, article.getDescription());
            st.setDate(3, new Date(java.util.Date.from(article.getDateDebutEncheres().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            st.setDate(4, new Date(java.util.Date.from(article.getDateFinEncheres().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            st.setInt(5, article.getMiseAPrix());
            st.setInt(6, article.getPrixVente());
            st.setInt(7, article.getUnUtilisateur().getNoUtilisateur());
            st.setInt(8, article.getUneCategorie().getNoCategorie());
            st.setString(9, article.getNomImage());
            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            if(rs.next())
            {
                article.setNoArticle(rs.getInt(1));
            }
        }
        catch(Exception e)
        {
            throw new ArticleDAOException(e.getMessage());
        }
    }

    @Override
    public int countArticles() throws ArticleDAOException {
        try(Connection cnx = ConnectionProvider.getConnection()) {
            PreparedStatement requete = cnx.prepareStatement(COUNT_ALL);
            ResultSet rs = requete.executeQuery();

            if(rs.next()){
                return rs.getInt("nbArticles");
            }

        }catch (Exception e){
            throw new ArticleDAOException(e.getMessage());
        }
        return 0;
    }

    private static Utilisateur itemBuilderUtilisateur(ResultSet res) throws SQLException
    {
        return new Utilisateur(res.getInt("no_utilisateur"),res.getString("pseudo"),res.getString("nom"),res.getString("prenom"),res.getString("email"),res.getString("telephone"),res.getString("rue"),res.getString("code_postal"),res.getString("ville"),res.getString("mot_de_passe"),res.getInt("credit"),res.getBoolean("administrateur"));
    }

    private static Categorie itemBuilderCategorie(ResultSet res) throws SQLException
    {
        return new Categorie(res.getInt("no_categorie"),res.getString("libelle"));
    }

    private static ArticleVendu itemBuilder(ResultSet res) throws SQLException
    {
        return new ArticleVendu(res.getInt("no_article"),res.getString("nom_article"),res.getString("description"),res.getDate("date_debut_encheres").toLocalDate(),res.getDate("date_fin_encheres").toLocalDate(),res.getInt("prix_vente"),res.getInt("prix_vente"),res.getString("nom_image"));
    }

    private ArticleVendu construireArticle(ResultSet res) throws SQLException {
        Categorie uneCategorie = itemBuilderCategorie(res);
        Utilisateur unUtilisateur = itemBuilderUtilisateur(res);
        ArticleVendu unArticleVendu = itemBuilder(res);
        unArticleVendu.setUneCategorie(uneCategorie);
        unArticleVendu.setUnUtilisateur(unUtilisateur);

        return unArticleVendu;
    }

    @Override
    public List<ArticleVendu> getLesArticles()
    {
        List<ArticleVendu> lesArticles = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ALL);
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                ArticleVendu unArticleVendu = construireArticle(res);

                if(!lesArticles.contains(unArticleVendu)) {
                    lesArticles.add(unArticleVendu);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lesArticles;
    }

    @Override
    public List<ArticleVendu> getLesArticlesByCategorieID(int idCategorie) {

        List<ArticleVendu> lesArticles = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ALL_BY_CATEGORIE);
            requete.setInt(1,idCategorie);
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                ArticleVendu unArticleVendu = construireArticle(res);

                if(!lesArticles.contains(unArticleVendu)) {
                    lesArticles.add(unArticleVendu);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lesArticles;
    }

    @Override
    public List<ArticleVendu> getLesArticlesByNom(String nomArticle) {

        List<ArticleVendu> lesArticles = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ALL_BY_NOM);
            requete.setString(1,"%"+nomArticle+"%");
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                ArticleVendu unArticleVendu = construireArticle(res);

                if(!lesArticles.contains(unArticleVendu)) {
                    lesArticles.add(unArticleVendu);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lesArticles;
    }

    @Override
    public List<ArticleVendu> getLesArticlesByParams(int idCategorie, String nomArticle) {

        List<ArticleVendu> lesArticles = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ALL_BY_PARAMS);
            requete.setInt(1,idCategorie);
            requete.setString(2,"%"+nomArticle+"%");
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                ArticleVendu unArticleVendu = construireArticle(res);

                if(!lesArticles.contains(unArticleVendu)) {
                    lesArticles.add(unArticleVendu);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lesArticles;
    }

    @Override
    public ArticleVendu getUnArticleByID(int noArticle) {

        ArticleVendu unArticleVendu = null;
        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ARTICLE_BY_ID);
            requete.setInt(1,noArticle);
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                unArticleVendu = construireArticle(res);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return unArticleVendu;
    }
}

