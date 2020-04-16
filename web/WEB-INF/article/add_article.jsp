<%@ page import="com.eni.encheres.bo.Utilisateur" %>
<%@ page import="java.util.List" %>
<%@ page import="com.eni.encheres.bo.Categorie" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <jsp:include page="../template/head.jsp"/>
        <script src="<%= request.getContextPath()%>/script/addArticle.js"></script>
        <title>Ajouter un article</title>
    </head>
    <body>
        <jsp:include page="../template/header.jsp"/>
        <div class="container my-5 py-5 z-depth-1">
            <section class="text-center">
                <form class="text-center needs-validation" novalidate action="<%= request.getContextPath() %>/articles/add" method="post" name="form">
                    <div class="row">
                        <div class="col-md-4">
                            <img src="<%= request.getContextPath() %>/img/articles/no-image.png" class="img-thumbnail" id="img-preview">
                            <div class="file-field">
                                <div class="btn btn-primary btn-rounded btn-upload">
                                    <span>Photo</span>
                                    <input required type="file" accept="image/*" id="photo" name="photo" enctype="multipart/form-data">
                                </div>
                                <div>
                                    <input required type="text" class="form-control" hidden id="photoB64" name="photoB64">
                                    <div class="invalid-feedback">Veuillez chosir une photo.</div>
                                </div>
                                <div>
                                    <input required type="number" hidden min="0" value="0" max="2000000" id="photoSize" name="photoSize" class="form-control">
                                    <div class="invalid-feedback">La taille de la photo est trop importante.</div>
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
                                <c:if test="${!empty requestScope.categories}">
                                    <c:set var="categories" value="${requestScope.categories}" scope="page" />
                                </c:if>
                                <c:forEach items="${categories}" var="categorie" varStatus="vs">
                                    <option value="${categorie.noCategorie}">${categorie.libelle}</option>
                                </c:forEach>
                            </select>
                            <div class="form-group">
                                <input required placeholder="Prix" type="number" min="5" step="5" value="150" id="prix" name="prix" class="form-control">
                            </div>
                            <div class="form-group">
                                <input required placeholder="Début de l'enchère" type="text" id="debut" class="form-control datepicker" name="debut">
                            </div>
                            <div class="form-group">
                                <input required placeholder="Fin de l'enchère" type="text" id="fin" class="form-control datepicker" name="fin">
                            </div>
                            <div class="card card-cascade narrower" id="card-retrait">
                                <div class="view view-cascade gradient-card-header blue-gradient">
                                    <h2 class="card-header-title">Retrait</h2>
                                </div>
                                <div class="card-body card-body-cascade text-center">
                                    <input required maxlength="30" type="text" id="rue" name="rue" class="form-control mb-4" placeholder="Rue" value="${unUtilisateur.getRue()}">
                                    <input
                                            required type="text"
                                            maxlength="5"
                                            minlength="5"
                                            pattern="^(([0-8][0-9])|(9[0-5])|(2[ab]))[0-9]{3}$"
                                            id="cp" class="form-control mb-4"
                                            placeholder="Code postal"
                                            value="${unUtilisateur.getCodePostal()}"
                                            name="cp">
                                    <input required maxlength="30" type="text" id="ville" name="ville" class="form-control mb-4" placeholder="Ville" value="${unUtilisateur.getVille()}">
                                </div>
                            </div>
                            <div class="row m-4">
                                <div class="col-md-5"><button class="btn btn-primary btn-rounded" type="submit">ENREGISTRER</button></div>
                                <div class="col-md-2"></div>
                                <div class="col-md-5"><a href="<%=request.getContextPath()%>" class="btn btn-outline-primary btn-rounded">ANNULER</a></div>
                            </div>
                        </div>
                    </div>
                </form>
            </section>
        </div>
    </body>
</html>
