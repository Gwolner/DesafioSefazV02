<%@page import="br.gov.pe.sefaz.model.classe.Telefone"%>
<%@page import="br.gov.pe.sefaz.model.classe.Usuario"%>
<%@page import="br.gov.pe.sefaz.model.dao.UsuarioDAO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>JSP Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="style/font-awesome-4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="style/geral.css">
        <link rel="stylesheet" href="style/perfil.css">
    </head>
    <body>
        
        <!--Mantem os dados do usuario atualizado-->
        <% Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado"); %>        

        <!--Exibição dos dados do usuario-->
        <h1>Perfil de <%= usuarioLogado.getNome() %> </h1>        
        
        <!--Exibe mensagem se houver-->
        <%
        String mensagem = (String) session.getAttribute("msg");
        if(mensagem != null){
        %> 
            <h2 class="mensagem"><%= mensagem %></h2>
        <%
        }
        session.removeAttribute("msg");
        %>        
                
        <!--Exibição dos dados do usuario-->
        <h2>E-mail: <%= usuarioLogado.getEmail() %></h2>
        
        <h2>Possui <%= usuarioLogado.getTelefones().size() %> telefone(s)</h2>
         
        <!--Bloco de serviço externo-->
        <div id="bloco-geral-servico">
            
            <!--Bloco de serviço: Adicionar telefone-->
            <div id="servico-1" class="servico-perfil">

                <a id="incluir-telefone" href="#" >Incluir telefone</a><br>

                <div id="form-telefone">
                    <form action="TelefoneServlet" method="post">
                        <input id="id-usuario" type="hidden" name="id-usuario" value=<%= usuarioLogado.getId() %> >

                        <input id="novo-ddd" type="number" name="novo-ddd" value="" required placeholder="DDD">
                        <br>
                        <input id="novo-numero" type="text" name="novo-numero" value="" required placeholder="Numero">
                        <br>
                        <input id="novo-tipo" type="text" name="novo-tipo" value="" required placeholder="Corporativo, Pessoal, etc">
                        <br>
                        <button id="btn-adicionar-telefone" type="submit">Adicionar</button>
                    </form>
                </div>
            </div>

            <!--Bloco de serviço: Atualizar os dados do usuario-->
            <div id="servico-2" class="servico-perfil">

                <a id="atualizar-dados" href="#" >Atualizar dados</a><br>

                <div id="form-dados">
                    <input id="atualizar-nome" type="text" value="<%= usuarioLogado.getNome() %>" required placeholder="Nome">
                    <br>
                    <input id="atualizar-email" type="email" value="<%= usuarioLogado.getEmail() %>" required placeholder="E-mail">
                    <br>
                    <button onclick="atualizarDadosUsuario()">Atualizar dados</button>
                </div>
            </div>

            <!--Bloco de serviço: Atualizar a senha do usuario-->
            <div id="servico-3" class="servico-perfil">

                <a id="atualizar-senha" href="#" >Alterar senha</a>

                <div id="form-senha">
                    <input id="nova-senha" type="text" required placeholder="Nova senha">
                    <br>
                    <button onclick="alterarSenha()">Alterar senha</button>
                </div>
            </div>
        </div>
               
        <!--Bloco de telefone-->
        <div id="bloco-perfil">
            <table border="1">
                <tr>
                    <th>DDD</th><th>Numero</th><th>Tipo</th><th>Excluir</th><th>Alterar</th>
                </tr>            
                <% for (int index = 0; index < usuarioLogado.getTelefones().size(); index++) { %>                
                    <tr>
                        <td><%= usuarioLogado.getTelefones().get(index).getDdd() %></td>
                        <td><%= usuarioLogado.getTelefones().get(index).getNumero() %></td>
                        <td><%= usuarioLogado.getTelefones().get(index).getTipo() %></td>
                        <td><a href="#" onclick="deletarTelefone(<%= usuarioLogado.getTelefones().get(index).getId() %>)">
                                <i class="fa fa-trash" aria-hidden="true"></i>
                            </a>
                        </td>
                        <td><a href="TelefoneServlet?idtelefone=<%= usuarioLogado.getTelefones().get(index).getId()%>">
                                <i class="fa fa-pencil" aria-hidden="true"></i>
                            </a>
                        </td>
                    </tr>
                <% } %> 
            </table>
        <div>
        
        <!--Veridica se há telefone para alteração. Se houver, exibe form-->
        <%
        Telefone telefoneTemp = (Telefone) session.getAttribute("telefoneTemp"); 
            if(telefoneTemp != null){
        %>
        <div id="div-atualizar-telefone" class="servico-perfil">
            
            <a id="atualizar-telefone" href="#" >Cancelar</a>
            
            <div id="form-telefones">            
                <input id="id-telefone" type="hidden" name="id-telefone" value=<%= telefoneTemp.getId() %> >
                <input id="atualizar-ddd" type="text" value="<%= telefoneTemp.getDdd()%>" required placeholder="DD">
                <br>
                <input id="atualizar-numero" type="email" value="<%= telefoneTemp.getNumero()%>" required placeholder="Numero">
                <br>
                <input id="atualizar-tipo" type="email" value="<%= telefoneTemp.getTipo()%>" required placeholder="Tipo">
                <br>
                <button onclick="atualizarUnicoTelefone()">Atualizar telefone</button>
            </div> 
        </div> 
        <%  
        }
        session.removeAttribute("telefoneTemp");
        %>
        
        <!--Bloco da direita-->
        <div id="perfil-right">
            
            <!--Exclui o usuario do sistema-->
            <a href="#" onclick="deletarUsuario(<%= usuarioLogado.getId() %>)">Excluir perfil <i class="fa fa-times" aria-hidden="true"></i></a>

            <!--Retorna para a agenda-->
            <a href="agenda.jsp">Agenda <i class="fa fa-book" aria-hidden="true"></i></a>
            
            <!--Retorna para o index-->
            <a href="index.jsp">Logout <i class="fa fa-sign-out" aria-hidden="true"></i></a>
        </div> 
        
        <!--Comportamento remoto do JQuery-->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        
        <!--Comportamento local do JS-->
        <script src="script/perfil.js"></script>
    </body>
</html>
