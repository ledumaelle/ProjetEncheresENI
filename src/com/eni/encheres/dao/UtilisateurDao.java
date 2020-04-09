package com.eni.encheres.dao;

import com.eni.encheres.bo.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Class DAO pour les utilisateur
 *
 * @see Utilisateur
 */
public class UtilisateurDao {


    private static final String SELECT_ALL = "SELECT no_utilisateur,pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM utilisateurs";
    private static final String SELECT_BY_ID = "SELECT no_utilisateur,pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM utilisateurs WHERE no_utilisateur=? ";
    private static final String SELECT_BY_MAIL = "SELECT no_utilisateur,pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM utilisateurs WHERE email=?";

    private static final String INSERT = "INSERT INTO utilisateurs (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE utilisateurs SET pseudo=?,nom=?,prenom=?,email=?,telephone=?,rue=?,code_postal=?,ville=?,mot_de_passe=?,credit=?,administrateur=?) WHERE no_utilisateur=? ";
    private static final String DELETE = "DELETE FROM utilisateurs WHERE no_utilisateur=?";


    private static final String DBUSER="userEncheres";
    private static final String PASSWORD="Pa$$w0rd";
    private static final String URL="jdbc:sqlserver://localhost\\SQLEXPRESS";
    private static final String DRIVER="com.microsoft.sqlserver.jdbc.SQLServerDriver";


    /**
     *
     * @param utilisateur
     * @return noUtilisateur ou 0 si erreur
     */
    public int creerUtilisateur(Utilisateur utilisateur){

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e1) {

            e1.printStackTrace();
        }

        try(Connection con = DriverManager.getConnection(URL, DBUSER,PASSWORD)){
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

    }

    public Utilisateur getUtilisateurById(int id){

        Utilisateur utilisateur = new Utilisateur();


        return utilisateur;
    }


    public Utilisateur getUtilisateurByMail(String mail){

        Utilisateur utilisateur = new Utilisateur();


        return utilisateur;
    }


    public List<Utilisateur> getUtilisateurs(){
        List<Utilisateur> liUtilisateur = new ArrayList<>();

        return liUtilisateur;
    }


    public void deleteUtilisateur(int id){

    }


}
