<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="com.eni.encheres.bo.Enchere"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
<head>
    <%! private List<Enchere> lesEncheres; %>

    <%@include file="head.jsp" %>
</head>
<body>
<% uneListe = (Liste) request.getAttribute("uneListe");%>
<%@include file="header.jsp" %>
<br>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-4"></div>
        <div class="col-sm-4">
            <h4 class="text-center">Votre panier : ${uneListe.getLibelle() }</h4>
        </div>
        <div class="col-sm-4"></div>
    </div>
    <br>
    <div class="row">
        <div class="col-sm-4"></div>

        <div class="col-sm-4">
            <c:if test="${uneListe.getLesArticlesDuPanier().size() > 0 }">
                <ul class="list-group">
                    <c:forEach items="${uneListe.getLesArticlesDuPanier()}" var="articleListe">
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <div class="form-group form-check">
                                <label class="form-check-label">
                                    <c:if test="${articleListe.isPanier}">
                                        <input class="form-check-input" type="checkbox" checked name="article" onclick="ajouterPanier(${uneListe.idListe} , ${articleListe.unArticle.idArticle},false);"> ${fn:trim(articleListe.unArticle.libelle)}
                                    </c:if>
                                    <c:if test="${!articleListe.isPanier}">
                                        <input class="form-check-input" type="checkbox" name="article" onclick="ajouterPanier(${uneListe.idListe} , ${articleListe.unArticle.idArticle},true);"> ${fn:trim(articleListe.unArticle.libelle)}
                                    </c:if>
                                </label>
                            </div>
                            <span class="badge badge-secondary badge-pill" data-toggle="tooltip" data-placement="right" title="${ articleListe.unArticle.getUneCategorie().libelle }"><img src="img/categorie/${articleListe.unArticle.getUneCategorie().code }.png" style="width:40px;"></span>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
            <c:if test="${uneListe.getLesArticlesDuPanier().size() <= 0 }">
                <p> Aucun article n'est encore renseigné.</p>
            </c:if>
        </div>
        <div class="col-sm-4"></div>
    </div>
    <br>
    <div class="row">
        <div class="col-sm-4"></div>

        <div class="col-sm-4">
            <a href="index.html" data-toggle="tooltip" data-placement="right" title="Revenir" class="float-left"><img src="img/retour.png" style="width:50px;"></a>
            <a data-placement="right" title="Effacer le panier" class="float-right"  data-toggle="modal" data-target="#effacerArticlesListeModal" style="cursor:pointer;"><img src="img/gomme.png" style="width:50px;"></a>
            <div class="modal fade" id="effacerArticlesListeModal" tabindex="-1" role="dialog" aria-labelledby="effacerArticlesListeLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">Confirmer la suppression</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <span>Vous allez supprimer tous les articles présents dans votre panier.<br> Confirmez-vous cette supprimer ? </span>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="viderPanier(${uneListe.idListe});">Vider</button>
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Fermer</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-4"></div>
    </div>
</div>

<%@include file="footer.jsp" %>

<script>
    new Darkmode({ label:'🌓' }).showWidget();

    function viderPanier(idListe){

        $.ajax({
            url : 'gestion_article_liste.html?clear=true&idListe='+idListe,
            type : 'PUT',
            dataType : 'html',
            success : function(code_html, statut){
                location.reload();
            }
        });

    }

    function ajouterPanier(idListe,idArticle,isPanier){

        $.ajax({
            url : 'gestion_article_liste.html?idListe='+idListe+'&idArticle='+idArticle+'&isPanier='+isPanier,
            type : 'PUT',
            dataType : 'html'
        });

    }
</script>
</body>
</html>