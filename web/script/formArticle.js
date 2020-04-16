$(function(){
    $('.datepicker').pickadate(
        {
            monthsFull: ['Janvier', 'Février', 'Mars', 'Avril', 'Mai', 'Juin', 'Juillet', 'Août', 'Septembre', 'Octobre', 'Novembre', 'Décembre'],
            weekdaysShort: ['Dim', 'Lun', 'Mar', 'Mer', 'Jeu', 'Ven', 'Sam'],
            today: 'aujourd\'hui',
            clear: 'effacer',
            close: 'fermer',
            format: 'dd/mm/yyyy',
            formatSubmit: 'yyyy-mm-dd',
            hiddenPrefix: 'date_',
            hiddenSuffix: '_format'
        }
    );

    $('.datepicker').removeAttr('readonly');

    $("#photo").change(function() {
        readURL(this);
    });
});

function readURL(input) {
    if (input.files && input.files[0]) {
        let reader = new FileReader();

        reader.onload = function(e) {
            $('#img-preview').attr('src', e.target.result);
            $('#photoB64').attr('value', e.target.result);
            $('#photoSize').attr('value', input.files[0].size)
        }

        reader.readAsDataURL(input.files[0]); // convert to base64 string
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