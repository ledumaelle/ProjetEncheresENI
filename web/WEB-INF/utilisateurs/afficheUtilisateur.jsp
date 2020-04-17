<%@ page import="com.eni.encheres.bo.Enchere" %>
<%@ page import="com.eni.encheres.bo.ArticleVendu" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <%@ include file="../template/head.jsp" %>
        <title>Profil</title>
    </head>

    <body>
    <jsp:include page="../template/header.jsp"/>



        <c:set var="user" value="${requestScope.userAffiche}" scope="page" />



<div class="container">
    <div class="row">
        <div class="col-12">
            <div class="card">

                <div class="card-body">

                    <div class="row">
                        <div class="col-12">

                            <div class="tab-content ml-1" id="myTabContent">
                                <div class="tab-pane fade show active" id="basicInfo" role="tabpanel" aria-labelledby="basicInfo-tab">


                                    <div class="row">
                                        <div class="col-sm-3 col-md-2 col-5">
                                            <label style="font-weight:bold;">Pseudo</label>
                                        </div>
                                        <div class="col-md-8 col-6">
                                            <c:out value="${user.pseudo}" />
                                        </div>
                                    </div>
                                    <hr />

                                    <div class="row">
                                        <div class="col-sm-3 col-md-2 col-5">
                                            <label style="font-weight:bold;">Nom</label>
                                        </div>
                                        <div class="col-md-8 col-6">
                                            <c:out value="${user.nom}" />
                                        </div>
                                    </div>
                                    <hr />


                                    <div class="row">
                                        <div class="col-sm-3 col-md-2 col-5">
                                            <label style="font-weight:bold;">Prénom</label>
                                        </div>
                                        <div class="col-md-8 col-6">
                                            <c:out value="${user.prenom}" />
                                        </div>
                                    </div>
                                    <hr />
                                    <div class="row">
                                        <div class="col-sm-3 col-md-2 col-5">
                                            <label style="font-weight:bold;">Email</label>
                                        </div>
                                        <div class="col-md-8 col-6">
                                            <c:out value="${user.email}" />
                                        </div>
                                    </div>
                                    <hr />
                                    <div class="row">
                                        <div class="col-sm-3 col-md-2 col-5">
                                            <label style="font-weight:bold;">Téléphone</label>
                                        </div>
                                        <div class="col-md-8 col-6">
                                            <c:out value="${user.telephone}" />
                                        </div>
                                    </div>
                                    <hr />
                                    <div class="row">
                                        <div class="col-sm-3 col-md-2 col-5">
                                            <label style="font-weight:bold;">Rue</label>
                                        </div>
                                        <div class="col-md-8 col-6">
                                            <c:out value="${user.rue}" />
                                        </div>
                                    </div>
                                    <hr />
                                    <div class="row">
                                        <div class="col-sm-3 col-md-2 col-5">
                                            <label style="font-weight:bold;">Code postal</label>
                                        </div>
                                        <div class="col-md-8 col-6">
                                            <c:out value="${user.codePostal}" />
                                        </div>
                                    </div>
                                    <hr />
                                    <div class="row">
                                        <div class="col-sm-3 col-md-2 col-5">
                                            <label style="font-weight:bold;">Ville</label>
                                        </div>
                                        <div class="col-md-8 col-6">
                                            <c:out value="${user.ville}" />
                                        </div>
                                    </div>


                                </div>

                            </div>
                        </div>
                    </div>
                    <c:if test="${unUtilisateur==user}">
                        <button class="btn btn-secondary-color btn-rounded" onclick="window.location.href = '${pageContext.servletContext.contextPath}/editUser/${user.noUtilisateur}';">
                            <i class="fas fa-edit mr-2" aria-hidden="true"></i> Modifier</button>
                        <button class="btn btn-secondary-red btn-rounded" onclick="deleteUser(${user.noUtilisateur})">
                            <i class="fas fa-trash mr-2" aria-hidden="true"></i> Supprimer</button>
                    </c:if>


                </div>

            </div>
        </div>
    </div>
</div>



</body>

<script>




    function deleteUser(id) {

        var result = confirm("Etes vous sûre de vouloir supprimer votre compte ?");
        if (result == true) {
           window.location.href='${pageContext.servletContext.contextPath}/deleteUser/${user.noUtilisateur}';
        }

    }
</script>

</html>

