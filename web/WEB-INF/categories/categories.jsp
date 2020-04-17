<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <jsp:include page="../template/head.jsp"/>
    <script src="<%= request.getContextPath()%>/script/categories.js"></script>
    <title>Gestion des catégorie</title>
</head>

<body>
<jsp:include page="../template/header.jsp"/>


<div class="container my-5 py-5">
    <section class="text-center">
        <ul class="nav nav-tabs md-tabs blue-gradient" id="myTabMD" role="tablist">
            <li class="nav-item">
                <a class="nav-link active show" id="update-tab-md" data-toggle="tab" href="#update" role="tab" aria-controls="update-md"
                   aria-selected="true">Modifier une catégorie</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" id="create-tab-md" data-toggle="tab" href="#create" role="tab" aria-controls="create-md"
                   aria-selected="false">Créer une catégorie</a>
            </li>
        </ul>
        <div class="tab-content card pt-5" id="myTabContentMD">
            <div class="tab-pane fade active show" id="update" role="tabpanel" aria-labelledby="update-tab-md">
                <form method="post" action="" class="needs-validation" novalidate>
                    <div class="form-group">
                        <select required class="form-control" id="selectCat" name="selectCat" onchange="select()">
                            <option disabled selected value> Selectionez une catégorie </option>
                            <c:forEach var="cat" items="${requestScope.liCat}"  >
                                <option value=" <c:out value="${cat.noCategorie}" /> " >
                                    <c:out value="${cat.libelle}" />
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <input type="text" class="form-control" id="name" name="nom" placeholder="Nouveau nom" required>
                    </div>

                    <div>
                        <button class="btn btn-primary btn-rounded">
                            <i class="fas fa-edit mr-2" aria-hidden="true"></i>Modifier
                        </button>
                        <button class="btn btn-danger btn-rounded" type="button" onclick="deleteCat()">
                            <i class="fas fa-trash mr-2" aria-hidden="true"></i> Supprimer
                        </button>
                    </div>
                </form>
            </div>
            <div class="tab-pane fade" id="create" role="tabpanel" aria-labelledby="mdp-tab-md">
                <form method="post" action="${pageContext.servletContext.contextPath}/addCat" class="needs-validation" novalidate>
                    <div class="form-group">
                        <input type="text" class="form-control" id="name2" name="nom" required placeholder="Nouvelle catégorie">
                    </div>

                    <button class="btn btn-primary btn-rounded">Créer</button>
                </form>
            </div>
        </div>
    </section>
</div>


</body>
<script>

    
</script>
</html>