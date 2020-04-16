package com.eni.encheres.dal.articles;

import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.bo.Categorie;
import com.eni.encheres.bo.Enchere;
import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.ConnectionProvider;
import com.eni.encheres.dal.exceptions.ArticleDAOException;

import javax.ejb.Local;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAOJdbcImpl implements ArticleDAO {

    private final String INSERT_NULL_EXCEPTION = "Un article ne peut pas être null";
    private final String INSERT = "INSERT INTO ARTICLES_VENDUS VALUES (?,?,?,?,?,?,?,?,?)";

    private final String UPDATE_PRIX_VENTE = "UPDATE ARTICLES_VENDUS SET prix_vente = ? WHERE no_article = ?";
    private final String UPDATE = "UPDATE ARTICLES_VENDUS SET nom_article = ?, " +
            "description = ?, " +
            "date_debut_encheres = ?, " +
            "date_fin_encheres = ?, " +
            "prix_initial = ?, " +
            "no_categorie = ? " +
            "WHERE no_article = ?";

    private final String DELETE = "DELETE FROM ARTICLES_VENDUS WHERE no_article = ?";

    private final String COUNT_ALL = "SELECT COUNT(*) as nbArticles FROM ARTICLES_VENDUS";

    private static final String SELECT_ALL="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image, " +
            "C.no_categorie, C.libelle," +
            "ISNULL(E.montant_enchere,-1) as montant_enchere, E.date_enchere," +
            "E.no_utilisateur as enrechisseur_no_utilisateur, U2.nom as enrechisseur_nom, U2.prenom as enrechisseur_prenom, U2.pseudo as enrechisseur_pseudo, U2.email as enrechisseur_email," +
            "U2.telephone as enrechisseur_telephone, U2.mot_de_passe as enrechisseur_mot_de_passe, U2.rue as enrechisseur_rue, U2.code_postal as enrechisseur_code_postal, U2.ville as enrechisseur_ville, U2.credit as enrechisseur_credit, U2.administrateur as enrechisseur_administrateur " +
            "FROM ARTICLES_VENDUS as A " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie " +
            "LEFT JOIN ENCHERES as E on E.no_article = A.no_article " +
            "LEFT JOIN UTILISATEURS as U2 on U2.no_utilisateur = E.no_utilisateur  " +
            "WHERE CURRENT_TIMESTAMP BETWEEN A.date_debut_encheres AND A.date_fin_encheres " +
            "ORDER BY A.date_fin_encheres ASC, montant_enchere DESC";

    private static final String SELECT_ALL_BY_CATEGORIE="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image, " +
            "C.no_categorie, C.libelle," +
            "ISNULL(E.montant_enchere,-1) as montant_enchere, E.date_enchere," +
            "E.no_utilisateur as enrechisseur_no_utilisateur, U2.nom as enrechisseur_nom, U2.prenom as enrechisseur_prenom, U2.pseudo as enrechisseur_pseudo, U2.email as enrechisseur_email," +
            "U2.telephone as enrechisseur_telephone, U2.mot_de_passe as enrechisseur_mot_de_passe, U2.rue as enrechisseur_rue, U2.code_postal as enrechisseur_code_postal, U2.ville as enrechisseur_ville, U2.credit as enrechisseur_credit, U2.administrateur as enrechisseur_administrateur " +
            "FROM ARTICLES_VENDUS as A " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie " +
            "LEFT JOIN ENCHERES as E on E.no_article = A.no_article " +
            "LEFT JOIN UTILISATEURS as U2 on U2.no_utilisateur = E.no_utilisateur  " +
            "WHERE CURRENT_TIMESTAMP BETWEEN A.date_debut_encheres AND A.date_fin_encheres " +
            "AND C.no_categorie = ? " +
            "ORDER BY A.date_fin_encheres ASC, montant_enchere DESC ";

    private static final String SELECT_ALL_BY_NOM="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image, " +
            "C.no_categorie, C.libelle," +
            "ISNULL(E.montant_enchere,-1) as montant_enchere, E.date_enchere," +
            "E.no_utilisateur as enrechisseur_no_utilisateur, U2.nom as enrechisseur_nom, U2.prenom as enrechisseur_prenom, U2.pseudo as enrechisseur_pseudo, U2.email as enrechisseur_email," +
            "U2.telephone as enrechisseur_telephone, U2.mot_de_passe as enrechisseur_mot_de_passe, U2.rue as enrechisseur_rue, U2.code_postal as enrechisseur_code_postal, U2.ville as enrechisseur_ville, U2.credit as enrechisseur_credit, U2.administrateur as enrechisseur_administrateur " +
            "FROM ARTICLES_VENDUS as A " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie " +
            "LEFT JOIN ENCHERES as E on E.no_article = A.no_article " +
            "LEFT JOIN UTILISATEURS as U2 on U2.no_utilisateur = E.no_utilisateur  " +
            "WHERE CURRENT_TIMESTAMP BETWEEN A.date_debut_encheres AND A.date_fin_encheres " +
            "AND A.nom_article LIKE ? " +
            "ORDER BY A.date_fin_encheres ASC, montant_enchere DESC";


    private static final String SELECT_ALL_BY_PARAMS="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image, " +
            "C.no_categorie, C.libelle," +
            "ISNULL(E.montant_enchere,-1) as montant_enchere, E.date_enchere," +
            "E.no_utilisateur as enrechisseur_no_utilisateur, U2.nom as enrechisseur_nom, U2.prenom as enrechisseur_prenom, U2.pseudo as enrechisseur_pseudo, U2.email as enrechisseur_email," +
            "U2.telephone as enrechisseur_telephone, U2.mot_de_passe as enrechisseur_mot_de_passe, U2.rue as enrechisseur_rue, U2.code_postal as enrechisseur_code_postal, U2.ville as enrechisseur_ville, U2.credit as enrechisseur_credit, U2.administrateur as enrechisseur_administrateur " +
            "FROM ARTICLES_VENDUS as A " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie " +
            "LEFT JOIN ENCHERES as E on E.no_article = A.no_article " +
            "LEFT JOIN UTILISATEURS as U2 on U2.no_utilisateur = E.no_utilisateur  " +
            "WHERE CURRENT_TIMESTAMP BETWEEN A.date_debut_encheres AND A.date_fin_encheres " +
            "AND C.no_categorie = ? " +
            "AND A.nom_article LIKE ? " +
            "ORDER BY A.date_fin_encheres ASC, montant_enchere DESC ";


    private static final String SELECT_ARTICLE_BY_ID="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image, " +
            "C.no_categorie, C.libelle," +
            "ISNULL(E.montant_enchere,-1) as montant_enchere, E.date_enchere," +
            "E.no_utilisateur as enrechisseur_no_utilisateur, U2.nom as enrechisseur_nom, U2.prenom as enrechisseur_prenom, U2.pseudo as enrechisseur_pseudo, U2.email as enrechisseur_email," +
            "U2.telephone as enrechisseur_telephone, U2.mot_de_passe as enrechisseur_mot_de_passe, U2.rue as enrechisseur_rue, U2.code_postal as enrechisseur_code_postal, U2.ville as enrechisseur_ville, U2.credit as enrechisseur_credit, U2.administrateur as enrechisseur_administrateur " +
            "FROM ARTICLES_VENDUS as A " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie " +
            "LEFT JOIN ENCHERES as E on E.no_article = A.no_article " +
            "LEFT JOIN UTILISATEURS as U2 on U2.no_utilisateur = E.no_utilisateur  " +
            "WHERE A.no_article = ? " +
            "ORDER BY A.date_fin_encheres ASC, montant_enchere DESC";

    private static final String SELECT_ARTICLES_BY_ENCHERES_EN_COURS_AND_USER="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image, " +
            "C.no_categorie, C.libelle," +
            "ISNULL(E.montant_enchere,-1) as montant_enchere, E.date_enchere," +
            "E.no_utilisateur as enrechisseur_no_utilisateur, U2.nom as enrechisseur_nom, U2.prenom as enrechisseur_prenom, U2.pseudo as enrechisseur_pseudo, U2.email as enrechisseur_email," +
            "U2.telephone as enrechisseur_telephone, U2.mot_de_passe as enrechisseur_mot_de_passe, U2.rue as enrechisseur_rue, U2.code_postal as enrechisseur_code_postal, U2.ville as enrechisseur_ville, U2.credit as enrechisseur_credit, U2.administrateur as enrechisseur_administrateur " +
            "FROM ARTICLES_VENDUS as A " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie " +
            "LEFT JOIN ENCHERES as E on E.no_article = A.no_article " +
            "LEFT JOIN UTILISATEURS as U2 on U2.no_utilisateur = E.no_utilisateur  " +
            "WHERE E.no_utilisateur = ? " +
            "AND CURRENT_TIMESTAMP BETWEEN A.date_debut_encheres AND A.date_fin_encheres " +
            "ORDER BY A.date_fin_encheres ASC, montant_enchere DESC";

    private static final String SELECT_ARTICLES_BY_ENCHERES_REMPORTEES_AND_USER="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image, " +
            "C.no_categorie, C.libelle," +
            "ISNULL(E.montant_enchere,-1) as montant_enchere, E.date_enchere," +
            "E.no_utilisateur as enrechisseur_no_utilisateur, U2.nom as enrechisseur_nom, U2.prenom as enrechisseur_prenom, U2.pseudo as enrechisseur_pseudo, U2.email as enrechisseur_email," +
            "U2.telephone as enrechisseur_telephone, U2.mot_de_passe as enrechisseur_mot_de_passe, U2.rue as enrechisseur_rue, U2.code_postal as enrechisseur_code_postal, U2.ville as enrechisseur_ville, U2.credit as enrechisseur_credit, U2.administrateur as enrechisseur_administrateur " +
            "FROM ARTICLES_VENDUS as A " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie " +
            "LEFT JOIN ENCHERES as E on E.no_article = A.no_article " +
            "LEFT JOIN UTILISATEURS as U2 on U2.no_utilisateur = E.no_utilisateur  " +
            "WHERE E.no_utilisateur = ? " +
            "AND A.prix_vente = E.montant_enchere " +
            "AND CURRENT_TIMESTAMP > A.date_fin_encheres " +
            "ORDER BY A.date_fin_encheres ASC, montant_enchere DESC";

    private static final String SELECT_ARTICLES_BY_VENTES_EN_COURS_AND_USER="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image, " +
            "C.no_categorie, C.libelle," +
            "ISNULL(E.montant_enchere,-1) as montant_enchere, E.date_enchere," +
            "E.no_utilisateur as enrechisseur_no_utilisateur, U2.nom as enrechisseur_nom, U2.prenom as enrechisseur_prenom, U2.pseudo as enrechisseur_pseudo, U2.email as enrechisseur_email," +
            "U2.telephone as enrechisseur_telephone, U2.mot_de_passe as enrechisseur_mot_de_passe, U2.rue as enrechisseur_rue, U2.code_postal as enrechisseur_code_postal, U2.ville as enrechisseur_ville, U2.credit as enrechisseur_credit, U2.administrateur as enrechisseur_administrateur " +
            "FROM ARTICLES_VENDUS as A " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie " +
            "LEFT JOIN ENCHERES as E on E.no_article = A.no_article " +
            "LEFT JOIN UTILISATEURS as U2 on U2.no_utilisateur = E.no_utilisateur  " +
            "WHERE CURRENT_TIMESTAMP BETWEEN A.date_debut_encheres AND A.date_fin_encheres " +
            "AND U.no_utilisateur = ? "+
            "ORDER BY A.date_fin_encheres ASC, montant_enchere DESC";

    private static final String SELECT_ARTICLES_BY_VENTES_NON_DEBUTEES_AND_USER="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image, " +
            "C.no_categorie, C.libelle," +
            "ISNULL(E.montant_enchere,-1) as montant_enchere, E.date_enchere," +
            "E.no_utilisateur as enrechisseur_no_utilisateur, U2.nom as enrechisseur_nom, U2.prenom as enrechisseur_prenom, U2.pseudo as enrechisseur_pseudo, U2.email as enrechisseur_email," +
            "U2.telephone as enrechisseur_telephone, U2.mot_de_passe as enrechisseur_mot_de_passe, U2.rue as enrechisseur_rue, U2.code_postal as enrechisseur_code_postal, U2.ville as enrechisseur_ville, U2.credit as enrechisseur_credit, U2.administrateur as enrechisseur_administrateur " +
            "FROM ARTICLES_VENDUS as A " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie " +
            "LEFT JOIN ENCHERES as E on E.no_article = A.no_article " +
            "LEFT JOIN UTILISATEURS as U2 on U2.no_utilisateur = E.no_utilisateur  " +
            "WHERE CURRENT_TIMESTAMP < A.date_debut_encheres " +
            "AND U.no_utilisateur = ? "+
            "ORDER BY A.date_fin_encheres ASC, montant_enchere DESC";

    private static final String SELECT_ARTICLES_BY_VENTES_TERMINEES_AND_USER="SELECT U.no_utilisateur, U.nom, U.prenom, U.pseudo, U.email,  U.telephone, U.mot_de_passe, U.rue, U.code_postal, U.ville, U.credit, U.administrateur, " +
            "A.no_article, A.nom_article, A.description, A.prix_initial, A.prix_vente, A.date_fin_encheres, A.date_debut_encheres, A.nom_image, " +
            "C.no_categorie, C.libelle," +
            "ISNULL(E.montant_enchere,-1) as montant_enchere, E.date_enchere," +
            "E.no_utilisateur as enrechisseur_no_utilisateur, U2.nom as enrechisseur_nom, U2.prenom as enrechisseur_prenom, U2.pseudo as enrechisseur_pseudo, U2.email as enrechisseur_email," +
            "U2.telephone as enrechisseur_telephone, U2.mot_de_passe as enrechisseur_mot_de_passe, U2.rue as enrechisseur_rue, U2.code_postal as enrechisseur_code_postal, U2.ville as enrechisseur_ville, U2.credit as enrechisseur_credit, U2.administrateur as enrechisseur_administrateur " +
            "FROM ARTICLES_VENDUS as A " +
            "INNER JOIN UTILISATEURS as U on U.no_utilisateur = A.no_utilisateur " +
            "INNER JOIN CATEGORIES as C on C.no_categorie = A.no_categorie " +
            "LEFT JOIN ENCHERES as E on E.no_article = A.no_article " +
            "LEFT JOIN UTILISATEURS as U2 on U2.no_utilisateur = E.no_utilisateur  " +
            "WHERE CURRENT_TIMESTAMP > A.date_fin_encheres " +
            "AND U.no_utilisateur = ? "+
            "ORDER BY A.date_fin_encheres ASC, montant_enchere DESC";

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
    public void update(ArticleVendu article) throws ArticleDAOException {
        if(null == article) {
            throw new ArticleDAOException(INSERT_NULL_EXCEPTION);
        }

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement st = cnx.prepareStatement(UPDATE);
            st.setString(1, article.getNomArticle());
            st.setString(2, article.getDescription());
            st.setDate(3, new Date(java.util.Date.from(article.getDateDebutEncheres().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            st.setDate(4, new Date(java.util.Date.from(article.getDateFinEncheres().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            st.setInt(5, article.getMiseAPrix());
            st.setInt(6, article.getUneCategorie().getNoCategorie());
            st.setInt(7, article.getNoArticle());
            st.executeUpdate();
        }
        catch(Exception e)
        {
            throw new ArticleDAOException(e.getMessage());
        }
    }

    @Override
    public void delete(int articleId) throws ArticleDAOException {
        if(0 == articleId) {
            throw new ArticleDAOException(INSERT_NULL_EXCEPTION);
        }

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement st = cnx.prepareStatement(DELETE);
            st.setInt(1, articleId);
            st.executeUpdate();
        }
        catch(Exception e)
        {
            throw new ArticleDAOException(e.getMessage());
        }
    }

    @Override
    public void updatePrixVente(int id, int montant) throws ArticleDAOException {
        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement st = cnx.prepareStatement(UPDATE_PRIX_VENTE);
            st.setInt(1, montant);
            st.setInt(2, id);
            st.executeUpdate();
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

    private static Utilisateur itemBuilderUtilisateurEnrechisseur(ResultSet res) throws SQLException
    {
        return new Utilisateur(res.getInt("enrechisseur_no_utilisateur"),res.getString("enrechisseur_pseudo"),res.getString("enrechisseur_nom"),res.getString("enrechisseur_prenom"),res.getString("enrechisseur_email"),res.getString("enrechisseur_telephone"),res.getString("enrechisseur_rue"),res.getString("enrechisseur_code_postal"),res.getString("enrechisseur_ville"),res.getString("enrechisseur_mot_de_passe"),res.getInt("enrechisseur_credit"),res.getBoolean("enrechisseur_administrateur"));
    }

    private static Enchere itemBuilderEnchere(ResultSet res) throws SQLException
    {
        return new Enchere(res.getDate("date_enchere").toLocalDate(),res.getInt("montant_enchere"));
    }

    private static Categorie itemBuilderCategorie(ResultSet res) throws SQLException
    {
        return new Categorie(res.getInt("no_categorie"),res.getString("libelle"));
    }

    private static ArticleVendu itemBuilder(ResultSet res) throws SQLException
    {
        return new ArticleVendu(res.getInt("no_article"),res.getString("nom_article"),res.getString("description"),res.getDate("date_debut_encheres").toLocalDate(),res.getDate("date_fin_encheres").toLocalDate(),res.getInt("prix_initial"),res.getInt("prix_vente"),res.getString("nom_image"));
    }

    private ArticleVendu construireArticle(ResultSet res) throws SQLException {
        Categorie uneCategorie = itemBuilderCategorie(res);
        Utilisateur unUtilisateur = itemBuilderUtilisateur(res);
        ArticleVendu unArticleVendu = itemBuilder(res);
        unArticleVendu.setUneCategorie(uneCategorie);
        unArticleVendu.setUnUtilisateur(unUtilisateur);

        return unArticleVendu;
    }

    private Enchere construireEnchere(ResultSet res) throws SQLException {

        Enchere uneEnchere = new Enchere();
        if(res.getInt("montant_enchere") != -1)
        {
            uneEnchere = itemBuilderEnchere(res);
            uneEnchere.setUnArticleVendu(itemBuilder(res));
            uneEnchere.setUnUtilisateur(itemBuilderUtilisateurEnrechisseur(res));
        }

        return uneEnchere;
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
                unArticleVendu.setEtatVente(getStatut(unArticleVendu));

                Enchere uneEnchere = construireEnchere(res);
                if(uneEnchere.getMontantEnchere() != -1)
                {
                    unArticleVendu.ajouterUneEnchere(uneEnchere);
                }

                if(!lesArticles.contains(unArticleVendu)) {
                    lesArticles.add(unArticleVendu);
                }
                else
                {
                    if(uneEnchere.getMontantEnchere() != -1 && !lesArticles.get(lesArticles.indexOf(unArticleVendu)).getLesEncheres().contains(uneEnchere))
                    {
                        lesArticles.get(lesArticles.indexOf(unArticleVendu)).ajouterUneEnchere(uneEnchere);
                    }
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
                unArticleVendu.setEtatVente(getStatut(unArticleVendu));


                Enchere uneEnchere = construireEnchere(res);
                if(uneEnchere.getMontantEnchere() != -1)
                {
                    unArticleVendu.ajouterUneEnchere(uneEnchere);
                }

                if(!lesArticles.contains(unArticleVendu)) {
                    lesArticles.add(unArticleVendu);
                }
                else
                {
                    if(uneEnchere.getMontantEnchere() != -1 && !lesArticles.get(lesArticles.indexOf(unArticleVendu)).getLesEncheres().contains(uneEnchere))
                    {
                        lesArticles.get(lesArticles.indexOf(unArticleVendu)).ajouterUneEnchere(uneEnchere);
                    }
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
                unArticleVendu.setEtatVente(getStatut(unArticleVendu));

                Enchere uneEnchere = construireEnchere(res);
                if(uneEnchere.getMontantEnchere() != -1)
                {
                    unArticleVendu.ajouterUneEnchere(uneEnchere);
                }

                if(!lesArticles.contains(unArticleVendu)) {
                    lesArticles.add(unArticleVendu);
                }
                else
                {
                    if(uneEnchere.getMontantEnchere() != -1 && !lesArticles.get(lesArticles.indexOf(unArticleVendu)).getLesEncheres().contains(uneEnchere))
                    {
                        lesArticles.get(lesArticles.indexOf(unArticleVendu)).ajouterUneEnchere(uneEnchere);
                    }
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
                unArticleVendu.setEtatVente(getStatut(unArticleVendu));

                Enchere uneEnchere = construireEnchere(res);
                if(uneEnchere.getMontantEnchere() != -1)
                {
                    unArticleVendu.ajouterUneEnchere(uneEnchere);
                }

                if(!lesArticles.contains(unArticleVendu)) {
                    lesArticles.add(unArticleVendu);
                }
                else
                {
                    if(uneEnchere.getMontantEnchere() != -1 && !lesArticles.get(lesArticles.indexOf(unArticleVendu)).getLesEncheres().contains(uneEnchere))
                    {
                        lesArticles.get(lesArticles.indexOf(unArticleVendu)).ajouterUneEnchere(uneEnchere);
                    }
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
    public List<ArticleVendu> getLesArticlesByEncheresEnCoursAndUserID(int idEncherisseur) {

        List<ArticleVendu> lesArticles = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ARTICLES_BY_ENCHERES_EN_COURS_AND_USER);
            requete.setInt(1,idEncherisseur);
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                ArticleVendu unArticleVendu = construireArticle(res);
                unArticleVendu.setEtatVente(getStatut(unArticleVendu));

                Enchere uneEnchere = construireEnchere(res);
                if(uneEnchere.getMontantEnchere() != -1)
                {
                    unArticleVendu.ajouterUneEnchere(uneEnchere);
                }

                if(!lesArticles.contains(unArticleVendu)) {
                    lesArticles.add(unArticleVendu);
                }
                else
                {
                    if(uneEnchere.getMontantEnchere() != -1 && !lesArticles.get(lesArticles.indexOf(unArticleVendu)).getLesEncheres().contains(uneEnchere))
                    {
                        lesArticles.get(lesArticles.indexOf(unArticleVendu)).ajouterUneEnchere(uneEnchere);
                    }
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
    public List<ArticleVendu> getLesArticlesByEncheresRemporteesAndUserID(int idEncherisseur) {

        List<ArticleVendu> lesArticles = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ARTICLES_BY_ENCHERES_REMPORTEES_AND_USER);
            requete.setInt(1,idEncherisseur);
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                ArticleVendu unArticleVendu = construireArticle(res);
                unArticleVendu.setEtatVente(getStatut(unArticleVendu));

                Enchere uneEnchere = construireEnchere(res);
                if(uneEnchere.getMontantEnchere() != -1)
                {
                    unArticleVendu.ajouterUneEnchere(uneEnchere);
                }

                if(!lesArticles.contains(unArticleVendu)) {
                    lesArticles.add(unArticleVendu);
                }
                else
                {
                    if(uneEnchere.getMontantEnchere() != -1 && !lesArticles.get(lesArticles.indexOf(unArticleVendu)).getLesEncheres().contains(uneEnchere))
                    {
                        lesArticles.get(lesArticles.indexOf(unArticleVendu)).ajouterUneEnchere(uneEnchere);
                    }
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
    public List<ArticleVendu> getLesArticlesByVentesEnCoursAndUserID(int idVendeur) {

        List<ArticleVendu> lesArticles = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ARTICLES_BY_VENTES_EN_COURS_AND_USER);
            requete.setInt(1,idVendeur);
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                ArticleVendu unArticleVendu = construireArticle(res);
                unArticleVendu.setEtatVente(getStatut(unArticleVendu));

                Enchere uneEnchere = construireEnchere(res);
                if(uneEnchere.getMontantEnchere() != -1)
                {
                    unArticleVendu.ajouterUneEnchere(uneEnchere);
                }

                if(!lesArticles.contains(unArticleVendu)) {
                    lesArticles.add(unArticleVendu);
                }
                else
                {
                    if(uneEnchere.getMontantEnchere() != -1 && !lesArticles.get(lesArticles.indexOf(unArticleVendu)).getLesEncheres().contains(uneEnchere))
                    {
                        lesArticles.get(lesArticles.indexOf(unArticleVendu)).ajouterUneEnchere(uneEnchere);
                    }
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
    public List<ArticleVendu> getLesArticlesByVentesNonDebuteesAndUserID(int idVendeur) {

        List<ArticleVendu> lesArticles = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ARTICLES_BY_VENTES_NON_DEBUTEES_AND_USER);
            requete.setInt(1,idVendeur);
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                ArticleVendu unArticleVendu = construireArticle(res);
                unArticleVendu.setEtatVente(getStatut(unArticleVendu));

                Enchere uneEnchere = construireEnchere(res);
                if(uneEnchere.getMontantEnchere() != -1)
                {
                    unArticleVendu.ajouterUneEnchere(uneEnchere);
                }

                if(!lesArticles.contains(unArticleVendu)) {
                    lesArticles.add(unArticleVendu);
                }
                else
                {
                    if(uneEnchere.getMontantEnchere() != -1 && !lesArticles.get(lesArticles.indexOf(unArticleVendu)).getLesEncheres().contains(uneEnchere))
                    {
                        lesArticles.get(lesArticles.indexOf(unArticleVendu)).ajouterUneEnchere(uneEnchere);
                    }
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
    public List<ArticleVendu> getLesArticlesByVentesTermineesAndUserID(int idVendeur) {

        List<ArticleVendu> lesArticles = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ARTICLES_BY_VENTES_TERMINEES_AND_USER);
            requete.setInt(1,idVendeur);
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                ArticleVendu unArticleVendu = construireArticle(res);
                unArticleVendu.setEtatVente(getStatut(unArticleVendu));

                Enchere uneEnchere = construireEnchere(res);
                if(uneEnchere.getMontantEnchere() != -1)
                {
                    unArticleVendu.ajouterUneEnchere(uneEnchere);
                }

                if(!lesArticles.contains(unArticleVendu)) {
                    lesArticles.add(unArticleVendu);
                }
                else
                {
                    if(uneEnchere.getMontantEnchere() != -1 && !lesArticles.get(lesArticles.indexOf(unArticleVendu)).getLesEncheres().contains(uneEnchere))
                    {
                        lesArticles.get(lesArticles.indexOf(unArticleVendu)).ajouterUneEnchere(uneEnchere);
                    }
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

        ArticleVendu unArticleVendu = new ArticleVendu();
        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ARTICLE_BY_ID);
            requete.setInt(1,noArticle);
            ResultSet res = requete.executeQuery();

            while(res.next())
            {
                if(unArticleVendu.getNoArticle() == -1 )
                {
                    unArticleVendu = construireArticle(res);
                    unArticleVendu.setEtatVente(getStatut(unArticleVendu));
                }

                Enchere uneEnchere = construireEnchere(res);

                if(uneEnchere.getMontantEnchere() != -1 && !unArticleVendu.getLesEncheres().contains(uneEnchere)) {
                    unArticleVendu.ajouterUneEnchere(uneEnchere);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return unArticleVendu;
    }

    private String getStatut(ArticleVendu unArticle)
    {
        String statut="";
        if (null != unArticle.getDateDebutEncheres() && null != unArticle.getDateFinEncheres())
        {
            if((unArticle.getDateDebutEncheres().isBefore(LocalDate.now()) || unArticle.getDateDebutEncheres().equals(LocalDate.now())) && unArticle.getDateFinEncheres().isAfter(LocalDate.now()))
            {
                statut = "en_cours";
            }
            else if(unArticle.getDateDebutEncheres().isAfter(LocalDate.now()))
            {
                statut = "non_debutee";
            }
            else if(unArticle.getDateFinEncheres().isBefore(LocalDate.now()))
            {
                statut = "terminee";
            }
        }
        return statut;
    }
}

