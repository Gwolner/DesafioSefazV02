/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.pe.sefaz.controller.servlet;

import br.gov.pe.sefaz.model.dao.UsuarioDAO;
import br.gov.pe.sefaz.model.classe.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Recebe os parametros para tentar efetuar login
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        
        //Definindo usuário
        Usuario usuario = new Usuario();        
        usuario.setEmail(email);
        usuario.setSenha(senha);        
        
        //Invocando responsabilidade do DAO
        int idLogin = UsuarioDAO.consultarIdUsuario(usuario);
        
        //Obtem a sessão
        HttpSession session = request.getSession();
        
        //Tratamento da consulta de acesso
        if(idLogin >= 0){
            
            //Salva id do usuario na sessão
            session.setAttribute("idLogin", idLogin);            
            
            //Recupera o usuario e o salva na sessão
            Usuario usuarioLogado = UsuarioDAO.consultarUsuario(idLogin);
            session.setAttribute("usuarioLogado", usuarioLogado);
            
            //Retomando para index.jsp
            response.sendRedirect("agenda.jsp");
        }else{
            
            //Cria mensagem de erro de login            
            session.setAttribute("msg", "Erro ao efetuar login. Verifique se os campos estão corretos.");
            
            //Desvia para erro.jsp
            response.sendRedirect("index.jsp");
        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet utilizado exclusivamente para realizar login de usuarios";
    }// </editor-fold>

}
