package com.eni.encheres.bo;

/**
 * Class Categorie
 */
public class Categorie {

    private int noCategorie;
    private String libelle;

    public Categorie()
    {

    }

    public Categorie(int noCategorie, String libelle) {
        this.noCategorie = noCategorie;
        this.libelle = libelle;
    }

    public int getNoCategorie() {
        return noCategorie;
    }

    public void setNoCategorie(int noCategorie) {
        this.noCategorie = noCategorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return this.noCategorie == ((Categorie)obj).getNoCategorie();
    }

    @Override
    public String toString() {
        return  libelle;
    }
}
