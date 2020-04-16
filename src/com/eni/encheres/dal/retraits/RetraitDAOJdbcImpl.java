package com.eni.encheres.dal.retraits;

import com.eni.encheres.bo.Retrait;
import com.eni.encheres.dal.ConnectionProvider;
import com.eni.encheres.dal.exceptions.RetraitDAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RetraitDAOJdbcImpl implements RetraitDAO {
    private final String INSERT_NULL_EXCEPTION = "Un retrait ne peut pas être null";
    private final String INSERT = "INSERT INTO RETRAITS (no_article, rue, code_postal, ville) VALUES (?,?,?,?)";

    private final String UPDATE = "UPDATE RETRAITS SET rue = ?, " +
            "code_postal = ?, " +
            "ville = ? " +
            "WHERE no_article = ?";

    private final String UPDATE_IS_RETIRE = "UPDATE RETRAITS SET is_retire = 1 where no_article = ?";

    private final String SELECT_BY_ARTICLE_ID = "SELECT * FROM RETRAITS WHERE no_article = ?";

    @Override
    public void insert(Retrait retrait) throws RetraitDAOException {
        if(null == retrait) {
            throw new RetraitDAOException(INSERT_NULL_EXCEPTION);
        }

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement st = cnx.prepareStatement(INSERT);
            st.setInt(1, retrait.getUnArticleVendu().getNoArticle());
            st.setString(2, retrait.getRue());
            st.setString(3, retrait.getCodePostal());
            st.setString(4, retrait.getVille());
            st.executeUpdate();
        }
        catch(Exception e)
        {
            throw new RetraitDAOException(e.getMessage());
        }
    }

    @Override
    public void update(Retrait retrait) throws RetraitDAOException {
        if(null == retrait) {
            throw new RetraitDAOException(INSERT_NULL_EXCEPTION);
        }

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement st = cnx.prepareStatement(UPDATE);
            st.setString(1, retrait.getRue());
            st.setString(2, retrait.getCodePostal());
            st.setString(3, retrait.getVille());
            st.setInt(4, retrait.getUnArticleVendu().getNoArticle());
            st.executeUpdate();
        }
        catch(Exception e)
        {
            throw new RetraitDAOException(e.getMessage());
        }
    }

    @Override
    public Retrait getRetraitByArticleId(int id) throws RetraitDAOException {
        try(Connection cnx = ConnectionProvider.getConnection())
        {
            Retrait retrait = null;
            PreparedStatement requete = cnx.prepareStatement(SELECT_BY_ARTICLE_ID);
            requete.setInt(1, id);
            ResultSet res = requete.executeQuery();
            if(res.next())
            {
                retrait = itemBuilder(res);
            }
            return retrait;
        }
        catch(Exception e)
        {
            throw new RetraitDAOException(e.getMessage());
        }
    }

    @Override
    public void setArticleIsRetire(int articleId) throws RetraitDAOException {
        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement st = cnx.prepareStatement(UPDATE_IS_RETIRE);
            st.setInt(1, articleId);
            st.executeUpdate();
        }
        catch(Exception e)
        {
            throw new RetraitDAOException(e.getMessage());
        }
    }

    private Retrait itemBuilder(ResultSet rs) throws SQLException {
        return new Retrait(rs.getString("rue"), rs.getString("code_postal"), rs.getString("ville"), rs.getBoolean("is_retire"));
    }
}
