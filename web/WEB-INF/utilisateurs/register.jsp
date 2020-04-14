<%--
  Created by IntelliJ IDEA.
  User: tcollin
  Date: 09/04/2020
  Time: 14:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../../include.jsp"/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
            <div class="card card-signin">
                <div class="card-body">
                    <button class="btn btn-dark hBack" type="button" onclick="back()">&#8249; Retour</button>
                    </br></br>
                    <h5 class="card-title text-center font-italic">Création Compte</h5>

                    <form class="form-signin" method="post" action="registerAdd.php">
                        <div class="form-label-group">
                            <input type="text" id="inputPseudo" name="inputPseudo" class="form-control" placeholder="Nom" required autofocus>
                            <label for="inputPseudo"><i class="fas fa-user"></i>   Pseudo</label>
                        </div>

                        <div class="form-label-group">
                            <input type="text" id="inputNom" name="inputNom" class="form-control" placeholder="Nom" required>
                            <label for="inputNom"><i class="fas fa-user"></i>   Nom</label>
                        </div>

                        <div class="form-label-group">
                            <input type="text" id="inputPrenom"  name="inputPrenom" class="form-control" placeholder="Prénom" required>
                            <label for="inputPrenom"><i class="fas fa-user"></i>  Prénom</label>
                        </div>

                        <div class="form-label-group">
                            <input type="text"  id="inputAdress"  name="inputAdress" class="form-control" placeholder="Adresse" required>
                            <label for="inputAdress"><i class="fas fa-home"></i>  Adresse</label>
                        </div>

                        <div class="form-label-group">
                            <input type="text"  id="inputVille"  name="inputVille" class="form-control" placeholder="Ville" required>
                            <label for="inputAdress"><i class="fas fa-city"></i>  Ville</label>
                        </div>

                        <div class="form-label-group">
                            <input type="text" pattern="[0-9]{5}"  name="inputCodePostal" id="inputCodePostal" class="form-control" placeholder="Code Postal" required>
                            <label for="inputCodePostal"><i class="fas fa-map-marker-alt"></i>  Code Postal</label>
                        </div>

                        <div class="form-label-group">
                            <input type="tel"  name="inputPhone" id="inputPhone" class="form-control" placeholder="Code Postal" required>
                            <label for="inputPhone"><i class="fas fa-phone"></i>  Téléphone mobile</label>
                        </div>

                        <div class="form-label-group">
                            <input type="email" id="inputEmail"  name="inputEmail" class="form-control" placeholder="Email address" required autofocus>
                            <label for="inputEmail"><i class="fas fa-at"></i>  Adresse Email</label>
                        </div>

                        <div class="form-label-group">
                            <input type="password" id="inputPassword"  name="inputPassword" class="form-control" placeholder="Password" required>
                            <label for="inputPassword"><i class="fas fa-key"></i> Mot De Passe</label>
                        </div>

                        <div class="form-label-group">
                            <input type="password" id="inputPasswordConfirm"  name="inputPasswordConfirm" class="form-control" placeholder="PasswordConfirm" required>
                            <label for="inputPasswordConfirm"><i class="fas fa-key"></i> Confirmer Mot De Passe</label>
                            <p id="mdperror"></p>
                        </div>
                        </br>

                        <button class="btn btn-lg btn-primary btn-block text-uppercase" value="Enregistrer">S'enregistrer</button>
                    </form>
                    </br>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script>
    var password = document.getElementById("inputPassword")
        , confirm_password = document.getElementById("inputPasswordConfirm");

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
