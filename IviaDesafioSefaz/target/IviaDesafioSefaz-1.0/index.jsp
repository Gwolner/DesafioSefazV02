<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="style/geral.css">
        <link rel="stylesheet" href="style/home.css">
    </head>
    <body>

        <h1>Desafio Sefaz</h1>

        <!--Exibe mensagem caso haja alguma-->
        <%
        String mensagem = (String) session.getAttribute("msg");
        if (mensagem != null) {
        %> 
        <h2 class="mensagem"><%= mensagem%></h2>
        <%
        }
        session.removeAttribute("msg");
        %>

        <!--Bloco principal-->
        <div id="bloco-index">            

            <!--Form de login-->
            <div id="login">
                <h2>Login</h2>
                <form action="LoginServlet" method="post">
                    <input id="login-email" type="text" name="email" required placeholder="E-mail">
                    <br>
                    <input id="login-senha" type="password" name="senha" required placeholder="Senha">
                    <br>
                    <button id="btn-acessar" type="submit">Acessar <i class="fa fa-sign-in" aria-hidden="true"></i></button>
                </form>
            </div>

            <!--Forme de cadastro de usuario-->
            <div id="registro">
                <h2>Registro</h2>
                <form action="UsuarioServlet" method="post">
                    <input id="nome" type="text" name="nome" required placeholder="Nome">
                    <br>
                    <input id="email" type="email" name="email" required placeholder="E-mail">
                    <br>
                    <input id="senha" type="password" name="senha" required placeholder="Senha">
                    <br>
                    <input id="ddd" type="number" name="ddd" required placeholder="DDD">
                    <br>
                    <input id="numero" type="text" name="numero" required placeholder="Numero">
                    <br>
                    <input id="tipo" type="text" name="tipo"  required placeholder="Corporativo, Pessoal, etc">
                    <br>
                    <button id="btn-registrar" type="submit">Registrar <i class="fa fa-user-plus" aria-hidden="true"></i></button>
                </form>
            </div>

            <!--Alterna entre registrar usuario ou logar no sistem-->
            <a href="#" id="btn-login">Login <i class="fa fa-sign-in" aria-hidden="true"></i></a>

            <a href="#" id="btn-registro">Registrar <i class="fa fa-user-plus" aria-hidden="true"></i></a>
            
        </div>

        <!--Importa comportamento remoto do JQuery-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

        <!--Importa comportamento local do JS-->
        <script src="script/index.js"></script>
    </body>
</html>
