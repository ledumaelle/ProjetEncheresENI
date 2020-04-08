package com.eni.encheres.dal.encheres;

import com.eni.encheres.bo.Enchere;
import com.eni.encheres.dal.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnchereDAOJdbcImpl implements EnchereDAO {

    private static final String SELECT_ALL="SELECT * FROM ENCHERES";

    private static Enchere itemBuilderEnchere(ResultSet res) throws SQLException
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
                Enchere uneEnchere = itemBuilderEnchere(res);

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
}
