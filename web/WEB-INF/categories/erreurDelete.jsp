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
        <p> Suppression impossible car des articles sont lié a cette catégorie</p>
    </div>
</div>
</body>
</html>
