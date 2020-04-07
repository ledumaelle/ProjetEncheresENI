package com.eni.encheres.bo;

/**
 * Class Retrait
 */
public class Retrait {

    private String rue;
    private String codePostal;
    private String ville;
    private ArticleVendu unArticleVendu;

    public Retrait() {

    }

    public Retrait(String rue, String codePostal, String ville) {
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
    }

    public Retrait(String rue, String codePostal, String ville, ArticleVendu unArticleVendu) {
        this(rue,codePostal,ville);
        this.unArticleVendu = unArticleVendu;
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

    public ArticleVendu getUnArticleVendu() {
        return unArticleVendu;
    }

    public void setUnArticleVendu(ArticleVendu unArticleVendu) {
        this.unArticleVendu = unArticleVendu;
    }

    @Override
    public String toString() {
        return "Retrait{" +
                "rue='" + rue + '\'' +
                ", codePostal='" + codePostal + '\'' +
                ", ville='" + ville + '\'' +
                ", unArticleVendu=" + unArticleVendu +
                '}';
    }
}
