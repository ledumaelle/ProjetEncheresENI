package com.eni.encheres.dao;

import com.eni.encheres.bo.Utilisateurs;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateursDao {


    private static final String SELECT_ALL = "SELECT no_utilisateur,pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM utilisateurs";
    private static final String SELECT_BY_ID = "SELECT no_utilisateur,pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM utilisateurs WHERE no_utilisateur=? ";
    private static final String SELECT_BY_MAIL = "SELECT no_utilisateur,pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur FROM utilisateurs WHERE email=?";

    private static final String INSERT = "INSERT INTO utilisateurs (pseudo,nom,prenom,email,telephone,rue,code_postal,ville,mot_de_passe,credit,administrateur) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE utilisateurs SET pseudo=?,nom=?,prenom=?,email=?,telephone=?,rue=?,code_postal=?,ville=?,mot_de_passe=?,credit=?,administrateur=?) WHERE no_utilisateur=? ";
    private static final String DELETE = "DELETE FROM utilisateurs WHERE no_utilisateur=?";



    

    public void creerUtilisateur(Utilisateurs utilisateurs){

    }

    public void majUtilisateur(Utilisateurs utilisateurs){

    }

    public Utilisateurs getUtilisateurById(int id){

        Utilisateurs utilisateurs = new Utilisateurs();


        return utilisateurs;
    }


    public Utilisateurs getUtilisateurByMail(String mail){

        Utilisateurs utilisateurs = new Utilisateurs();


        return utilisateurs;
    }


    public List<Utilisateurs> getUtilisateurs(){
        List<Utilisateurs> liUtilisateur = new ArrayList<>();

        return liUtilisateur;
    }


    public void deleteUtilisateur(int id){

    }

    public Utilisateurs checkLogin(String email, String password) throws SQLException,
            ClassNotFoundException {
        String jdbcURL = "jdbc:mysql://localhost:3306/";
        String dbUser = "userEncheres";
        String dbPassword = "Pa$$w0rd";

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
        String sql = "SELECT * FROM UTILISATEURS WHERE email = ? and mot_de_passe = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email);
        statement.setString(2, password);

        ResultSet result = statement.executeQuery();

        Utilisateurs utilisateurs = null;

        if (result.next()) {
            utilisateurs = new Utilisateurs();
            utilisateurs.setPseudo(result.getString("pseudo"));
            utilisateurs.setEmail(email);
        }

        connection.close();

        return utilisateurs;
    }


}
