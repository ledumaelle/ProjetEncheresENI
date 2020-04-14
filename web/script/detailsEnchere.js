$(function () {
    $('.material-tooltip-main').tooltip({
        template: '<div class="tooltip md-tooltip"><div class="tooltip-arrow md-arrow"></div><div class="tooltip-inner md-inner"></div></div>'
    });

    $(document).ready(function () {
        disabledButton()
    })
})

function disabledButton() {
    $("#valideEnchere").prop("disabled", parseInt($("#newEnchere").data("credit")) - parseInt($("#newEnchere").val()) < 0)
}