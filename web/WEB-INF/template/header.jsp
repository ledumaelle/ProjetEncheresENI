<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.eni.encheres.bo.Utilisateur"%>

<header>

    <c:if test="${!empty sessionScope.unUtilisateur}">
        <c:set var="unUtilisateur" value="${sessionScope.unUtilisateur}" scope="page" />
    </c:if>

    <!--Navbar -->
    <nav class="mb-1 navbar navbar-expand-lg navbar-dark info-color">
        <a class="navbar-brand" href="${pageContext.servletContext.contextPath}">ENI - Enchères</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent-4"
                aria-controls="navbarSupportedContent-4" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent-4">
            <ul class="navbar-nav ml-auto">
                <c:if test="${empty unUtilisateur}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.servletContext.contextPath}/login">
                            <i class="fas fa-sign-in-alt"></i> S'inscrire - Se connecter
                        </a>
                    </li>
                </c:if>
                <c:if test="${!empty unUtilisateur}">

                    <c:if test="${unUtilisateur.administrateur}">
                        <li class="nav-item" >
                            <a class="nav-link" href="${pageContext.servletContext.contextPath}/admin">
                                <i class="fas fa-user-cog"></i> Admin</a>
                        </li>
                    </c:if>

                    <li class="nav-item">
                        <a class="nav-link"><i class="fas fa-coins"></i> ${unUtilisateur.getCredit()} crédits</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.servletContext.contextPath}">
                            <i class="fas fa-balance-scale"></i> Enchères</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.servletContext.contextPath}/articles/add">
                            <i class="fas fa-shopping-cart"></i> Vendre un article</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.servletContext.contextPath}/utilisateur/${unUtilisateur.noUtilisateur}">
                            <i class="far fa-user"></i> Mon profil</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.servletContext.contextPath}/logout">
                            <i class="fas fa-sign-out-alt"></i> Déconnexion</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </nav>
    <!--/.Navbar -->
</header>