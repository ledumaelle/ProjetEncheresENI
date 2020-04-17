<%@ page import="com.eni.encheres.bo.Enchere" %>
<%@ page import="com.eni.encheres.bo.ArticleVendu" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <%@ include file="../template/head.jsp" %>
    <title>Modifier utilisateur</title>

</head>

<body>
<jsp:include page="../template/header.jsp"/>



<c:set var="user" value="${requestScope.userEdit}" scope="page" />

<body>



<nav class="navbar navbar-expand-lg navbar-light bg-light">


    <div class="navbar-collapse" id="navbarSupportedContent">
        <ul class="nav nav-tabs">
            <li class="nav-item ${requestScope.homeNavActive}">
                <a class="nav-link ${requestScope.homeNavActive}" href="#home" data-toggle="tab" role="tab" >Mes infos personnelles <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item ${requestScope.mdpNavActive}">
                <a class="nav-link ${requestScope.mdpNavActive}" href="#profile" data-toggle="tab" role="tab" >Mot de passe</a>
            </li>

        </ul>
    </div>
</nav>


    <div class="col-sm-4">
    <div id="myTabContent" class="tab-content">
        <div class="tab-pane ${requestScope.homeActive}" id="home">
            <p id="message" class="text-success"><c:out value="${requestScope.message}" /></p>
            <form id="tab" method="post" action="${pageContext.servletContext.contextPath}/editUser/${user.noUtilisateur}/1">
                <div class="form-group">

                    <div class="col-xs-6">
                        <label for="pseudo"><h4>Pseudo</h4></label>
                        <input type="text" class="form-control" name="pseudo" id="pseudo" placeholder="Pseudo" required value="<c:out value="${user.pseudo}" />" >
                    </div>
                </div>
                <div class="form-group">

                    <div class="col-xs-6">
                        <label for="nom"><h4>Nom</h4></label>
                        <input type="text" class="form-control" name="nom" id="nom" placeholder="Nom" required value="<c:out value="${user.nom}" />" >
                    </div>
                </div>
                <div class="form-group">

                    <div class="col-xs-6">
                        <label for="prenom"><h4>Prénom</h4></label>
                        <input type="text" class="form-control" name="prenom" id="prenom" placeholder="Prénom" required value="<c:out value="${user.prenom}" />">
                    </div>
                </div>

                <div class="form-group">

                    <div class="col-xs-6">
                        <label for="email"><h4>Email</h4></label>
                        <input type="email" class="form-control" name="email" id="email" placeholder="email" required value="<c:out value="${user.email}" />">
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-xs-6">
                        <label for="telephone"><h4>Téléphone</h4></label>
                        <input type="text" class="form-control" name="telephone" id="telephone" placeholder="telephone" value="<c:out value="${user.telephone}" />">
                    </div>
                </div>
                <div class="form-group">

                    <div class="col-xs-6">
                        <label for="rue"><h4>Rue</h4></label>
                        <input type="text" class="form-control" name="rue" id="rue" placeholder="rue" required value="<c:out value="${user.rue}" />">
                    </div>
                </div>
                <div class="form-group">

                    <div class="col-xs-6">
                        <label for="codePostal"><h4>Code Postal</h4></label>
                        <input type="text" class="form-control" name="codePostal" id="codePostal" placeholder="Code Postal" required value="<c:out value="${user.codePostal}" />">
                    </div>
                </div>
                <div class="form-group">

                    <div class="col-xs-6">
                        <label for="ville"><h4>Ville</h4></label>
                        <input type="text" class="form-control" name="ville" id="ville" placeholder="Ville" required value="<c:out value="${user.ville}" />">
                    </div>
                </div>

                <div>
                    <button class="btn btn-primary">Enregistré</button>
                    <button class="btn btn-dark hBack" type="button" onclick="window.location.href = '${pageContext.servletContext.contextPath}/utilisateur/${user.noUtilisateur}';" >Annuler</button>
                </div>
            </form>

        </div>
        <div class="tab-pane ${requestScope.mdpActive}" id="profile">
            <p id="message_succes" class="text-success"><c:out value="${requestScope.messageSucces}" /></p>
            <p id="message_error" class="text-danger"><c:out value="${requestScope.messageErreur}" /></p>
            <form id="tab2" method="post" action="${pageContext.servletContext.contextPath}/editUser/${user.noUtilisateur}/2">

                <div class="form-group">

                    <div class="col-xs-6">
                        <label>Mots de passe actuel</label>
                        <input type="password" class="form-control" name="old" id="old" required>
                    </div>
                </div>

                <div class="form-group">

                    <div class="col-xs-6">
                        <label>Nouveau mots de passe</label>
                        <input type="password" class="form-control" name="mdp" id="mdp" required>
                    </div>
                </div>

                <div class="form-group">

                    <div class="col-xs-6">
                        <label>Confirmation</label>
                        <input type="password" class="form-control" name="confirm" id="confirm" required>
                    </div>
                </div>
                <button class="btn btn-primary">Valider</button>
                <button class="btn btn-dark hBack" type="button" onclick="window.location.href = '${pageContext.servletContext.contextPath}/utilisateur/${user.noUtilisateur}';" >Annuler</button>

            </form>

        </div>
    </div>
</body>

<script>




    var password = document.getElementById("mdp")
        , confirm_password = document.getElementById("confirm");

    function validatePassword(){
        if(password.value != confirm_password.value) {
            confirm_password.setCustomValidity("Mot de passe pas identique");
        } else {
            confirm_password.setCustomValidity('');
        }
    }

    password.onchange = validatePassword;
    confirm_password.onkeyup = validatePassword;
</script>
</html>
