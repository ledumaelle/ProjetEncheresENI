package com.eni.encheres.dal.encheres;

import com.eni.encheres.bo.Enchere;
import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.ConnectionProvider;
import com.eni.encheres.dal.exceptions.EnchereDAOException;

import java.sql.*;
import java.time.ZoneId;
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

    private static final String INSERT_NULL_EXCEPTION = "Une enchère ne peut pas être null";
    private static final String INSERT = "INSERT INTO ENCHERES (date_enchere, montant_enchere, no_utilisateur, no_article) VALUES (?,?,?,?)";
    private static final String UPDATE = "UPDATE ENCHERES SET date_enchere = ?, montant_enchere = ? WHERE no_utilisateur = ? AND no_article = ?";

    private static final String FIND_ENCHERE_BY_USER_ID = "SELECT * FROM ENCHERES WHERE no_utilisateur = ? and no_article = ?";

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

    @Override
    public void makeEnchere(Enchere enchere) throws EnchereDAOException {
        if(null == enchere) {
            throw new EnchereDAOException(INSERT_NULL_EXCEPTION);
        }

        String query = INSERT;
        if(findEnchereByUserId(enchere.getUnUtilisateur().getNoUtilisateur(), enchere.getUnArticleVendu().getNoArticle())){
            query = UPDATE;
        }

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement st = cnx.prepareStatement(query);
            st.setDate(1, new Date(java.util.Date.from(enchere.getDateEnchere().atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
            st.setInt(2, enchere.getMontantEnchere());
            st.setInt(3, enchere.getUnUtilisateur().getNoUtilisateur());
            st.setInt(4, enchere.getUnArticleVendu().getNoArticle());
            st.executeUpdate();
        }
        catch(Exception e)
        {
            throw new EnchereDAOException(e.getMessage());
        }
    }

    private boolean findEnchereByUserId(int utilisateurId, int articleId) throws EnchereDAOException {
        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement rs = cnx.prepareStatement(FIND_ENCHERE_BY_USER_ID);
            rs.setInt(1, utilisateurId);
            rs.setInt(2, articleId);
            ResultSet res = rs.executeQuery();

            return res.next();
        }
        catch(Exception e)
        {
            throw new EnchereDAOException(e.getMessage());
        }
    }
}
