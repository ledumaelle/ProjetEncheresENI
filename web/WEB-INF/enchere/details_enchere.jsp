<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <jsp:include page="../template/head.jsp"/>
        <script src="<%= request.getContextPath()%>/script/detailsEnchere.js"></script>
        <title>Détails d'une enchère</title>
    </head>
    <body>
        <c:if test="${!empty requestScope.unArticle}">
            <c:set var="unArticle" value="${requestScope.unArticle}" scope="page" />
        </c:if>

        <c:if test="${!empty requestScope.lastEnchere}">
            <c:set var="lastEnchere" value="${requestScope.lastEnchere}" scope="page" />
        </c:if>

        <c:if test="${!empty requestScope.unRetrait}">
            <c:set var="unRetrait" value="${requestScope.unRetrait}" scope="page" />
        </c:if>

        <fmt:parseDate  value="${unArticle.getDateDebutEncheres()}"  type="date" pattern="yyyy-MM-dd" var="debutDate"/>
        <fmt:parseDate  value="${unArticle.getDateFinEncheres()}"  type="date" pattern="yyyy-MM-dd" var="finDate"/>

        <jsp:include page="../template/header.jsp"/>

        <c:if test="${!empty unArticle}">

            <div class="container my-5 py-5 z-depth-1">
                <div class="row">
                    <div class="text-center text-md-left text-md-right col-sm-12">
                        <c:if test="${unUtilisateur.getNoUtilisateur() == unArticle.getUnUtilisateur().getNoUtilisateur() && unArticle.getEtatVente().equals('non_debutee')}">
                            <a href="<%= request.getContextPath() %>/articles/form?no_article=${unArticle.getNoArticle()}" class="btn btn-secondary-color btn-rounded">
                                <i class="fas fa-edit mr-2" aria-hidden="true"></i> Modifier</a>
                        </c:if>
                        <c:if test="${unUtilisateur.getNoUtilisateur() == unArticle.getUnUtilisateur().getNoUtilisateur() && unArticle.getEtatVente().equals('terminee') && !unRetrait.getRetire()}">
                            <a href="<%= request.getContextPath() %>/retirer?no_article=${unArticle.getNoArticle()}&credit=${unArticle.getPrixVente()}" class="btn btn-secondary-color btn-rounded">
                                <i class="fas fa-box-open" aria-hidden="true"></i> Retirer</a>
                        </c:if>
                    </div>
                </div>

                <!--Section: Content-->
                <section class="text-center">
                    <div class="row">

                        <div class="col-lg-6">

                            <img src="<%=request.getContextPath()%>/img/articles/${unArticle.getNomImage()}"
                                 alt="${unArticle.getNomImage()}" class="img-fluid">
                        </div>

                        <div class="col-lg-5 text-center text-md-left">
                            <c:if test="${unArticle.getEtatVente().equals('terminee')}">
                                <h4><span class="badge badge-pill badge-primary"><i class="fas fa-gavel"></i>
                                    <c:if test="${unUtilisateur.getNoUtilisateur() != lastEnchere.getUnUtilisateur().getNoUtilisateur()}">
                                        ${lastEnchere.getUnUtilisateur().getPseudo()} a remporté l'enchère!
                                    </c:if>
                                    <c:if test="${unUtilisateur.getNoUtilisateur() == lastEnchere.getUnUtilisateur().getNoUtilisateur()}">
                                        Vous avez remporté l'enchère!
                                    </c:if>
                                </span></h4>
                            </c:if>
                            <h2 class="h2-responsive text-center text-md-left product-name font-weight-bold dark-grey-text mb-1 ml-xl-0 ml-4">
                                <strong>${unArticle.getNomArticle()}</strong>
                            </h2>
                            <span class="badge purple product mb-4 ml-xl-0 ml-4">${unArticle.getUneCategorie().getLibelle()}</span>
                            <c:if test="${unArticle.getEtatVente().equals('en_cours')}">
                                <span class="badge badge-success product mb-4 ml-xl-0 ml-4">EN COURS</span>
                            </c:if>
                            <c:if test="${unArticle.getEtatVente().equals('non_debutee')}">
                                <c:set var="dayBefore" value="${ChronoUnit.DAYS.between(LocalDate.now(), unArticle.getDateDebutEncheres())}" scope="page" />
                                <span class="badge badge-warning product mb-4 ml-xl-0 ml-4">NON DÉBUTÉE J-${dayBefore}</span>
                            </c:if>
                            <c:if test="${unArticle.getEtatVente().equals('terminee')}">
                                <span class="badge badge-danger product mb-4 ml-xl-0 ml-4">TERMINÉE</span>
                            </c:if>
                            <h3 class="h3-responsive text-center text-md-left mb-5 ml-xl-0 ml-4">
                                <c:if test="${unArticle.getEtatVente().equals('non_debutee')}">
                                    <span>Les enchères n'ont pas encore commencé.</span><br/>
                                </c:if>
                                <c:if test="${!unArticle.getEtatVente().equals('non_debutee')}">
                                    <c:if test="${empty lastEnchere.getMontantEnchere()}">
                                        <span>Aucune enchère pour le moment.</span><br/>
                                    </c:if>
                                    <c:if test="${!empty lastEnchere.getMontantEnchere()}">
                                    <span class="red-text font-weight-bold">
                                        <strong class="material-tooltip-main" data-toggle="tooltip" data-placement="bottom" data-html="true"
                                                title="Meilleure offre par <b>${lastEnchere.getUnUtilisateur().getPseudo()}</b>">
                                            ${lastEnchere.getMontantEnchere()} pts
                                        </strong>
                                    </span>
                                    </c:if>
                                </c:if>
                                <span class="grey-text">
                                    <small>
                                        <span class="material-tooltip-main" data-toggle="tooltip" data-placement="bottom" data-html="true" title="Mise à prix">${unArticle.getMiseAPrix()} pts
                                        </span>
                                    </small>
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
                                            <span> <b> Rue : </b> ${unRetrait.getRue()}</span><br/>
                                            <span> <b> CP : </b> ${unRetrait.getCodePostal()}</span><br/>
                                            <span> <b> Ville : </b> ${unRetrait.getVille()}</span><br/>
                                        </div>
                                    </div>
                                </div>
                                <!-- Accordion card -->

                            </div>
                            <!--/.Accordion wrapper-->
                            <c:if test="${unUtilisateur.getNoUtilisateur() != unArticle.getUnUtilisateur().getNoUtilisateur() && unArticle.getEtatVente().equals('en_cours')}">
                                <section class="color">
                                    <div class="mt-5">
                                        <div class="row">
                                            <form  class="form-inline" action="<%= request.getContextPath() %>/details_enchere" method="post">
                                                <div class="col-sm-6">
                                                    <label class="mdb-main-label grey-text" for="newEnchere">Ma proposition</label>
                                                    <input
                                                            type="number"
                                                            class="form-control"
                                                            id="newEnchere"
                                                            name="newEnchere"
                                                            min="${lastEnchere == null ? unArticle.getMiseAPrix() : (lastEnchere.getMontantEnchere() + 5)}"
                                                            step="5"
                                                            data-credit="${unUtilisateur.getCredit()}"
                                                            onchange="disabledButton()"
                                                            value="${lastEnchere == null ? unArticle.getMiseAPrix() : (lastEnchere.getMontantEnchere() + 5)}">
                                                    <input type="text" hidden name="idArticle" value="${unArticle.getNoArticle()}">
                                                    <input type="text" hidden name="dernierUser" value="${lastEnchere == null ? 0 : lastEnchere.getUnUtilisateur().getNoUtilisateur()}">
                                                    <input type="text" hidden name="derniereOffre" value="${lastEnchere == null ? 0 : lastEnchere.getMontantEnchere()}">
                                                </div>
                                                <div class="col-sm-6 text-center text-md-left text-md-right mt-3">
                                                    <button class="btn btn-primary btn-rounded" id="valideEnchere" type="submit">
                                                        <i class="fas fa-cart-plus mr-2" aria-hidden="true"></i> Enchérir</button>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="row" id="erreur-credit">
                                            <span class="text-danger"><i class="fas fa-exclamation-circle"></i>Vous n'avez pas assez de crédit pour faire une enchère</span>
                                        </div>
                                    </div>
                                </section>
                            </c:if>
                            <c:if test="${unUtilisateur.getNoUtilisateur() == unArticle.getUnUtilisateur().getNoUtilisateur() && unArticle.getEtatVente().equals('en_cours')}">
                                <!-- Add to Cart -->
                                <section>
                                    <div class="mt-5">
                                        <p class="grey-text">Les enchérisseurs</p>
                                        <div class="text-center text-md-left scroll_vertical">
                                            <ul class="list-group" id="listEncherisseurs">
                                                <c:forEach items="${unArticle.getLesEncheres()}" var="enchere" varStatus="vs">
                                                    <li class="list-group-item d-flex justify-content-between align-items-center">
                                                        ${enchere.getUnUtilisateur().getPseudo()}
                                                        <c:if test="${vs.first}">
                                                            <span class="badge badge-danger badge-pill">${enchere.getMontantEnchere()} pts</span>
                                                        </c:if>
                                                        <c:if test="${!vs.first}">
                                                            <span class="badge badge-light badge-pill">${enchere.getMontantEnchere()} pts</span>
                                                        </c:if>
                                                    </li>
                                                </c:forEach>
                                            </ul>
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
        </c:if>
        <c:if test="${empty unArticle}">
            <div class="container">
                <div class="row">
                    <div class="mt-5 text-center col-sm-12">
                        <img src="<%=request.getContextPath()%>/img/page-not-found.png" alt="non trouvé" class="rounded img-fluid">
                        <h4 class="mt-3"> Oups... Aucun article trouvé ! Revenir à la <a href="<%=request.getContextPath()%>/">page d'accueil</a></h4>
                    </div>
                </div>
            </div>
        </c:if>
        <%@ include file="../template/footer.jsp" %>
    </body>
</html>
