package com.eni.encheres.bo;

import java.util.Objects;

public class Utilisateurs {

   private int no_utilisateur;
   private String pseudo;
   private String nom;
   private String prenom;
   private String email;
   private String telephone;
   private String rue;
   private String code_postal;
   private String ville;
   private String mot_de_passe;
   private int credit;
   private boolean administrateur;


    public Utilisateurs() {
        this.no_utilisateur=0;
        this.credit=0;
        this.administrateur=false;

    }

    public Utilisateurs(int no_utilisateur, String pseudo, String nom, String prenom, String email, String telephone, String rue, String code_postal, String ville, String mot_de_passe, int credit, boolean administrateur) {
        this();
        this.no_utilisateur = no_utilisateur;
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.rue = rue;
        this.code_postal = code_postal;
        this.ville = ville;
        this.mot_de_passe = mot_de_passe;
        this.credit = credit;
        this.administrateur = administrateur;
    }

    public int getNo_utilisateur() {
        return no_utilisateur;
    }

    public void setNo_utilisateur(int no_utilisateur) {
        this.no_utilisateur = no_utilisateur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(boolean administrateur) {
        this.administrateur = administrateur;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateurs that = (Utilisateurs) o;
        return no_utilisateur == that.no_utilisateur &&
                credit == that.credit &&
                administrateur == that.administrateur &&
                Objects.equals(pseudo, that.pseudo) &&
                Objects.equals(nom, that.nom) &&
                Objects.equals(prenom, that.prenom) &&
                Objects.equals(email, that.email) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(rue, that.rue) &&
                Objects.equals(code_postal, that.code_postal) &&
                Objects.equals(ville, that.ville) &&
                Objects.equals(mot_de_passe, that.mot_de_passe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(no_utilisateur, pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Utilisateurs{");
        sb.append("no_utilisateur=").append(no_utilisateur);
        sb.append(", pseudo='").append(pseudo).append('\'');
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", prenom='").append(prenom).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", rue='").append(rue).append('\'');
        sb.append(", code_postal='").append(code_postal).append('\'');
        sb.append(", ville='").append(ville).append('\'');
        sb.append(", mot_de_passe='").append(mot_de_passe).append('\'');
        sb.append(", credit=").append(credit);
        sb.append(", administrateur=").append(administrateur);
        sb.append('}');
        return sb.toString();
    }


}
