<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
    <head>
        <jsp:include page="../template/head.jsp"/>
        <title>S'inscrire</title>
        <script src="<%= request.getContextPath()%>/script/register.js"></script>
    </head>
    <body>
        <jsp:include page="../template/header.jsp"/>
        <div class="container mt-5 mb-5">
            <!--Grid row-->
            <div class="row d-flex justify-content-center">
                <!--Grid column-->
                <div class="col-md-6 mx-auto">
                    <div class="card">
                        <!--Card content-->
                        <div class="card-body">
                            <p id="message_error" class="text-danger"><c:out value="${requestScope.message}" /></p>
                              <form class="needs-validation" method="post" action="<%=request.getContextPath()%>/register" novalidate>

                                <p class="h4 mb-4 text-center">S'enregistrer</p>

                                <div class="form-row">
                                    <div class="col-md-12 mb-3">
                                        <input type="text" id="idPseudo" name="inputPseudo" placeholder="Pseudo" class="form-control" required autofocus>
                                        <div class="invalid-feedback">Veuillez chosir un pseudo.</div>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="col-md-6 mb-3">
                                        <input type="text" id="idNom" name="inputNom" class="form-control" required placeholder="Nom">
                                        <div class="invalid-feedback">Veuillez chosir un nom.</div>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <input type="text" id="idPrenom" name="inputPrenom" class="form-control" required placeholder="Prénom">
                                        <div class="invalid-feedback">Veuillez chosir un prénom.</div>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="col-md-12 mb-3">
                                        <input type="email" id="idEmail" name="inputEmail" class="form-control" required placeholder="Email">
                                        <div class="invalid-feedback">Veuillez chosir un email.</div>
                                    </div>
                                </div>


                                <div class="form-row">
                                    <div class="col-md-6 mb-3">
                                        <input type="password" id="idPassword" name="inputPassword" class="form-control" required placeholder="Mot de passe">
                                        <div class="invalid-feedback">Veuillez chosir un mot de passe.</div>
                                    </div>
                                    <div class="col-md-6 mb-3">
                                        <input type="password" id="idPasswordConfirm"  name="inputPasswordConfirm" class="form-control" required placeholder="Confirmation">
                                        <div class="invalid-feedback">Le mot de passe n'est pas identique.</div>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="col-md-12 mb-3">
                                        <input type="text"  id="idAdress"  name="inputAdress" class="form-control" required placeholder="Adresse">
                                        <div class="invalid-feedback">Veuillez chosir une adresse.</div>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="col-md-12 mb-3">
                                        <input type="text" pattern="[0-9]{5}"  name="inputCodePostal" id="idCodePostal" class="form-control" required placeholder="Code Postal">
                                        <div class="invalid-feedback">Veuillez chosir un code postal.</div>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="col-md-12 mb-3">
                                        <input type="text"  id="idVille"  name="inputVille" class="form-control" required placeholder="Ville">
                                        <div class="invalid-feedback">Veuillez chosir une ville.</div>
                                    </div>
                                </div>

                                <div class="form-row">
                                    <div class="col-md-12 mb-3">
                                        <input type="number" id="idPhone" name="inputPhone" class="form-control" required placeholder="Téléphone">
                                        <div class="invalid-feedback">Veuillez chosir un téléphone.</div>
                                    </div>
                                </div>

                                <!-- Sign up button -->
                                <div class="form-row">
                                    <div class="col-md-6 text-center text-md-right">
                                        <button class="btn btn-primary btn-rounded text-uppercase" type="submit">ENREGISTRER</button>
                                    </div>
                                    <div class="col-md-6 text-center text-md-left">
                                        <a href="<%=request.getContextPath()%>" class="btn btn-outline-primary btn-rounded text-uppercase">ANNULER</a>
                                    </div>
                                </div>

                            </form>
                            <!-- Default form register -->
                        </div>
                    </div>
                </div>
                <!--Grid column-->
            </div>
            <!--Grid row-->
        </div>

    <jsp:include page="../template/footer.jsp"/>
    </body>

    <script>
        var password = document.getElementById("idPassword")
            , confirm_password = document.getElementById("idPasswordConfirm");

        function validatePassword(){
            if(password.value !== confirm_password.value) {
                confirm_password.setCustomValidity("Le mot de passe n'est pas identique.");
            } else {
                confirm_password.setCustomValidity('');
            }
        }

        password.onchange = validatePassword;
        confirm_password.onkeyup = validatePassword;
    </script>
</html>
