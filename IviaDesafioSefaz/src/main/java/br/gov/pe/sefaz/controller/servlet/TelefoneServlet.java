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
@WebServlet(name = "TelefoneServlet", urlPatterns = {"/TelefoneServlet"})
public class TelefoneServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            int idTelefoneTemp = Integer.parseInt(request.getParameter("idtelefone"));
            
            //Invoca o DAO para recuperar o telefone
            Telefone telefoneTemp = TelefoneDAO.consultarTelefonesPorId(idTelefoneTemp);
            
            //Salva parametros na sessão            
            HttpSession session = request.getSession();
            session.setAttribute("telefoneTemp", telefoneTemp);
            
            //Desvia (volta) para o perfil
            response.sendRedirect("perfil.jsp");
            
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Recebe os parametros
        int idUsuario = Integer.parseInt(request.getParameter("id-usuario"));
        int novoDdd = Integer.parseInt(request.getParameter("novo-ddd"));
        String novoNumero = request.getParameter("novo-numero");
        String novoTipo = request.getParameter("novo-tipo");
        
        //Invoca método DAO para recuperar usuario
        Usuario usuarioLogado = UsuarioDAO.consultarUsuario(idUsuario);
        
        //Instancia e seta os atributos do novo telefone
        Telefone novoTelefone = new Telefone();
        novoTelefone.setIdUsuario(idUsuario);
        novoTelefone.setDdd(novoDdd);
        novoTelefone.setNumero(novoNumero);
        novoTelefone.setTipo(novoTipo);
        
        //Atribui novo telefone ao usuario
        usuarioLogado.getTelefones().add(novoTelefone);
        
        //Persiste telefone
        TelefoneDAO.incluirTelefone(usuarioLogado);        
        
        //Abre a sessão e salva usuario nela
        HttpSession session = request.getSession();
        session.setAttribute("usuarioLogado", usuarioLogado);
        
        
        //Desvia (volta) para o perfil
        response.sendRedirect("perfil.jsp");
        
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPut(request, response);
        
        //Recebe parámetros para usuario específico
        int idTelefoneTemp = Integer.parseInt(request.getParameter("idtelefone"));
        int dddTemp = Integer.parseInt(request.getParameter("novoddd"));
        String numeroTemp = request.getParameter("novonumero");
        String tipoTemp = request.getParameter("novotipo");
        
        //Invoca método DAO para recuperar os telefones cadastrados
        boolean retorno = TelefoneDAO.alterarTelefone(idTelefoneTemp, dddTemp, numeroTemp, tipoTemp);

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doDelete(request, response);
        
        int idTelefone = Integer.parseInt(request.getParameter("idtelefone"));                
            
        //Chama metodo de exclusão do número
        TelefoneDAO.removerTelefone(idTelefone);
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet responsável por concentrar as atribuições dos telefones e suas relações com usuarios";
    }// </editor-fold>

}
