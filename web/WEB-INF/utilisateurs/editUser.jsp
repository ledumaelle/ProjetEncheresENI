<%@ page import="com.eni.encheres.bo.Enchere" %>
<%@ page import="com.eni.encheres.bo.ArticleVendu" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <jsp:include page="../template/head.jsp"/>
        <script src="<%= request.getContextPath()%>/script/editUser.js"></script>
    </head>

    <body>
        <jsp:include page="../template/header.jsp"/>
        <c:set var="user" value="${requestScope.userEdit}" scope="page" />

        <div class="container my-5 py-5">
            <section class="text-center">
                <ul class="nav nav-tabs md-tabs blue-gradient" id="myTabMD" role="tablist">
                    <li class="nav-item">
                        <a class="nav-link ${requestScope.homeNavActive}" id="profil-tab-md" data-toggle="tab" href="#profil" role="tab" aria-controls="profil-md"
                           aria-selected="true">Mes infos personnelles</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link ${requestScope.mdpNavActive}" id="mdp-tab-md" data-toggle="tab" href="#mdp-tab" role="tab" aria-controls="mdp-md"
                           aria-selected="false">Mot de passe</a>
                    </li>
                </ul>
                <div class="tab-content card pt-5" id="myTabContentMD">
                    <div class="tab-pane fade ${requestScope.homeActive}" id="profil" role="tabpanel" aria-labelledby="profil-tab-md">
                        <p id="message" class="text-success"><c:out value="${requestScope.message}" /></p>
                        <form class="needs-validation" novalidate id="tab" method="post" action="${pageContext.servletContext.contextPath}/editUser/${user.noUtilisateur}/1">
                            <div class="form-group">
                                <div class="col-xs-6">
                                    <input type="text" class="form-control" name="pseudo" id="pseudo" placeholder="Pseudo" required value="<c:out value="${user.pseudo}" />" >
                                </div>
                            </div>
                            <div class="form-group">

                                <div class="col-xs-6">
                                    <input type="text" class="form-control" name="nom" id="nom" placeholder="Nom" required value="<c:out value="${user.nom}" />" >
                                </div>
                            </div>
                            <div class="form-group">

                                <div class="col-xs-6">
                                    <input type="text" class="form-control" name="prenom" id="prenom" placeholder="Prénom" required value="<c:out value="${user.prenom}" />">
                                </div>
                            </div>

                            <div class="form-group">

                                <div class="col-xs-6">
                                    <input type="email" class="form-control" name="email" id="email" placeholder="Mail" required value="<c:out value="${user.email}" />">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-xs-6">
                                    <input type="text" class="form-control" name="telephone" id="telephone" placeholder="Téléphone" required value="<c:out value="${user.telephone}" />">
                                </div>
                            </div>
                            <div class="form-group">

                                <div class="col-xs-6">
                                    <input type="text" class="form-control" name="rue" id="rue" placeholder="Rue" required value="<c:out value="${user.rue}" />">
                                </div>
                            </div>
                            <div class="form-group">

                                <div class="col-xs-6">
                                    <input type="text" class="form-control" name="codePostal" id="codePostal" maxlength="5" minlength="5" pattern="^(([0-8][0-9])|(9[0-5])|(2[ab]))[0-9]{3}$" placeholder="Code Postal" required value="<c:out value="${user.codePostal}" />">
                                </div>
                            </div>
                            <div class="form-group">

                                <div class="col-xs-6">
                                    <input type="text" class="form-control" name="ville" id="ville" placeholder="Ville" required value="<c:out value="${user.ville}" />">
                                </div>
                            </div>

                            <div>
                                <button class="btn btn-primary btn-rounded">Enregistrer</button>
                                <button class="btn btn-outline-primary btn-rounded" type="button" onclick="window.location.href = '${pageContext.servletContext.contextPath}/utilisateur/${user.noUtilisateur}';" >Annuler</button>
                            </div>
                        </form>
                    </div>
                    <div class="tab-pane fade ${requestScope.mdpActive}" id="mdp-tab" role="tabpanel" aria-labelledby="mdp-tab-md">
                        <p id="message_succes" class="text-success"><c:out value="${requestScope.messageSucces}" /></p>
                        <p id="message_error" class="text-danger"><c:out value="${requestScope.messageErreur}" /></p>
                        <form class="needs-validation" novalidate id="tab2" method="post" action="${pageContext.servletContext.contextPath}/editUser/${user.noUtilisateur}/2">

                            <div class="form-group">

                                <div class="col-xs-6">
                                    <input type="password" class="form-control" name="old" id="old" required placeholder="Ancien mot de passe">
                                </div>
                            </div>

                            <div class="form-group">

                                <div class="col-xs-6">
                                    <input type="password" class="form-control" name="mdp" id="mdp" required placeholder="Nouveau mot de passe">
                                </div>
                            </div>

                            <div class="form-group">

                                <div class="col-xs-6">
                                    <input type="password" class="form-control" name="confirm" id="confirm" required  placeholder="Confirmation mot de passe">
                                </div>
                            </div>
                            <button class="btn btn-primary btn-rounded">Enregistrer</button>
                            <button class="btn btn-outline-primary btn-rounded" type="button" onclick="window.location.href = '${pageContext.servletContext.contextPath}/utilisateur/${user.noUtilisateur}';" >Annuler</button>

                        </form>
                    </div>
                </div>
        </section>
        </div>
    </body>
</html>
