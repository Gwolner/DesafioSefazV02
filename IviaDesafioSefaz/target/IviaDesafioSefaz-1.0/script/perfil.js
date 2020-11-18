//Captura e definição de variáveis
var incluirTelefone = document.getElementById("incluir-telefone");
var atualizarDados = document.getElementById("atualizar-dados");
var atualizarSenha = document.getElementById("atualizar-senha");
var atualizarTelefone = document.getElementById("atualizar-telefone");
var nomeDoProjeto = "IviaDesafioSefaz";
var flagTelefone = 0;
var flagDados = 0;
var flagSenha = 0;

//Inicia oculto os forms de telefone e de dados
$("#form-telefone").css("display", "none");
$("#form-dados").css("display", "none");
$("#form-senha").css("display", "none");

//Exibe ou omite o form de adição de telefones
incluirTelefone.addEventListener("click", function () {
    if (flagTelefone === 0) {
        $("#form-telefone").css("display", "block");
        incluirTelefone.innerHTML = "Cancelar";
        flagTelefone = 1;
    } else if (flagTelefone === 1) {
        $("#form-telefone").css("display", "none");
        incluirTelefone.innerHTML = "Incluir telefone";
        flagTelefone = 0;
    }
});

//Exibe ou omite o form de atualização de dados
atualizarDados.addEventListener("click", function () {
    if (flagDados === 0) {
        $("#form-dados").css("display", "block");
        atualizarDados.innerHTML = "Cancelar";
        flagDados = 1;
    } else if (flagDados === 1) {
        $("#form-dados").css("display", "none");
        atualizarDados.innerHTML = "Atualizar dados";
        flagDados = 0;
    }
});

//Exibe ou omite o form de atualização de senha
atualizarSenha.addEventListener("click", function () {
    if (flagSenha === 0) {
        $("#form-senha").css("display", "block");
        atualizarSenha.innerHTML = "Cancelar";
        flagSenha = 1;
    } else if (flagSenha === 1) {
        $("#form-senha").css("display", "none");
        atualizarSenha.innerHTML = "Alterar senha";
        flagSenha = 0;
    }
});

//Omite o form de atualização de telefone
atualizarTelefone.addEventListener("click", function () {
    $("#div-atualizar-telefone").css("display", "none");      
});

//Requisição Fetch (AJAX) para deletar um telefone específico
function deletarTelefone(idTelefone) {
    fetch("TelefoneServlet?idtelefone=" + idTelefone, {method: 'delete'})
            .then(function (response) {
                window.location.replace("/" + nomeDoProjeto + "/UsuarioServlet?flag=perfil");
            });
}
;

//Requisição Fetch (AJAX) para atualizar dados do usuario
function atualizarDadosUsuario() {

    //A captura ocorre DEPOIS que o value é alterado
    var atualizarNome = document.getElementById("atualizar-nome").value;
    var atualizarEmail = document.getElementById("atualizar-email").value;

    fetch("UsuarioServlet?flag=dados&novonome=" + atualizarNome + "&novoemail=" + atualizarEmail, {method: 'put'})
            .then(function (response) {
                window.location.replace("/" + nomeDoProjeto + "/UsuarioServlet?flag=perfil");
            });
}
;

//Requisição Fetch (AJAX) para atualizar senha do usuario
function alterarSenha() {

    //A captura ocorre DEPOIS que o value é alterado
    var novaSenha = document.getElementById("nova-senha").value;

    fetch("UsuarioServlet?flag=senha&nova=" + novaSenha, {method: 'put'})
            .then(function (response) {
                window.location.replace("/" + nomeDoProjeto + "/UsuarioServlet?flag=perfil");
            });
}
;

//Requisição Fetch (AJAX) para atualizar telefone
function atualizarUnicoTelefone() {

    //A captura ocorre DEPOIS que o value é alterado
    var idTelefone = document.getElementById("id-telefone").value;
    var atualizarDdd = document.getElementById("atualizar-ddd").value;
    var atualizarNumero = document.getElementById("atualizar-numero").value;
    var atualizarTipo = document.getElementById("atualizar-tipo").value;

    fetch("TelefoneServlet?idtelefone=" + idTelefone + "&novoddd=" + atualizarDdd + "&novonumero=" + atualizarNumero + "&novotipo=" + atualizarTipo, {method: 'put'})
            .then(function (response) {
                window.location.replace("/" + nomeDoProjeto + "/UsuarioServlet?flag=perfil");
            });
}
;

//Requisição Fetch (AJAX)
function deletarUsuario(idUsuario) {
    fetch("UsuarioServlet?idusuario=" + idUsuario, {method: 'delete'})
            .then(function (response) {
                window.location.replace("/" + nomeDoProjeto + "/index.jsp");
            });
}
;

