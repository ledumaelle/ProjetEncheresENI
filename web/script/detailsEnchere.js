$(function () {
    $('.material-tooltip-main').tooltip({
        template: '<div class="tooltip md-tooltip"><div class="tooltip-arrow md-arrow"></div><div class="tooltip-inner md-inner"></div></div>'
    });

    $(document).ready(function () {
        disabledButton()
    })
})

function disabledButton() {
    if(parseInt($("#newEnchere").data("credit")) - parseInt($("#newEnchere").val()) > 0){
        $('#erreur-credit').css("display", "none")
        $("#valideEnchere").prop("disabled", false)
    }else{
        $('#erreur-credit').css("display", "block")
        $("#valideEnchere").prop("disabled", true)
    }
}