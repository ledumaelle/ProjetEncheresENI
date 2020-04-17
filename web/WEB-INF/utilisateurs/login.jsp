<%@ page import="com.eni.encheres.bo.Enchere" %>
<%@ page import="com.eni.encheres.bo.ArticleVendu" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
  <%@ include file="../template/head.jsp" %>
  <title>Connexion</title>

</head>
<style>
  :root {
    --input-padding-x: 1.5rem;
    --input-padding-y: .75rem;
  }

  body {
    background-size: cover;
    background-repeat: no-repeat;
  }

  .card-signin {
    border: 0;
    margin-top: 10rem;
    border-radius: 1rem;
    box-shadow: 0 0.5rem 1rem 0 rgba(0, 0, 0, 0.1);
  }

  .card-signin .card-title {
    margin-bottom: 2rem;
    font-weight: 300;
    font-size: 1.5rem;
  }

  .card-signin .card-body .card-option
  {
    padding: 2rem;
  }

  .card-option {
    font-weight: 300;
    font-size: 1rem;
  }

  .form-signin {
    width: 100%;
  }

  .form-signin .btn {
    font-size: 80%;
    border-radius: 5rem;
    letter-spacing: .1rem;
    font-weight: bold;
    padding: 1rem;
    transition: all 0.2s;
  }

  .form-label-group {
    position: relative;
    margin-bottom: 1rem;
  }

  .form-label-group input {
    height: auto;
    border-radius: 2rem;
  }

  .form-label-group>input,
  .form-label-group>label {
    padding: var(--input-padding-y) var(--input-padding-x);
  }

  .form-label-group>label {
    position: absolute;
    top: 0;
    left: 0;
    display: block;
    width: 100%;
    margin-bottom: 0;
    /* Override default `<label>` margin */
    line-height: 1.5;
    color: #495057;
    border: 1px solid transparent;
    border-radius: .25rem;
    transition: all .1s ease-in-out;
  }

  .form-label-group input::-webkit-input-placeholder {
    color: transparent;
  }

  .form-label-group input:-ms-input-placeholder {
    color: transparent;
  }

  .form-label-group input::-ms-input-placeholder {
    color: transparent;
  }

  .form-label-group input::-moz-placeholder {
    color: transparent;
  }

  .form-label-group input::placeholder {
    color: transparent;
  }

  .form-label-group input:not(:placeholder-shown) {
    padding-top: calc(var(--input-padding-y) + var(--input-padding-y) * (2 / 3));
    padding-bottom: calc(var(--input-padding-y) / 3);
  }

  .form-label-group input:not(:placeholder-shown)~label {
    padding-top: calc(var(--input-padding-y) / 3);
    padding-bottom: calc(var(--input-padding-y) / 3);
    font-size: 12px;
    color: #777;
  }

  .btn-google {
    color: white;
    background-color: #ea4335;
  }

  .btn-facebook {
    color: white;
    background-color: #3b5998;
  }

  /* Fallback for Edge
  -------------------------------------------------- */

  @supports (-ms-ime-align: auto) {
    .form-label-group>label {
      display: none;
    }
    .form-label-group input::-ms-input-placeholder {
      color: #777;
    }
  }

  /* Fallback for IE
  -------------------------------------------------- */

  @media all and (-ms-high-contrast: none),
  (-ms-high-contrast: active) {
    .form-label-group>label {
      display: none;
    }
    .form-label-group input:-ms-input-placeholder {
      color: #777;
    }
  }

</style>
<body>
<jsp:include page="../template/header.jsp"/>
<div class="container">
  <div class="row">
    <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
      <div class="card card-signin">
        <div class="card-body">
          <h5 class="card-title text-center">Connexion</h5>
          <p id="message_error" class="text-danger"><c:out value="${requestScope.message}" /></p>
          <form class="form-signin" action="login" method="post" id="loginForm">
            <div class="form-label-group">
              <input type="text" id="email" name="email" class="form-control" placeholder="Identifiant" value="<c:out value="${URLDecoder.decode(cookie.LOGIN.getValue(),'UTF-8')}"  />" required autofocus>
              <label for="email">Identifiant</label>
            </div>

            <div class="form-label-group">
              <input type="password" id="password" name="password" class="form-control" placeholder="Mot de passe" value="<c:out value="${URLDecoder.decode(cookie.MDP.getValue(),'UTF-8')}"  />" required>
              <label for="password">Mot de passe</label>
            </div>

            <div class="form-check-group ">
              <input type="checkbox" class="form-check-input" id="memoire" name="memoire" <c:out value="${requestScope.checkMemoire}"  />  >
              <label class="form-check-label" for="memoire">Se souvenir de moi</label>
            </div>
            <br>

            <button class="btn btn-lg btn-primary btn-block text-uppercase" id="submit_form_connection" type="submit">Connexion</button>
            <div class="text-center">
              <div class="card-option text-center">
                <hr class="my-4">
                <a href="${pageContext.servletContext.contextPath}/register" id="register">Pas encore de compte ?</a><br>
                <a href="#" id="mdpforgot">Mot de passe oublié ?</a>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>


<div class="modal fade" id="centralModalInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
  <div class="modal-dialog modal-notify modal-info" role="document">
    <!--Content-->
    <div class="modal-content">
      <!--Header-->
      <div class="modal-header">
        <p class="heading lead">Mot de passe oublié</p>

        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true" class="white-text">&times;</span>
        </button>
      </div>


      <!--Body-->
      <div class="modal-body">
        <div class="text-center">
          <div class="">
            <div class="">
              </br>
              <p>Oublier son mot de passe, ça nous arrive à tous ! </p>
              <p>Remplissez votre e-mail et nous vous aiderons a arranger cela.</p>
              </br>
            </div>
            <div class="form-label-group">
              <input type="email" id="inputEmail" class="form-control" name="email" placeholder="Votre adresse email" required autofocus>
              <label for="inputEmail">Email</label>
            </div>
          </div>
        </div>
      </div>

      <!--Footer-->
      <div class="modal-footer justify-content-center">
        <a type="button" class="btn btn-primary text-white" id="envoyer">Envoyer</a>
        <a type="button" class="btn btn-outline-primary waves-effect" data-dismiss="modal">Fermer</a>
      </div>
    </div>
    <!--/.Content-->
  </div>
</div>
<%@ include file="../template/footer.jsp" %>
</body>
<script type="text/javascript">
  // Animations initialization
  new WOW().init();

</script>

<script>

  $('#mdpforgot').click(function(){
    $('#centralModalInfo').modal('show');
  });


  $('#centralModalInfo').on('show.bs.modal', function () {
    $('#inputEmail').css("border","1.5px solid black");
    $('#envoyer').click(function(){

      if(document.getElementById("inputEmail").value == ""){


        $('#inputEmail').css("border","1.5px solid red");

      }else{

      }
    });
  });



</script>
</html>
