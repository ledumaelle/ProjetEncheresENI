package com.eni.encheres.dal.articles;

import com.eni.encheres.bo.ArticleVendu;
import com.eni.encheres.dal.ConnectionProvider;
import com.eni.encheres.dal.exceptions.ArticleDAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ArticleDAOJdbcImpl implements ArticleDAO {

    private static final String INSERT_NULL_EXCEPTION = "Un article ne peut pas être null";
    private static final String INSERT = "INSERT INTO ARTICLES_VENDUS (nom_article) values (?)";

    @Override
    public void insert(ArticleVendu article) throws ArticleDAOException {
        if(null == article){
            System.err.println(INSERT_NULL_EXCEPTION);
            throw new ArticleDAOException(INSERT_NULL_EXCEPTION);
        }
        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement st = cnx.prepareStatement(INSERT, PreparedStatement.RETURN_GENERATED_KEYS);
            st.setString(1, "TEST");
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if(rs.next())
            {
                System.out.println(rs.getInt(1));
            }
        }
        catch(Exception e)
        {
            System.err.println(e.getMessage());
            throw new ArticleDAOException(e.getMessage());
        }
    }
}
