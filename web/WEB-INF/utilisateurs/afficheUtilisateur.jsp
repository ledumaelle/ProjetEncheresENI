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
        <div class="container mt-5 mb-5">
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
                                <div class="col-md-12 text-center text-md-right">
                                    <button class="btn btn-info btn-rounded" onclick="window.location.href = '${pageContext.servletContext.contextPath}/editUser/${user.noUtilisateur}';">
                                        <i class="fas fa-edit mr-2" aria-hidden="true"></i> Modifier</button>
                                    <c:set var="hasArticleOrEnchere" value="${requestScope.hasArticleOrEnchere}" scope="page" />

                                        <button class="btn btn-danger btn-rounded" data-toggle="modal" data-target="#modalDeleteUser"
                                            <c:if test="${hasArticleOrEnchere}">
                                                disabled
                                            </c:if>
                                        >
                                            <i class="fas fa-trash mr-2" aria-hidden="true"></i> Supprimer
                                        </button>
                                        <c:if test="${hasArticleOrEnchere}">
                                            <div class="row">
                                                <span class="text-danger"><i class="fas fa-exclamation-circle"></i>Vous avez des ventes ou des enchères, la suppression de votre compte est impossible pour le moment.</span>
                                            </div>
                                        </c:if>
                                </div>

                                <!-- Modal DELETE USER -->
                                <div class="modal fade" id="modalDeleteUser" tabindex="-1" role="dialog" aria-labelledby="modalDeleteUserlabel"
                                     aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Confirmation</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                Êtes vous sûre de vouloir supprimer votre compte ?
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary-color btn-rounded" data-dismiss="modal">ANNULER</button>
                                                <button type="button" class="btn btn-info btn-rounded" onclick="deleteUser(${user.noUtilisateur})">OUI</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>


                        </div>

                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../template/footer.jsp"/>
    </body>

    <script>
        function deleteUser(id) {
            if(id !=null)
            {
               window.location.href='${pageContext.servletContext.contextPath}/deleteUser/${user.noUtilisateur}';
            }
        }
    </script>
</html>

