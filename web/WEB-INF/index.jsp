<%-- Created by IntelliJ IDEA. --%>
<%@ page import="java.util.List" %>
<%@ page import="com.eni.encheres.bo.Categorie" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<html>
  <head>
    <%@ include file="template/head.jsp" %>
    <title>ENI - Enchères</title>
  </head>
  <body>
    <%@ include file="template/header.jsp" %>
    <h1 class="text-center"> Liste des enchères </h1>
    <h1> Filtres </h1>
    <input class="form-control" name="txtFiltreNom"/>
    <label>Catégories</label>
    <select  class="form-control custom-select" id="categories" name="categories">
      <c:forEach items="${lesCategories}" var="categorie">
        <option value="${categorie.getNoCategorie()}">${categorie.getLibelle()}</option>
      </c:forEach>
    </select>
    <input class="form-control" name="txtRechercher" value="Rechercher"/>


    Page d'accueil !!

  </body>
</html>