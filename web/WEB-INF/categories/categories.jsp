<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="java.time.LocalDate" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <jsp:include page="../template/head.jsp"/>
    <script src="<%= request.getContextPath()%>/script/detailsEnchere.js"></script>
    <title>Détails d'une enchère</title>
</head>

<body>
<jsp:include page="../template/header.jsp"/>



<div class="col-sm-4">
    <div id="myTabContent" class="tab-content">
        <form method="post" action="">

            <div class="form-group">
                <label for="selectCat">Catégories :</label>
                <select class="form-control" id="selectCat" name="selectCat" onchange="select()">

                    <option disabled selected value> Selectionez une catégorie </option>
                    <c:forEach var="cat" items="${requestScope.liCat}"  >
                        <option value=" <c:out value="${cat.noCategorie}" /> " >
                            <c:out value="${cat.libelle}" />
                        </option>

                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="name">Nouveau nom</label>
                <input type="text" class="form-control" id="name" name="nom" required>
            </div>

            <div>
                <button class="btn btn-primary">Enregistré</button>

                <button class="btn btn-secondary-red btn-rounded" type="button" onclick="deleteCat()">
                    <i class="fas fa-trash mr-2" aria-hidden="true"></i> Supprimer</button>
            </div>
        </form>
    </div>

    <div id="myTabContent2" class="tab-content">
        <form method="post" action="${pageContext.servletContext.contextPath}/addCat">



            <div class="form-group">
                <label for="name">Nouvelle catégorie</label>
                <input type="text" class="form-control" id="name2" name="nom" required>
            </div>


            <button class="btn btn-primary">Céer</button>
        </form>
    </div>
</div>


</body>
<script>
    function select() {


        var sel = document.getElementById("selectCat");
        var text= sel.options[sel.selectedIndex].text;
        document.getElementById("name").value = text;



    }

    function deleteCat() {

        var result = confirm("Etes vous sûre de vouloir supprimer cette catégorie?");

        if (result == true) {
            var id = document.getElementById("selectCat").value;


            window.location.href='${pageContext.servletContext.contextPath}/dellCat/'+id;
        }

    }
    
</script>
</html>