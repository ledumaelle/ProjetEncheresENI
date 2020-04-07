package com.eni.encheres.dao;

import com.eni.encheres.bo.Utilisateurs;

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


}
