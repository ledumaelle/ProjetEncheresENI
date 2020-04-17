package com.eni.encheres.dal.utilisateurs;

import com.eni.encheres.bo.Utilisateur;
import com.eni.encheres.dal.exceptions.UtilisateurDAOException;

import java.util.List;

public interface UtilisateurDao {

    public int creerUtilisateur(Utilisateur utilisateur);

    public void majUtilisateur(Utilisateur utilisateur);

    public boolean hasArticleOrEnchere(int id);

    public Utilisateur getUtilisateurById(int id);

    public Utilisateur getUtilisateurByMail(String mail);

    public Utilisateur getUtilisateurByPseudo(String pseudo);

    public List<Utilisateur> getUtilisateurs();

    public void deleteUtilisateur(int id);

    public void updateCredit(int id, int credit, String type) throws UtilisateurDAOException;
}
