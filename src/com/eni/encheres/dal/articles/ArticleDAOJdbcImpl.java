package com.eni.encheres.dal.articles;

import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.dal.ConnectionProvider;
import com.eni.encheres.dal.exceptions.ArticleDAOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.ZoneId;

public class ArticleDAOJdbcImpl implements ArticleDAO {
    private final String INSERT_NULL_EXCEPTION = "Un article ne peut pas être null";
    private final String INSERT = "INSERT INTO ARTICLES_VENDUS VALUES (?,?,?,?,?,?,?,?)";

    @Override
    public ArticleVendu insert(ArticleVendu article) throws ArticleDAOException {
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
        return article;
    }
}
