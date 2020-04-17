package com.eni.encheres.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Class ArticleVendu
 */
public class ArticleVendu {

    private int noArticle;
    private String nomArticle;
    private String description;
    private LocalDate dateDebutEncheres;
    private LocalDate dateFinEncheres;
    private int miseAPrix;
    private int prixVente;
    private String nomImage;
    private String etatVente;
    private Categorie uneCategorie;
    private Utilisateur unUtilisateur;
    private List<Enchere> lesEncheres;

    public ArticleVendu()
    {
        lesEncheres = new ArrayList<>();
        this.noArticle =-1;
    }

    public ArticleVendu(int noArticle)
    {
        this.noArticle = noArticle;
        lesEncheres = new ArrayList<>();
    }

    public ArticleVendu(String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int miseAPrix, Categorie uneCategorie, Utilisateur unUtilisateur, String nomImage) {
        lesEncheres = new ArrayList<>();
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.uneCategorie = uneCategorie;
        this.unUtilisateur = unUtilisateur;
        this.prixVente = 0;
        this.nomImage = nomImage;
    }

    public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int miseAPrix, Categorie uneCategorie) {
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.uneCategorie = uneCategorie;
    }

    public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int miseAPrix, int prixVente, String nomImage) {
        lesEncheres = new ArrayList<>();
        this.noArticle = noArticle;
        this.nomArticle = nomArticle;
        this.description = description;
        this.dateDebutEncheres = dateDebutEncheres;
        this.dateFinEncheres = dateFinEncheres;
        this.miseAPrix = miseAPrix;
        this.prixVente = prixVente;
        this.nomImage = nomImage;
    }

    public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int miseAPrix, int prixVente, String etatVente, String nomImage) {
        this(noArticle,nomArticle,description,dateDebutEncheres,dateFinEncheres,miseAPrix,prixVente,nomImage);
        this.etatVente = etatVente;
    }

    public ArticleVendu(int noArticle, String nomArticle, String description, LocalDate dateDebutEncheres, LocalDate dateFinEncheres, int miseAPrix, int prixVente, String nomImage, String etatVente, Categorie uneCategorie) {
        this(noArticle,nomArticle,description,dateDebutEncheres,dateFinEncheres,miseAPrix,prixVente,nomImage,etatVente);
        this.uneCategorie = uneCategorie;
    }

    public int getNoArticle() {
        return noArticle;
    }

    public void setNoArticle(int noArticle) {
        this.noArticle = noArticle;
    }

    public String getNomArticle() {
        return nomArticle;
    }

    public void setNomArticle(String nomArticle) {
        this.nomArticle = nomArticle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDebutEncheres() {
        return dateDebutEncheres;
    }

    public void setDateDebutEncheres(LocalDate dateDebutEncheres) {
        this.dateDebutEncheres = dateDebutEncheres;
    }

    public LocalDate getDateFinEncheres() {
        return dateFinEncheres;
    }

    public void setDateFinEncheres(LocalDate dateFinEncheres) {
        this.dateFinEncheres = dateFinEncheres;
    }

    public int getMiseAPrix() {
        return miseAPrix;
    }

    public void setMiseAPrix(int miseAPrix) {
        this.miseAPrix = miseAPrix;
    }

    public int getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(int prixVente) {
        this.prixVente = prixVente;
    }

    public String getEtatVente() {
        return etatVente;
    }

    public void setEtatVente(String etatVente) {
        this.etatVente = etatVente;
    }

    public Categorie getUneCategorie() {
        return uneCategorie;
    }

    public void setUneCategorie(Categorie uneCategorie) {
        this.uneCategorie = uneCategorie;
    }

    public Utilisateur getUnUtilisateur() {
        return unUtilisateur;
    }

    public void setUnUtilisateur(Utilisateur unUtilisateur) {
        this.unUtilisateur = unUtilisateur;
    }

    public String getNomImage() {
        return nomImage;
    }

    public void setNomImage(String nomImage) {
        this.nomImage = nomImage;
    }

    public List<Enchere> getLesEncheres() {
        return lesEncheres;
    }

    public void ajouterUneEnchere(Enchere uneEnchere)
    {
        if(!lesEncheres.contains(uneEnchere))
        {
            lesEncheres.add(uneEnchere);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.noArticle == ((ArticleVendu)obj).getNoArticle();
    }

    @Override
    public String toString() {
        return "ArticleVendu{" +
                "noArticle=" + noArticle +
                ", nomArticle='" + nomArticle + '\'' +
                ", description='" + description + '\'' +
                ", dateDebutEncheres=" + dateDebutEncheres +
                ", dateFinEncheres=" + dateFinEncheres +
                ", miseAPrix=" + miseAPrix +
                ", prixVente=" + prixVente +
                ", nomImage='" + nomImage + '\'' +
                ", etatVente='" + etatVente + '\'' +
                ", uneCategorie=" + uneCategorie +
                ", unUtilisateur=" + unUtilisateur +
                ", lesEncheres=" + lesEncheres +
                '}';
    }
}
