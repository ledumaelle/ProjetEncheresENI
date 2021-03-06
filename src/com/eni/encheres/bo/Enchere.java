package com.eni.encheres.bo;

import java.time.LocalDate;

/**
 * Class Enchere
 */
public class Enchere {

    private Utilisateur unUtilisateur;
    private ArticleVendu unArticleVendu;
    private LocalDate dateEnchere;
    private int montantEnchere;

   public Enchere()
   {
        this.montantEnchere = -1;
   }

    public Enchere(LocalDate dateEnchere, int montantEnchere) {
        this.dateEnchere = dateEnchere;
        this.montantEnchere = montantEnchere;
    }

    public Enchere(Utilisateur unUtilisateur, ArticleVendu unArticleVendu, LocalDate dateEnchere, int montantEnchere) {
       this(dateEnchere,montantEnchere);
        this.unUtilisateur = unUtilisateur;
        this.unArticleVendu = unArticleVendu;
    }

    public LocalDate getDateEnchere() {
        return dateEnchere;
    }

    public void setDateEnchere(LocalDate dateEnchere) {
        this.dateEnchere = dateEnchere;
    }

    public Utilisateur getUnUtilisateur() {
        return unUtilisateur;
    }

    public void setUnUtilisateur(Utilisateur unUtilisateur) {
        this.unUtilisateur = unUtilisateur;
    }

    public ArticleVendu getUnArticleVendu() {
        return unArticleVendu;
    }

    public void setUnArticleVendu(ArticleVendu unArticleVendu) {
        this.unArticleVendu = unArticleVendu;
    }

    public int getMontantEnchere() {
        return montantEnchere;
    }

    public void setMontantEnchere(int montantEnchere) {
        this.montantEnchere = montantEnchere;
    }

    @Override
    public String toString() {
        return "Enchere{" +
                "unUtilisateur=" + unUtilisateur +
                ", unArticleVendu=" + unArticleVendu +
                ", dateEnchere=" + dateEnchere +
                ", montantEnchere=" + montantEnchere +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Enchere enchere = (Enchere) object;
        return this.getUnUtilisateur().getNoUtilisateur() == enchere.getUnUtilisateur().getNoUtilisateur() &&
                this.getUnArticleVendu().getNoArticle() == enchere.getUnArticleVendu().getNoArticle() &&
                this.getDateEnchere().isEqual(enchere.getDateEnchere()) &&
                this.getMontantEnchere() == enchere.getMontantEnchere();
    }
}
