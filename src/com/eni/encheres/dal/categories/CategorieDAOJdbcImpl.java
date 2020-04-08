package com.eni.encheres.dal.categories;

import com.eni.encheres.bo.Categorie;
import com.eni.encheres.dal.ConnectionProvider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategorieDAOJdbcImpl implements CategorieDAO {

    private static final String SELECT_ALL="SELECT no_categorie,libelle FROM CATEGORIES";

    private static Categorie itemBuilder(ResultSet res) throws SQLException
    {
        return new Categorie(res.getInt("no_categorie"),res.getString("libelle"));
    }

    @Override
    public List<Categorie> getLesCategories() {
        List<Categorie> lesCategories = new ArrayList<>();

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_ALL);
            ResultSet res = requete.executeQuery();
            while(res.next())
            {
                Categorie uneCategorie = itemBuilder(res);

                if(!lesCategories.contains(uneCategorie))
                {
                    lesCategories.add(uneCategorie);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return lesCategories;
    }
}
