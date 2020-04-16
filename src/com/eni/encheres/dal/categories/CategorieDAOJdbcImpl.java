package com.eni.encheres.dal.categories;

import com.eni.encheres.bo.Categorie;
import com.eni.encheres.dal.ConnectionProvider;
import com.eni.encheres.dal.utilisateurs.UtilisateurDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CategorieDAOJdbcImpl implements CategorieDAO {

    private static final Logger LOGGER = Logger.getLogger(CategorieDAO.class.toString());

    private static final String SELECT_ALL="SELECT no_categorie,libelle FROM CATEGORIES";
    private static final String SELECT_BY_ID="SELECT * FROM categories WHERE no_categorie=? ";

    private static final String UPDATE = "UPDATE categories SET libelle=? WHERE no_categorie=? ";

    private static final String CREATE = "INSERT INTO categories (libelle) values (?)";

    private static final String DELETE = "DELETE FROM categories WHERE no_categorie=? ";

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

    @Override
    public Categorie getCategorieById(int id) {


        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(SELECT_BY_ID);
            requete.setInt(1,id);
            if (requete.execute()){
                ResultSet res = requete.getResultSet();
                if(res.next()){
                    return itemBuilder(res);
                }
            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Categorie categorie) {

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(UPDATE);
            requete.setString(1,categorie.getLibelle());
            requete.setInt(2,categorie.getNoCategorie());
            if(requete.executeUpdate()!=1){
                LOGGER.severe("erreur lors de l'update");
            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int creer(Categorie categorie) {
        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(CREATE, Statement.RETURN_GENERATED_KEYS);
            requete.setString(1,categorie.getLibelle());

            if(requete.executeUpdate()==1){
                ResultSet res = requete.getGeneratedKeys();
                if(res.next()) {

                    return res.getInt(1);
                }

            } else{
                LOGGER.severe("erreur lors de la création");
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void delete(int id) {

        try(Connection cnx = ConnectionProvider.getConnection())
        {
            PreparedStatement requete = cnx.prepareStatement(DELETE);

            requete.setInt(1,id);
            if(requete.executeUpdate()!=1){
                LOGGER.severe("erreur lors de la suppression");
            }


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }


}
