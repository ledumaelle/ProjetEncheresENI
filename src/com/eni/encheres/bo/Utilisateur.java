package com.eni.encheres.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class Utilisateur
 */
public class Utilisateur {

   private int noUtilisateur;
   private String pseudo;
   private String nom;
   private String prenom;
   private String email;
   private String telephone;
   private String rue;
   private String codePostal;
   private String ville;
   private String motDePasse;
   private int credit;
   private boolean administrateur;

   private List<Enchere> lesEncheres;
    private List<ArticleVendu> lesArticlesVendus;

    public Utilisateur() {
        this.noUtilisateur=0;
        this.credit=0;
        this.administrateur=false;
        lesEncheres = new ArrayList<>();
        lesArticlesVendus = new ArrayList<>();
    }

    public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville, String motDePasse, int credit, boolean administrateur) {
        this();
        this.noUtilisateur = noUtilisateur;
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.motDePasse = motDePasse;
        this.credit = credit;
        this.administrateur = administrateur;
    }

    public int getNoUtilisateur() {
        return noUtilisateur;
    }

    public void setNoUtilisateur(int noUtilisateur) {
        this.noUtilisateur = noUtilisateur;
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

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
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

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
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

    public List<Enchere> getLesEncheres() {
        return lesEncheres;
    }

    /**
     * Fonction Set pour ajouter une enchère
     * @param uneEnchere de type Enchere
     */
    public void ajouterUneEnchere(Enchere uneEnchere) {
        if(!this.lesEncheres.contains(uneEnchere))
        {
            this.lesEncheres.add(uneEnchere);
        }
    }

    public List<ArticleVendu> getLesArticlesVendus() {
        return lesArticlesVendus;
    }

    /**
     * Fonction Set pour ajouter un article vendu
     * @param unArticleVendu de type ArticleVendu
     */
    public void ajouterUnArticleVendu(ArticleVendu unArticleVendu) {
        if(!this.lesArticlesVendus.contains(unArticleVendu))
        {
            this.lesArticlesVendus.add(unArticleVendu);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return noUtilisateur == that.noUtilisateur &&
                credit == that.credit &&
                administrateur == that.administrateur &&
                Objects.equals(pseudo, that.pseudo) &&
                Objects.equals(nom, that.nom) &&
                Objects.equals(prenom, that.prenom) &&
                Objects.equals(email, that.email) &&
                Objects.equals(telephone, that.telephone) &&
                Objects.equals(rue, that.rue) &&
                Objects.equals(codePostal, that.codePostal) &&
                Objects.equals(ville, that.ville) &&
                Objects.equals(motDePasse, that.motDePasse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(noUtilisateur, pseudo, nom, prenom, email, telephone, rue, codePostal, ville, motDePasse, credit, administrateur);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Utilisateurs{");
        sb.append("noUtilisateur=").append(noUtilisateur);
        sb.append(", pseudo='").append(pseudo).append('\'');
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", prenom='").append(prenom).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", rue='").append(rue).append('\'');
        sb.append(", codePostal='").append(codePostal).append('\'');
        sb.append(", ville='").append(ville).append('\'');
        sb.append(", motDePasse='").append(motDePasse).append('\'');
        sb.append(", credit=").append(credit);
        sb.append(", administrateur=").append(administrateur);
        sb.append('}');
        return sb.toString();
    }


}
