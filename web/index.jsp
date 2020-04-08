<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
  <h1> Liste des enchères </h1>
  <h1> Filtres </h1>
  <input name="txtFiltreNom"/>
  <label>Catégories</label>
  <select  class="custom-select" id="categories" name="categories">
    <c:forEach items="${lesCategories}" var="categorie">
      <option value="${categorie.getNoCategorie()}">${categorie.getLibelle()}</option>
    </c:forEach>
  </select>
  <a href="ServletPageAccueil">Lien pour tester la servlet</a>
  Page d'accueil !!

  </body>
</html>