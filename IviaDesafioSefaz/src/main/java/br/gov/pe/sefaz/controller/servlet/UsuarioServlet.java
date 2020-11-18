/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.pe.sefaz.controller.servlet;

import br.gov.pe.sefaz.model.dao.TelefoneDAO;
import br.gov.pe.sefaz.model.dao.UsuarioDAO;
import br.gov.pe.sefaz.model.classe.Telefone;
import br.gov.pe.sefaz.model.classe.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wolner
 */
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String flagTemp = request.getParameter("flag");

        //Recupera o id na sessão
        HttpSession session = request.getSession();
        int idLogin = (int) session.getAttribute("idLogin");

        //Consulta usuario
        Usuario usuarioLogado = UsuarioDAO.consultarUsuario(idLogin);

        //Salva usuario na sessão
        session.setAttribute("usuarioLogado", usuarioLogado);

        if (usuarioLogado != null) {

            //Define o redirecionamento 
            if (flagTemp.equals("perfil")) {
                response.sendRedirect("perfil.jsp");
            } else if (flagTemp.equals("agenda")) {
                response.sendRedirect("agenda.jsp");
            }

        } else {

            //Cria mensagem de erro
            session.setAttribute("msg", "Ocorreu um erro de redirecionamento. Contate o administrador do sistema");

            //Define redirecionamento para agenda ou perfil
            if (flagTemp.equals("perfil")) {
                response.sendRedirect("agenda.jsp");
            } else if (flagTemp.equals("agenda")) {
                response.sendRedirect("perfil.jsp");
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Recebe os parametros para cadastro
        String nome = request.getParameter("nome");
        String senha = request.getParameter("senha");
        String email = request.getParameter("email");
        int ddd = Integer.parseInt(request.getParameter("ddd"));
        String numero = request.getParameter("numero");
        String tipo = request.getParameter("tipo");

        //Divisão de responsabilidade: Usuario
        Usuario usuarioTemp = new Usuario();

        usuarioTemp.setNome(nome);
        usuarioTemp.setSenha(senha);
        usuarioTemp.setEmail(email);

        //Divisão de responsabilidade: Telefone
        Telefone telefoneTemp = new Telefone();

        telefoneTemp.setDdd(ddd);
        telefoneTemp.setNumero(numero);
        telefoneTemp.setTipo(tipo);

        //Acoplamento os componentes
        ArrayList<Telefone> telefones = new ArrayList();
        telefones.add(telefoneTemp);
        usuarioTemp.setTelefones(telefones);

        //Invocando responsabilidade do DAO
        boolean retCadastro = UsuarioDAO.incluirUsuario(usuarioTemp);

        //Abre sessão para criação de mensagem
        HttpSession session = request.getSession();

        if (!retCadastro) {
            //Mensagem de sucesso
            session.setAttribute("msg", "Usuário cadastrado no sistema.");
        } else {
            //Erro ao cadastrar usuario
            session.setAttribute("msg", "Ocorreu um erro ao cadastrar usuário. Contate o administrador do sistema");
        }

        //Retomando para index.jsp
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPut(request, response);

        //Parametro de chaveamento de operação
        String flagTemp = request.getParameter("flag");

        //Atualiza os dados do usuario
        if (flagTemp.equals("dados")) {

            String nomeTemp = request.getParameter("novonome");
            String emailTemp = request.getParameter("novoemail");

            //Abre sessão para obter usuario
            HttpSession session = request.getSession();

            int idUsuarioTemp = (int) session.getAttribute("idLogin");

            //Invoca o DAO alterar os dados do usuario
            boolean retorno = UsuarioDAO.alterarUsuario(idUsuarioTemp, nomeTemp, emailTemp);

            //Se houver erro, desvia para a página de erro
            if (retorno) {

                //Mensagem de sucesso
                session.setAttribute("msg", "Usuário atualizado no sistema.");
            } else {
                //Erro ao cadastrar usuario
                session.setAttribute("msg", "Ocorreu um erro ao atualizar usuário. Contate o administrador do sistema");
            }
            
        } //Atualiza apenas a senha do usuário
        else if (flagTemp.equals("senha")) {

            String novaSenhaTemp = request.getParameter("nova");

            //Abre sessão para criação de mensagem
            HttpSession session = request.getSession();

            int idUsuarioTemp = (int) session.getAttribute("idLogin");

            System.out.println("ID LOGIN: " + idUsuarioTemp);
            System.out.println("SENHA NOVA: " + novaSenhaTemp);

            //Invoca o DAO alterar a senha do usuario
            boolean retorno = UsuarioDAO.alterarSenha(idUsuarioTemp, novaSenhaTemp);

            if (retorno) {
                //Mensagem de sucesso
                session.setAttribute("msg", "Senha alterada com sucesso.");
            } else {
                //Erro ao alterar senha
                session.setAttribute("msg", "Ocorreu um erro ao alterar a senha. Contate o administrador do sistema");
            }
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doDelete(request, response);

        int idUsuario = Integer.parseInt(request.getParameter("idusuario"));

        //Chama metodo de exclusão do número
        boolean retTelefone = TelefoneDAO.removerTelefonesDoUsuario(idUsuario);

        //Abre sessão para criação de mensagem
        HttpSession session = request.getSession();

        if (!retTelefone) {
            //Mensagem de sucesso
            session.setAttribute("msg", "Usuário excluido do sistema.");
        } else {
            //Mensagem de erro
            session.setAttribute("msg", "Ocorreu um erro ao excluir usuario. Contate o administrador do sistema");
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet responsável por concentrar as atribuições dos usuarios e suas relações com telefones";
    }// </editor-fold>

}
