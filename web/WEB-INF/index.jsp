<%-- Created by IntelliJ IDEA. --%>
<%@ page import="java.util.List" %>
<%@ page import="com.eni.encheres.bo.Categorie" %>
<%@ page import="com.eni.encheres.bo.Categorie" %>
<%@ page isELIgnored="false"%>
<%@ page import="com.eni.encheres.bo.ArticleVendu" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
  <head>
    <%@ include file="template/head.jsp" %>
    <title>ENI - Enchères</title>
  </head>
  <body>

    <c:if test="${!empty requestScope.lesCategories}">
      <c:set var="lesCategories" value="${requestScope.lesCategories}" scope="page" />
    </c:if>

    <c:if test="${!empty requestScope.lesArticles}">
      <c:set var="lesArticles" value="${requestScope.lesArticles}" scope="page" />
    </c:if>

    <c:if test="${!empty requestScope.nbSides}">
      <c:set var="nbSides" value="${requestScope.nbSides}" scope="page" />
    </c:if>

    <c:if test="${!empty param.idCategorie}">
      <c:set var="idCategorie" value="${param.idCategorie}" scope="page" />
    </c:if>

    <c:if test="${!empty param.txtFiltreNom}">
      <c:set var="nomArticle" value="${param.txtFiltreNom}" scope="page" />
    </c:if>

    <c:if test="${!empty param.groupeRadio}">
      <c:if test="${param.groupeRadio.equals('achats')}">
        <c:set var="radioAchats" value="${param.groupeRadio}" scope="page" />
      </c:if>
      <c:if test="${param.groupeRadio.equals('ventes')}">
        <c:set var="radioVentes" value="${param.groupeRadio}" scope="page" />
      </c:if>
    </c:if>

    <c:if test="${!empty param.encheres_ouvertes}">
      <c:set var="encheres_ouvertes" value="${param.encheres_ouvertes}" scope="page" />
    </c:if>

    <c:if test="${!empty param.encheres_en_cours}">
      <c:set var="encheres_en_cours" value="${param.encheres_en_cours}" scope="page" />
    </c:if>

    <c:if test="${!empty param.encheres_remportees && null != param}">
      <c:set var="encheres_remportees" value="${param.encheres_remportees}" scope="page" />
    </c:if>

    <c:if test="${!empty param.ventes_en_cours}">
      <c:set var="ventes_en_cours" value="${param.ventes_en_cours}" scope="page" />
    </c:if>

    <c:if test="${!empty param.ventes_non_debutees}">
      <c:set var="ventes_non_debutees" value="${param.ventes_non_debutees}" scope="page" />
    </c:if>

    <c:if test="${!empty param.ventes_terminees}">
      <c:set var="ventes_terminees" value="${param.ventes_terminees}" scope="page" />
    </c:if>

    <%@ include file="template/header.jsp" %>

    <h1 class="text-center"> Liste des enchères </h1>
    <form method="get" action=${pageContext.servletContext.contextPath}>
      <div class="container z-depth-1 p-5 my-5">
        <section>
          <!-- Filter Area -->
          <div class="row align-items-center">
            <div class="col-md-4">
                <label class="mdb-main-label grey-text">Filtres</label>
                <input class="form-control" id="txtFiltreNom" name="txtFiltreNom" placeholder="Nom de l'article" value="${nomArticle}"/>
            </div>
            <c:if test="${!empty unUtilisateur}">

            <div class="col-md-4">
              <!-- Material unchecked -->
              <div class="form-check">
                <input type="radio" class="form-check-input" id="radioAchats" name="groupeRadio" value="achats" <c:if test="${!empty radioAchats}">checked</c:if>/>
                <label class="form-check-label" for="radioAchats">Achats</label>

                <!-- Material inline 1 -->
                <div class="form-check">
                  <input type="checkbox" class="form-check-input checkAchats" id="checkEncheresOuvertes" name="encheres_ouvertes" <c:if test="${(empty radioAchats && empty radioVentes) || !empty radioVentes}">disabled</c:if> <c:if test="${!empty encheres_ouvertes}">checked</c:if>/>
                  <label class="form-check-label checkAchats" for="checkEncheresOuvertes">Enchères ouvertes</label>
                </div>

                <!-- Material inline 2 -->
                <div class="form-check">
                  <input type="checkbox" class="form-check-input checkAchats" id="checkEncheresEnCours" name="encheres_en_cours" <c:if test="${(empty radioAchats && empty radioVentes) || !empty radioVentes}">disabled</c:if> <c:if test="${!empty encheres_en_cours}">checked</c:if>/>
                  <label class="form-check-label checkAchats" for="checkEncheresEnCours">Mes enchères en cours</label>
                </div>

                <!-- Material inline 3 -->
                <div class="form-check">
                  <input type="checkbox" class="form-check-input checkAchats" id="checkEncheresRemportees" name="encheres_remportees" <c:if test="${ (empty radioAchats && empty radioVentes) || !empty radioVentes}">disabled</c:if> <c:if test="${!empty encheres_remportees}">checked</c:if> />
                  <label class="form-check-label checkAchats" for="checkEncheresRemportees">Mes enchères remportées</label>
                </div>

              </div>
            </div>
            <div class="col-md-4">
              <!-- Material checked -->
              <div class="form-check">
                <input type="radio" class="form-check-input" id="radioVentes" name="groupeRadio"  value="ventes" <c:if test="${!empty radioVentes}">checked</c:if>/>
                <label class="form-check-label" for="radioVentes">Mes ventes</label>

                <!-- Material inline 1 -->
                <div class="form-check">
                  <input type="checkbox" class="form-check-input checkVentes" id="checkVentesEnCours" name="ventes_en_cours" <c:if test="${(empty radioAchats && empty radioVentes) || !empty radioAchats}">disabled</c:if> <c:if test="${!empty ventes_en_cours}">checked</c:if> />
                  <label class="form-check-label checkVentes" for="checkVentesEnCours">Mes ventes en cours</label>
                </div>

                <!-- Material inline 2 -->
                <div class="form-check">
                  <input type="checkbox" class="form-check-input checkVentes" id="checkVentesNonDebutees" name="ventes_non_debutees" <c:if test="${(empty radioAchats && empty radioVentes) || !empty radioAchats}">disabled</c:if> <c:if test="${!empty ventes_non_debutees}">checked</c:if> />
                  <label class="form-check-label checkVentes" for="checkVentesNonDebutees">Mes ventes non débutées</label>
                </div>

                <!-- Material inline 3 -->
                <div class="form-check">
                  <input type="checkbox" class="form-check-input checkVentes" id="checkVentesTerminees" name="ventes_terminees" <c:if test="${(empty radioAchats && empty radioVentes) || !empty radioAchats}">disabled</c:if> <c:if test="${!empty ventes_terminees}">checked</c:if> />
                  <label class="form-check-label checkVentes" for="checkVentesTerminees">Ventes terminées</label>
                </div>

              </div>
            </div>
            </c:if>
          </div>
          <br>
          <!-- Filter Area -->
          <div class="row align-items-center">
            <div class="col-md-4">
              <!-- Sort by -->
              <select class="mdb-select grey-text md-form" id="idCategorie" name="idCategorie">
                  <c:if test="${empty idCategorie || idCategorie == ''}">
                      <option value="">Toutes</option>
                  </c:if>
                  <c:if test="${!empty idCategorie && idCategorie != ''}">
                      <option value="" selected>Toutes</option>
                  </c:if>
                <c:forEach items="${lesCategories}" var="categorie">
                  <c:if test="${String.valueOf(categorie.getNoCategorie()).equals(idCategorie)}">
                    <option value="${categorie.getNoCategorie()}" selected>${categorie.getLibelle()}</option>
                  </c:if>
                  <c:if test="${!String.valueOf(categorie.getNoCategorie()).equals(idCategorie)}">
                    <option value="${categorie.getNoCategorie()}">${categorie.getLibelle()}</option>
                  </c:if>

                </c:forEach>
              </select>
              <label class="mdb-main-label">Catégories</label>
              <!-- Sort by -->
            </div>
            <div class="col-8 col-md-8 text-right">

              <a class="btn btn-default-color btn-rounded" href="<%=request.getContextPath()%>/">
                <i class="fas fa-times-circle mr-2" aria-hidden="true"></i>
                <strong>Effacer</strong>
              </a>

              <!-- View Switcher -->
              <button class="btn btn-primary btn-rounded" type="submit">
                <i class="fas fa-search mr-2" aria-hidden="true"></i>
                <strong>Rechercher</strong>
              </button>
              <!-- View Switcher -->
            </div>
          </div>
        </section>
      </div>
    </form>

    <div class="container mt-5  col-sm-10">
      <c:if test="${lesArticles.size() > 0 }">
        <!--Section: Content-->
        <section class="dark-grey-text text-center">
          <!-- Carousel Wrapper -->
          <div id="multi-item-example" class="carousel slide carousel-multi-item" data-ride="carousel">
            <!-- Controls -->
            <div class="controls-top">
              <a class="btn-floating primary-color waves-effect waves-light" href="#multi-item-example" data-slide="prev">
                <i class="fas fa-chevron-left"></i>
              </a>
              <a class="btn-floating primary-color waves-effect waves-light" href="#multi-item-example" data-slide="next">
                <i class="fas fa-chevron-right"></i>
              </a>
            </div>
            <!-- Controls -->
            <!-- Indicators -->
            <ol class="carousel-indicators mb-n3">
              <c:forEach begin="0" end="${nbSides-1}" varStatus="vs">
                <c:if test="${vs.first}">
                  <li class="primary-color active" data-target="#multi-item-example" data-slide-to="${vs.index}"></li>
                </c:if>
                <c:if test="${!vs.first}">
                  <li class="primary-color" data-target="#multi-item-example" data-slide-to="${vs.index}"></li>
                </c:if>
              </c:forEach>
            </ol>
            <!-- Indicators -->
            <!-- Slides -->
            <div class="carousel-inner" role="listbox">
                <c:set var="currentIndex" value="0" scope="page" />
              <c:forEach begin="0" end="${nbSides-1}" varStatus="vs2">
                  <c:set var="startIndex" value="${currentIndex}" scope="page" />
                <c:if test="${vs2.first}">
                  <!-- First slide -->
                  <div class="carousel-item active">
                </c:if>
                <c:if test="${!vs2.first}">
                  <!-- Other slide -->
                  <div class="carousel-item">
                </c:if>
                    <c:forEach items="${lesArticles}" begin="${startIndex}" end="${startIndex +5}" var="article" varStatus="vs3">
                      <c:set var="currentIndex" value="${currentIndex+1}"/>
                      <!-- Card -->
                      <c:if test="${vs3.first}">
                        <div class="col-md-2 mb-2">
                      </c:if>
                      <c:if test="${!vs3.first}">
                        <div class="col-md-2 mb-2 clearfix d-none d-md-block">
                      </c:if>

                      <div class="card card-cascade narrower card-ecommerce">
                        <!-- Card image -->
                        <div class="view view-cascade overlay">
                          <img src=${pageContext.servletContext.contextPath}"/img/articles/${article.getNomImage()}" class="card-img-top"
                               alt="${article.getNomImage()}">
                            <div class="mask rgba-white-slight"></div>
                        </div>
                        <!-- Card image -->
                        <!-- Card content -->
                        <div class="card-body card-body-cascade text-center">
                          <c:if test="${article.getEtatVente().equals('en_cours')}">
                            <span class="badge badge-success product mb-4 ml-xl-0 ml-4">EN COURS</span>
                          </c:if>
                          <c:if test="${article.getEtatVente().equals('non_debutee')}">
                            <span class="badge badge-warning product mb-4 ml-xl-0 ml-4">NON DÉBUTÉE</span>
                          </c:if>
                          <c:if test="${article.getEtatVente().equals('terminee')}">
                            <span class="badge badge-danger product mb-4 ml-xl-0 ml-4">TERMINÉE</span>
                          </c:if>
                          <!-- Category & Title -->
                          <p class="text-muted">
                            <h5>${article.getUneCategorie().getLibelle()}</h5>
                          </p>
                          <h4 class="card-title my-4">
                            <strong>
                              <c:if test="${unUtilisateur == null }">
                                <a href="<%=request.getContextPath()%>/seConnecter">${article.getNomArticle()}</a>
                              </c:if>
                              <c:if test="${unUtilisateur != null }">
                                <a href="<%=request.getContextPath()%>/details_enchere?no_article=${article.getNoArticle()}">${article.getNomArticle()}</a>
                              </c:if>
                            </strong>
                          </h4>
                          <!-- Description -->
                          <p class="card-text">
                            <c:if test="${unUtilisateur == null }">
                              <b>${article.getUnUtilisateur().getPseudo()}</b>
                            </c:if>
                            <c:if test="${unUtilisateur != null }">
                              <a class="text-danger" href="<%=request.getContextPath()%>/profil/${article.getUnUtilisateur().getNoUtilisateur()}"><b>${article.getUnUtilisateur().getPseudo()}</b></a>
                            </c:if>

                          </p>
                          <!-- Card footer -->
                          <div class="card-footer px-1">
                            <span class="float-left">${article.getMiseAPrix()}pts</span>
                            <fmt:parseDate  value="${article.getDateFinEncheres()}"  type="date" pattern="yyyy-MM-dd" var="parsedDate"/>
                            <span class="float-right"><fmt:formatDate pattern="dd/MM/YYYY" value="${parsedDate}"/></span>
                          </div>
                        </div>
                        <!-- Card content -->
                      </div>
                      <!-- Card -->
                      </div>
                    </c:forEach>


                  </div>
                  </c:forEach>

                  </div>
                <!-- Slides -->
          </div>
          <!-- Carousel Wrapper -->

        </section>
        <!--Section: Content-->
      </c:if>
    </div>
    <div class="container mt-5">
      <c:if test="${lesArticles.size() == 0 }">
        <h5> Oups, il n'y a aucune enchère qui correspond à votre recherche ! </h5>
      </c:if>
    </div>

    <script>
      // Material Select Initialization
      $(document).ready(function() {
        $('.mdb-select').materialSelect();
      });

      $('input[type=radio][name=groupeRadio]').change(function () {
        switch ($(this).val()) {
          case 'ventes':
            $('.checkAchats').prop("checked", false);
            $('.checkAchats').attr("disabled", true);
            $('.checkVentes').attr("disabled", false);
            break;
          case 'achats':
            $('.checkVentes').prop("checked", false);
            $('.checkVentes').attr("disabled", true);
            $('.checkAchats').attr("disabled", false);
            break;
        }
      });
    </script>
  </body>
</html>