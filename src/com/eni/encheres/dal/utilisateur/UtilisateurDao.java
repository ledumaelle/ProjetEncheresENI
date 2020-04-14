package com.eni.encheres.dal.utilisateur;

import com.eni.encheres.bo.Utilisateur;

import java.util.List;

public interface UtilisateurDao {

    public int creerUtilisateur(Utilisateur utilisateur);

    public void majUtilisateur(Utilisateur utilisateur);

    public Utilisateur getUtilisateurById(int id);

    public Utilisateur getUtilisateurByMail(String mail);

    public List<Utilisateur> getUtilisateurs();

    public void deleteUtilisateur(int id);

}
