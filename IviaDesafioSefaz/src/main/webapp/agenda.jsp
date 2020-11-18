<%@page import="br.gov.pe.sefaz.model.classe.Telefone"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.gov.pe.sefaz.model.dao.UsuarioDAO"%>
<%@page import="br.gov.pe.sefaz.model.classe.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="style/geral.css">
        <link rel="stylesheet" href="style/agenda.css">
    </head>
    <body>
        <h1>Agenda</h1>

        <!--Exibe mensagem se houver-->
        <%
        String mensagem = (String) session.getAttribute("msg");
        if (mensagem != null) {
        %> 
            <h2><%= mensagem%></h2>
        <%
        }
        session.removeAttribute("msg");
        %>

        <!--Mantem os dados do usuario atualizado-->
        <% Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");%>
        
        <!--Bloco principal-->
        <div id="bloco-agenda">
                
            <!--Exibe nome recuperado-->
            <h2>Bem vindo(a), <%= usuarioLogado.getNome()%></h2>

            <!--Recupera usuario-->
            <% ArrayList<Usuario> usuarios = UsuarioDAO.consultarUsuarios();%>          

            <h2>Atualmente existem <%= usuarios.size()%> usuários cadastrados</h2> 
            
            <!--Exibe todos os usuarios cadastrados e seus respectivos telefones-->
            <table border="1">
                <tr>
                    <th>ID Usuario</th><th>Nome</th><th>E-mail</th>
                    <th>Quant. Telefones</th><th>Telefone</th><th>Tipo</th>
                </tr>

                <%
                //Percorre todos os usuarios, exibindo-os    
                for (int indexUsuario = 0; indexUsuario < usuarios.size(); indexUsuario++) {

                    //Recupera todos os telefones de um usuario
                    ArrayList<Telefone> telefonesTemp = usuarios.get(indexUsuario).getTelefones();

                    //Atribui a quantidade de telefones ao tamanho de linhas mescladas
                    int linhas = telefonesTemp.size();
                %>                
                <tr>
                    <td rowspan="<%= linhas%>"><%= usuarios.get(indexUsuario).getId()%></td>
                    <td rowspan="<%= linhas%>"><%= usuarios.get(indexUsuario).getNome()%></td>
                    <td rowspan="<%= linhas%>"><%= usuarios.get(indexUsuario).getEmail()%></td>                    
                    <td rowspan="<%= linhas%>"><%= telefonesTemp.size()%></td>

                    <!--Percorre os telefones pertencentes ao usuario-->
                    <%for (int indexTelefone = 0; indexTelefone < telefonesTemp.size(); indexTelefone++) {%>
                        <td>(<%= telefonesTemp.get(indexTelefone).getDdd()%>) <%= telefonesTemp.get(indexTelefone).getNumero()%></td>
                        <td><%= telefonesTemp.get(indexTelefone).getTipo()%></td>
                </tr>
                    <% } %>

                <% }%>            
            </table>
            
            <!--Bloco da direita-->
            <div id="agenda-right">
                
                <!--Desvia para pagina de perfil do usuario logado-->
                <a id="btn-perfil" href="UsuarioServlet?flag=perfil">Perfil <i class="fa fa-id-card-o" aria-hidden="true"></i></a>
                
                <!--Retorna para o index-->
                <a href="index.jsp">Logout <i class="fa fa-sign-out" aria-hidden="true"></i></a>
            </div> 
        </div>
    </body>
</html>
