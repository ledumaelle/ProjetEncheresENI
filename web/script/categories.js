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

(function() {
    'use strict';
    window.addEventListener('load', function() {
        var forms = document.getElementsByClassName('needs-validation');

        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();