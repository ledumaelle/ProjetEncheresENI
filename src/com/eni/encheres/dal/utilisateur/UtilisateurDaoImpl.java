package com.eni.encheres.dal.utilisateur;

import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.ConnectionProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


/**
 * Class DAO pour les utilisateur
 *
 * @see Utilisateur
 */
public class UtilisateurDaoImpl implements UtilisateurDao {


    private static final String SELECT_ALL = "SELECT * FROM utilisateurs";
    private static final String SELECT_BY_ID = "SELECT * FROM utilisateurs WHERE no_utilisateur=? ";
    private static final String SELECT_BY_MAIL = "SELECT * FROM utilisateurs WHERE email=?";

    private static final String INSERT = "INSERT INTO utilisateurs (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE utilisateurs SET pseudo=?,nom=?,prenom=?,email=?,telephone=?,rue=?,code_postal=?,ville=?,mot_de_passe=?,credit=?,administrateur=?) WHERE no_utilisateur=? ";
    private static final String DELETE = "DELETE FROM utilisateurs WHERE no_utilisateur=?";


    private static final Logger LOGGER = Logger.getLogger(UtilisateurDao.class.toString());




    


    private Utilisateur utilisateurBuilder(ResultSet resultSet) throws SQLException {

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setPseudo(resultSet.getString("pseudo"));
        utilisateur.setNom(resultSet.getString("nom"));
        utilisateur.setPrenom(resultSet.getString("prenom"));
        utilisateur.setEmail(resultSet.getString("email"));
        utilisateur.setTelephone(resultSet.getString("telephone"));
        utilisateur.setRue(resultSet.getString("rue"));
        utilisateur.setCodePostal(resultSet.getString("code_postal"));
        utilisateur.setVille(resultSet.getString("ville"));
        utilisateur.setMotDePasse(resultSet.getString("mot_de_passe"));
        utilisateur.setCredit(resultSet.getInt("credit"));
        utilisateur.setAdministrateur(resultSet.getBoolean("administrateur"));
        utilisateur.setNoUtilisateur(resultSet.getInt("no_utilisateur"));

        return utilisateur;

    }



    /**
     *
     * @param utilisateur
     * @return noUtilisateur ou 0 si erreur
     */
    public int creerUtilisateur(Utilisateur utilisateur){



        try(Connection con = ConnectionProvider.getConnection()){
            PreparedStatement stmt = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1,utilisateur.getPseudo());
            stmt.setString(2,utilisateur.getNom());
            stmt.setString(3,utilisateur.getPrenom());
            stmt.setString(4,utilisateur.getEmail());
            stmt.setString(5,utilisateur.getTelephone());
            stmt.setString(6,utilisateur.getRue());
            stmt.setString(7,utilisateur.getCodePostal());
            stmt.setString(8,utilisateur.getVille());
            stmt.setString(9,utilisateur.getMotDePasse());

            stmt.setInt(10,utilisateur.getCredit());
            stmt.setBoolean(11,utilisateur.isAdministrateur());

            if(stmt.executeUpdate()==1) {

                ResultSet res = stmt.getGeneratedKeys();
                if(res.next()) {

                    return res.getInt(1);
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return 0;


    }

    public void majUtilisateur(Utilisateur utilisateur){

        try(Connection con = ConnectionProvider.getConnection()){
            PreparedStatement stmt = con.prepareStatement(UPDATE);

            stmt.setString(1,utilisateur.getPseudo());
            stmt.setString(2,utilisateur.getNom());
            stmt.setString(3,utilisateur.getPrenom());
            stmt.setString(4,utilisateur.getEmail());
            stmt.setString(5,utilisateur.getTelephone());
            stmt.setString(6,utilisateur.getRue());
            stmt.setString(7,utilisateur.getCodePostal());
            stmt.setString(8,utilisateur.getVille());
            stmt.setString(9,utilisateur.getMotDePasse());

            stmt.setInt(10,utilisateur.getCredit());
            stmt.setBoolean(11,utilisateur.isAdministrateur());
            stmt.setInt(12,utilisateur.getNoUtilisateur());

            if(stmt.executeUpdate()!=1){
                LOGGER.severe("erreur maj utilisteur");
            }


        }catch(Exception e) {
            e.printStackTrace();
        }


    }

    /**
     *
     * @param id
     * @return utilisateur ou null si aucun resultat
     */
    public Utilisateur getUtilisateurById(int id){


        try(Connection con = ConnectionProvider.getConnection()){
            PreparedStatement stmt = con.prepareStatement(SELECT_BY_ID);
            stmt.setInt(1,id);

            if(stmt.execute()){
                ResultSet resultSet = stmt.getResultSet();

                if(resultSet.next()){


                    return utilisateurBuilder(resultSet);

                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     *
     * @param mail
     * @return utilisateur ou null si aucun resultat
     */
    public Utilisateur getUtilisateurByMail(String mail){

        Utilisateur utilisateur = new Utilisateur();

        try(Connection con = ConnectionProvider.getConnection()){
            PreparedStatement stmt = con.prepareStatement(SELECT_BY_MAIL);
            stmt.setString(1,mail);

            if(stmt.execute()){
                ResultSet resultSet = stmt.getResultSet();

                if(resultSet.next()){


                    return utilisateurBuilder(resultSet);
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     *
     * @return liste de tout les utilisateurs ou null si aucun resultat
     */
    public List<Utilisateur> getUtilisateurs(){
        List<Utilisateur> liUtilisateur = new ArrayList<>();

        try(Connection con = ConnectionProvider.getConnection()){
            PreparedStatement stmt = con.prepareStatement(SELECT_ALL);


            if(stmt.execute()){
                ResultSet resultSet = stmt.getResultSet();

                while(resultSet.next()){

                    liUtilisateur.add(utilisateurBuilder(resultSet));

                }

                return liUtilisateur;

            }

        }catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     *
     * @param id
     */
    public void deleteUtilisateur(int id) {

        try (Connection con = ConnectionProvider.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(DELETE);
            stmt.setInt(1,id);

            if(stmt.executeUpdate()!=1){
                LOGGER.severe("erreur suppression utilisateur");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
