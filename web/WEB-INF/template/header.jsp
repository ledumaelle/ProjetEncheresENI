<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.eni.encheres.bo.Utilisateur"%>
<%! private Utilisateur unUtilisateur; %>
<header>
    <%
        if(session.getAttribute("unUtilisateur") != null)
        {
            unUtilisateur = (Utilisateur) session.getAttribute("unUtilisateur");
        }
    %>
    <!--Navbar -->
    <nav class="mb-1 navbar navbar-expand-lg navbar-dark info-color">
        <a class="navbar-brand" href="index.html">ENI - Enchères</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent-4"
                aria-controls="navbarSupportedContent-4" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent-4">
            <ul class="navbar-nav ml-auto">
                <c:if test="${unUtilisateur == null }">
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/seConnecter">
                            <i class="fas fa-sign-in-alt"></i> S'inscrire - Se connecter
                        </a>
                    </li>
                </c:if>
                <c:if test="${unUtilisateur != null }">
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/index.html">
                            <i class="fas fa-balance-scale"></i> Enchères</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<%= request.getContextPath() %>/articles/add">
                            <i class="fas fa-shopping-cart"></i> Vendre un article</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <i class="far fa-user"></i> Mon profil</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">
                            <i class="fas fa-sign-out-alt"></i> Déconnexion</a>
                    </li>
                </c:if>
            </ul>
        </div>
    </nav>
    <!--/.Navbar -->
</header>