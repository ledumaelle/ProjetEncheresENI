<%@ page import="com.eni.encheres.bo.Enchere" %>
<%@ page import="com.eni.encheres.bo.ArticleVendu" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<%@ include file="../template/head.jsp" %>
        </head>

<body>
<jsp:include page="../template/header.jsp"/>

    <h1 class="text-center"> Détails utilisateur</h1>

    <c:set var="user" value="${requestScope.userAffiche}" scope="page" />
    <div class="container z-depth-1 p-5 my-5">
        <h3 class="h3-responsive text-center text-md-left mb-5 ml-xl-0 ml-4">Pseudo : <c:out value="${user.pseudo}" /></h3>
        <h3 class="h3-responsive text-center text-md-left mb-5 ml-xl-0 ml-4">Nom : <c:out value="${user.nom}" /></h3>
        <h3 class="h3-responsive text-center text-md-left mb-5 ml-xl-0 ml-4">Prénom : <c:out value="${user.prenom}" /></h3>
        <h3 class="h3-responsive text-center text-md-left mb-5 ml-xl-0 ml-4">Email : <c:out value="${user.email}" /></h3>
        <h3 class="h3-responsive text-center text-md-left mb-5 ml-xl-0 ml-4">Téléphone : <c:out value="${user.telephone}" /></h3>
        <h3 class="h3-responsive text-center text-md-left mb-5 ml-xl-0 ml-4">Rue : <c:out value="${user.rue}" /></h3>
        <h3 class="h3-responsive text-center text-md-left mb-5 ml-xl-0 ml-4">Code postal : <c:out value="${user.codePostal}" /></h3>
        <h3 class="h3-responsive text-center text-md-left mb-5 ml-xl-0 ml-4">Ville : <c:out value="${user.ville}" /></h3>
    </div>


</body>

</html>

