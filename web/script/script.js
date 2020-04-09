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
        }

        reader.readAsDataURL(input.files[0]); // convert to base64 string
    }
}

function submitForm(){
    let invalidClassName = 'invalid'
    let inputs = document.querySelectorAll('input, select, textarea')
    inputs.forEach(function (input) {
        if(input.id !== '' && $('#' + input.id).val() === ''){
            input.classList.add(invalidClassName)
        }

        if(input.id !== '' && $('#' + input.id).val() !== ''){
            input.classList.remove(invalidClassName)
        }
    });
}