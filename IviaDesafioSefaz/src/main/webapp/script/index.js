//Inicia com form registro oculto
$("#registro").css("display", "none");

//Inicia com link de login oculto
$("#btn-login").css("display", "none");

//Ação realizada ao clicar no link de login
var login = document.getElementById("btn-login");
login.addEventListener("click", function () {
    
    //Oculta link do login e exibe o de registro
    $("#btn-login").css("display", "none");
    $("#btn-registro").css("display", "block");
    
    //Oculta form do registro e exibe o de login
    $("#login").css("display", "block");
    $("#registro").css("display", "none");
});

//Ação realizada ao clicar no link de registro
var registro = document.getElementById("btn-registro");
registro.addEventListener("click", function () {
    
    //Oculta link do registro e exibe o de login
    $("#btn-login").css("display", "block");
    $("#btn-registro").css("display", "none");
    
    //Oculta form do login e exibe o de registro
    $("#login").css("display", "none");
    $("#registro").css("display", "block");
});

