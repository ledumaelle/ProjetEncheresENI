<%-- Created by IntelliJ IDEA. --%>
<%@ page import="java.util.List" %>
<%@ page import="com.eni.encheres.bo.Categorie" %>
<%@ page isELIgnored="false"%>
<%@ page import="com.eni.encheres.bo.ArticleVendu" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%! private List<Categorie> lesCategories; %>
<%! private String idCategorie; %>
<%! private String nomArticle; %>
<%! private List<ArticleVendu> lesArticles; %>
<%! private int nbSides; %>
<html>
  <head>
    <%@ include file="template/head.jsp" %>
    <title>ENI - Enchères</title>
  </head>
  <body>
    <% lesCategories = (List<Categorie>) request.getAttribute("lesCategories"); %>
    <% lesArticles = (List<ArticleVendu>) request.getAttribute("lesArticles"); %>
    <% nbSides = (int) request.getAttribute("nbSides"); %>
    <% if(request.getAttribute("idCategorie") == null) { idCategorie="" ;} else {idCategorie = (String) request.getAttribute("idCategorie");} %>
    <% if(request.getAttribute("txtFiltreNom") == null) { nomArticle="" ;} else {nomArticle = (String) request.getAttribute("txtFiltreNom");} %>

    <%@ include file="template/header.jsp" %>

    <h1 class="text-center"> Liste des enchères </h1>
    <form method="get" action=<%=request.getContextPath()%>"/index.html">
      <div class="container z-depth-1 p-5 my-5">
        <section>
          <!-- Filter Area -->
          <div class="row align-items-center">
            <div class="col-md-4">
                <label class="mdb-main-label grey-text">Filtres</label>
                <input class="form-control" id="txtFiltreNom" name="txtFiltreNom" placeholder="Nom de l'article" value="<%=nomArticle%>"/>
            </div>
            <c:if test="${unUtilisateur != null }">

            <div class="col-md-4">
              <!-- Material unchecked -->
              <div class="form-check">
                <input type="radio" class="form-check-input" id="radioAchats" name="groupeRadio" value="achats" checked>
                <label class="form-check-label" for="radioAchats">Achats</label>

                <!-- Material inline 1 -->
                <div class="form-check">
                  <input type="checkbox" class="form-check-input checkAchats" id="checkEncheresOuvertes">
                  <label class="form-check-label checkAchats" for="checkEncheresOuvertes">Enchères ouvertes</label>
                </div>

                <!-- Material inline 2 -->
                <div class="form-check">
                  <input type="checkbox" class="form-check-input checkAchats" id="checkEncheresEnCours">
                  <label class="form-check-label checkAchats" for="checkEncheresEnCours">Mes enchères en cours</label>
                </div>

                <!-- Material inline 3 -->
                <div class="form-check">
                  <input type="checkbox" class="form-check-input checkAchats" id="checkEncheresRemportees">
                  <label class="form-check-label checkAchats" for="checkEncheresRemportees">Mes enchères remportées</label>
                </div>

              </div>
            </div>
            <div class="col-md-4">
              <!-- Material checked -->
              <div class="form-check">
                <input type="radio" class="form-check-input" id="radioVentes" name="groupeRadio"  value="ventes">
                <label class="form-check-label" for="radioVentes">Mes ventes</label>

                <!-- Material inline 1 -->
                <div class="form-check">
                  <input type="checkbox" class="form-check-input checkVentes" id="checkVentesEnCours" disabled>
                  <label class="form-check-label checkVentes" for="checkVentesEnCours">Mes ventes en cours</label>
                </div>

                <!-- Material inline 2 -->
                <div class="form-check">
                  <input type="checkbox" class="form-check-input checkVentes" id="checkVentesNonDebutees" disabled>
                  <label class="form-check-label checkVentes" for="checkVentesNonDebutees">Mes ventes non débutées</label>
                </div>

                <!-- Material inline 3 -->
                <div class="form-check">
                  <input type="checkbox" class="form-check-input checkVentes" id="checkVentesTerminees" disabled>
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

              <a class="btn btn-default-color btn-rounded" href=<%=request.getContextPath()%>"/index.html">
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

    <div class="container mt-5">
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
                    <c:forEach items="${lesArticles}" begin="${startIndex}" end="${startIndex +2}" var="article" varStatus="vs3">
                      <c:set var="currentIndex" value="${currentIndex+1}"/>
                      <!-- Card -->
                      <c:if test="${vs3.first}">
                        <div class="col-md-4 mb-2">
                      </c:if>
                      <c:if test="${!vs3.first}">
                        <div class="col-md-4 mb-2 clearfix d-none d-md-block">
                      </c:if>

                      <div class="card card-cascade narrower card-ecommerce">
                        <!-- Card image -->
                        <div class="view view-cascade overlay">
                          <img src=<%=request.getContextPath()%>"/img/articles/${article.getNomImage()}" class="card-img-top"
                               alt="${article.getNomImage()}">

                            <div class="mask rgba-white-slight"></div>

                        </div>
                        <!-- Card image -->
                        <!-- Card content -->
                        <div class="card-body card-body-cascade text-center">
                          <!-- Category & Title -->
                          <p class="text-muted">
                            <h5>${article.getUneCategorie().getLibelle()}</h5>
                          </p>
                          <h4 class="card-title my-4">
                            <strong>
                              <c:if test="${unUtilisateur == null }">
                                <a href=<%=request.getContextPath()%>"/seConnecter">${article.getNomArticle()}</a>
                              </c:if>
                              <c:if test="${unUtilisateur != null }">
                                <a href=<%=request.getContextPath()%>"/details_enchere.html?no_article=${article.getNoArticle()}">${article.getNomArticle()}</a>
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
            $('.checkAchats').attr("disabled", true);
            $('.checkVentes').attr("disabled", false);
            break;
          case 'achats':
            $('.checkVentes').attr("disabled", true);
            $('.checkAchats').attr("disabled", false);
            break;
        }
      });
    </script>
  </body>
</html>