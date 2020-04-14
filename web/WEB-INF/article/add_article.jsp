<%@ page import="com.eni.encheres.bo.Utilisateur" %>
<%@ page import="java.util.List" %>
<%@ page import="com.eni.encheres.bo.Categorie" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%!
    private List<Categorie> categories;
    private Utilisateur utilisateur;
%>
<html>
    <head>
        <jsp:include page="../template/head.jsp"/>
        <title>Ajouter un article</title>
    </head>
    <body>
        <jsp:include page="../template/header.jsp"/>
        <div class="m-4">
            <h5 class="card-header info-color white-text text-center py-4">
                <strong>NOUVELLE VENTE</strong>
            </h5>
            <form class="text-center border border-light p-5" action="${pageContext.servletContext.contextPath}/articles/add" method="post" name="form">
                <div class="row">
                    <div class="col-md-4">
                        <img src="${pageContext.servletContext.contextPath}/img/articles/no-image.png" class="img-thumbnail" id="img-preview">
                        <div class="file-field">
                            <div class="btn btn-info btn-upload">
                                <span>Photo</span>
                                <input required type="file" accept="image/*" id="photo" name="photo" enctype="multipart/form-data">
                                <input required type="text" hidden id="photoB64" name="photoB64">
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <input required maxlength="30" type="text" id="article" name="article" class="form-control mb-4" placeholder="Article">
                        <div class="form-group">
                            <textarea required maxlength="300" class="form-control rounded-0" id="description" name="description" rows="3" placeholder="Description"></textarea>
                        </div>
                        <select required class="browser-default custom-select mb-4" name="categorie">
                            <option value="" disabled>Chosir une catégorie</option>
                            <% categories = (List<Categorie>) request.getAttribute("categories"); %>
                            <c:forEach items="${categories}" var="categorie" varStatus="vs">
                                <option value="${categorie.noCategorie}">${categorie.libelle}</option>
                            </c:forEach>
                        </select>
                        <div class="form-group">
                            <input required placeholder="Prix" type="number" min="1" value="150" id="prix" name="prix" class="form-control">
                        </div>
                        <div class="form-group">
                            <input placeholder="Début de l'enchère" type="text" id="debut" class="form-control datepicker" name="debut">
                        </div>
                        <div class="form-group">
                            <input placeholder="Fin de l'enchère" type="text" id="fin" class="form-control datepicker" name="fin">
                        </div>
                        <ul class="list-group">
                            <li class="list-group-item info-color white-text text-center">RETRAIT</li>
                            <li class="list-group-item">
                                <% utilisateur = (Utilisateur) session.getAttribute("unUtilisateur"); %>
                                <input required maxlength="30" type="text" id="rue" class="form-control mb-4" placeholder="Rue" value="<%= utilisateur.getRue() %>">
                                <input
                                        required type="text"
                                        maxlength="5"
                                        minlength="5"
                                        pattern="^(([0-8][0-9])|(9[0-5])|(2[ab]))[0-9]{3}$"
                                        id="cp" class="form-control mb-4"
                                        placeholder="Code postal"
                                        value="<%= utilisateur.getCodePostal() %>">
                                <input required maxlength="30" type="text" id="ville" class="form-control mb-4" placeholder="Ville" value="<%= utilisateur.getVille() %>">
                            </li>
                        </ul>
                        <div class="row m-4">
                            <div class="col-md-5"><button class="btn btn-info btn-block" type="submit" onclick="submitForm()">ENREGISTRER</button></div>
                            <div class="col-md-2"></div>
                            <div class="col-md-5"><a href="#" class="btn btn-outline-info btn-block">ANNULER</a></div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <jsp:include page="../template/footer.jsp"/>
    </body>
</html>
