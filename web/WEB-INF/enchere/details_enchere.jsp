<%--
  Created by IntelliJ IDEA.
  User: LE DU Maëlle
  Date: 10/04/2020
  Time: 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.eni.encheres.bo.Enchere" %>
<%@ page import="com.eni.encheres.bo.ArticleVendu" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <%@ include file="../template/head.jsp" %>
        <title>Détails d'une enchère</title>
    </head>
    <body>
        <c:if test="${!empty requestScope.unArticle}">
            <c:set var="unArticle" value="${requestScope.unArticle}" scope="page" />
        </c:if>

        <c:if test="${!empty requestScope.lastEnchere}">
            <c:set var="lastEnchere" value="${requestScope.lastEnchere}" scope="page" />
        </c:if>

        <fmt:parseDate  value="${unArticle.getDateDebutEncheres()}"  type="date" pattern="yyyy-MM-dd" var="debutDate"/>
        <fmt:parseDate  value="${unArticle.getDateFinEncheres()}"  type="date" pattern="yyyy-MM-dd" var="finDate"/>

        <%@ include file="../template/header.jsp" %>


        <div class="container my-5 py-5 z-depth-1">
            <div class="row">
                <div class="text-center text-md-left text-md-right col-sm-2">
                    <c:if test="${unArticle.getEtatVente().equals('en_cours')}">
                        <span class="badge badge-success product mb-4 ml-xl-0 ml-4">EN COURS</span>
                    </c:if>
                    <c:if test="${unArticle.getEtatVente().equals('non_debutee')}">
                        <span class="badge badge-warning product mb-4 ml-xl-0 ml-4">NON DÉBUTÉE</span>
                    </c:if>
                    <c:if test="${unArticle.getEtatVente().equals('terminee')}">
                        <span class="badge badge-danger product mb-4 ml-xl-0 ml-4">TERMINÉE</span>
                    </c:if>
                </div>
                <div class="text-center text-md-left text-md-right col-sm-10">
                    <c:if test="${unUtilisateur.getNoUtilisateur() == unArticle.getUnUtilisateur().getNoUtilisateur() && unArticle.getEtatVente().equals('non_debutee')}">
                        <button class="btn btn-secondary-color btn-rounded">
                            <i class="fas fa-edit mr-2" aria-hidden="true"></i> Modifier</button>
                    </c:if>
                </div>
            </div>

            <!--Section: Content-->
            <section class="text-center">
                <!-- Section heading -->
                <h3 class="font-weight-bold mb-5">Détails d'une enchère</h3>

                <div class="row">

                    <div class="col-lg-6">

                        <img src="<%=request.getContextPath()%>/img/articles/${unArticle.getNomImage()}"
                             alt="${unArticle.getNomImage()}" class="img-fluid">
                    </div>

                    <div class="col-lg-5 text-center text-md-left">

                        <h2 class="h2-responsive text-center text-md-left product-name font-weight-bold dark-grey-text mb-1 ml-xl-0 ml-4">
                            <strong>${unArticle.getNomArticle()}</strong>
                        </h2>
                        <span class="badge badge-danger product mb-4 ml-xl-0 ml-4">${unArticle.getUneCategorie().getLibelle()}</span>
                        <h3 class="h3-responsive text-center text-md-left mb-5 ml-xl-0 ml-4">
                            <c:if test="${empty lastEnchere.getMontantEnchere()}">
                                <span>Aucune enchère pour le moment.</span><br/>
                            </c:if>
                            <span class="red-text font-weight-bold">
                                <c:if test="${!empty lastEnchere.getMontantEnchere()}">
                                    <strong class="material-tooltip-main" data-toggle="tooltip" data-placement="bottom" data-html="true"
                                        title="Meilleure offre par <b>${lastEnchere.getUnUtilisateur().getPseudo()}</b>">
                                        ${lastEnchere.getMontantEnchere()} pts
                                    </c:if>
                                </strong>
                            <span class="grey-text">
                                <small>
                                    <span class="material-tooltip-main" data-toggle="tooltip" data-placement="bottom" data-html="true" title="Mise à prix">${unArticle.getMiseAPrix()} pts
                                    </span>
                                </small>
                            </span>
                          </span>
                        </h3>

                        <!--Accordion wrapper-->
                        <div class="accordion md-accordion" id="accordionEx" role="tablist" aria-multiselectable="true">

                            <!-- Accordion card -->
                            <div class="card">

                                <!-- Card header -->
                                <div class="card-header" role="tab" id="headingOne1">
                                    <a data-toggle="collapse" data-parent="#accordionEx" href="#collapseOne1" aria-expanded="true"
                                       aria-controls="collapseOne1">
                                        <h5 class="mb-0">
                                            Description
                                            <i class="fas fa-angle-down rotate-icon"></i>
                                        </h5>
                                    </a>
                                </div>

                                <!-- Card body -->
                                <div id="collapseOne1" class="collapse show" role="tabpanel" aria-labelledby="headingOne1"
                                     data-parent="#accordionEx">
                                    <div class="card-body">
                                        ${unArticle.getDescription()}
                                    </div>
                                </div>
                            </div>
                            <!-- Accordion card -->

                            <!-- Accordion card -->
                            <div class="card">

                                <!-- Card header -->
                                <div class="card-header" role="tab" id="headingTwo2">
                                    <a class="collapsed" data-toggle="collapse" data-parent="#accordionEx" href="#collapseTwo2"
                                       aria-expanded="false" aria-controls="collapseTwo2">
                                        <h5 class="mb-0">
                                            Détails
                                            <i class="fas fa-angle-down rotate-icon"></i>
                                        </h5>
                                    </a>
                                </div>

                                <!-- Card body -->
                                <div id="collapseTwo2" class="collapse" role="tabpanel" aria-labelledby="headingTwo2"
                                     data-parent="#accordionEx">
                                    <div class="card-body">
                                        <span> <b>Vendeur : </b>${unArticle.getUnUtilisateur().getPseudo()}</span><br/>
                                        <span>  <b>Début de l'enchère : </b> <fmt:formatDate pattern="dd/MM/YYYY" value="${debutDate}"/></span><br/>
                                        <span>  <b>Fin de l'enchère : </b> <fmt:formatDate pattern="dd/MM/YYYY" value="${finDate}"/></span>
                                    </div>
                                </div>
                            </div>
                            <!-- Accordion card -->

                            <!-- Accordion card -->
                            <div class="card">

                                <!-- Card header -->
                                <div class="card-header" role="tab" id="headingThree3">
                                    <a class="collapsed" data-toggle="collapse" data-parent="#accordionEx" href="#collapseThree3"
                                       aria-expanded="false" aria-controls="collapseThree3">
                                        <h5 class="mb-0">
                                            Retrait
                                            <i class="fas fa-angle-down rotate-icon"></i>
                                        </h5>
                                    </a>
                                </div>

                                <!-- Card body -->
                                <div id="collapseThree3" class="collapse" role="tabpanel" aria-labelledby="headingThree3"
                                     data-parent="#accordionEx">
                                    <div class="card-body">
                                        <span> <b> Rue : </b> ${unArticle.getUnUtilisateur().getRue()}</span><br/>
                                        <span> <b> CP : </b> ${unArticle.getUnUtilisateur().getCodePostal()}</span><br/>
                                        <span> <b> Ville : </b> ${unArticle.getUnUtilisateur().getVille()}</span><br/>
                                    </div>
                                </div>
                            </div>
                            <!-- Accordion card -->

                        </div>
                        <!--/.Accordion wrapper-->
                        <c:if test="${unUtilisateur.getNoUtilisateur() != unArticle.getUnUtilisateur().getNoUtilisateur() && unArticle.getEtatVente().equals('en_cours')}">
                            <!-- Add to Cart -->
                            <section class="color">
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-sm-6">
                                            <label class="mdb-main-label grey-text" for="nbProposition">Ma proposition</label>
                                            <input type="number" class="form-control" id="nbProposition" name="nbProposition" value="${lastEnchere.getMontantEnchere()}">
                                        </div>
                                            <div class="col-sm-6 text-center text-md-left text-md-right mt-3">
                                                <button class="btn btn-primary btn-rounded">
                                                    <i class="fas fa-cart-plus mr-2" aria-hidden="true"></i> Enchérir</button>
                                            </div>

                                    </div>
                                </div>
                            </section>
                            <!-- /.Add to Cart -->
                        </c:if>
                        </div>
                    </div>
                </section>
                <!--Section: Content-->
            </div>
        <script>
            $(function () {
                $('.material-tooltip-main').tooltip({
                    template: '<div class="tooltip md-tooltip"><div class="tooltip-arrow md-arrow"></div><div class="tooltip-inner md-inner"></div></div>'
                });
            })
        </script>
    </body>
</html>
