/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.pe.sefaz.model.dao;


import br.gov.pe.sefaz.controller.jdbc.ConexaoJDBC;
import br.gov.pe.sefaz.model.classe.Telefone;
import br.gov.pe.sefaz.model.classe.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author wolner
 */
public class UsuarioDAO {
    
    //Queries de usuario
    private static final String SQL_INCLUIR_USUARIO = "INSERT INTO \"PUBLIC\".\"USUARIO\"(\"NOME\",\"EMAIL\",\"SENHA\") VALUES (?, ?, ?);";
    private static final String SQL_ALTERAR_USUARIO = "UPDATE PUBLIC.USUARIO SET NOME=?, EMAIL=? WHERE ID=?;";    
    private static final String SQL_ALTERAR_SENHA_USUARIO = "UPDATE PUBLIC.USUARIO SET SENHA=? WHERE ID=?;";    
    private static final String SQL_REMOVER_USUARIO = "DELETE FROM PUBLIC.USUARIO WHERE ID=?;";    
    private static final String SQL_CONSULTAR_USUARIOS = "SELECT * FROM PUBLIC.USUARIO;";    
    private static final String SQL_CONSULTAR_ID_USUARIO = "SELECT id FROM PUBLIC.USUARIO WHERE EMAIL=? AND SENHA=?;";
    private static final String SQL_CONSULTAR_USUARIO = "SELECT * FROM PUBLIC.USUARIO WHERE ID=?;";
    
    private static PreparedStatement pst = null;
       
    
    //Consulta o id pelo usuario
    public static int consultarIdUsuario(Usuario usuario){
        
        ResultSet rst;
        int idUsuario = -1;
        
        //Abre conexão
        Connection conn = ConexaoJDBC.conectar();
        
        try{
            //Atribui a query ao PreparedStatement
            pst = conn.prepareStatement(SQL_CONSULTAR_ID_USUARIO);
            
            //Seta valores na query
            pst.setString(1, usuario.getEmail());
            pst.setString(2, usuario.getSenha());
            
            //Executa a inserção e retorna um registro
            rst = pst.executeQuery();            
            
            //Caso o Select tenha retorno, então recupero o Id do usuário
            if(rst.next()){
                idUsuario = Integer.parseInt(rst.getString("id"));
            }
            
            //Fecha o ResultSet
            rst.close();
            
            //Término de uso do recurso PreparedStatement
            pst.close();            
            
        }catch(SQLException exSql){
            
            //Exibe mensagem mais detalhada do problema
            exSql.printStackTrace();
            
        }finally{
            
            //Fechando conexão com o BD
            ConexaoJDBC.desconectar();
        }
        
        return idUsuario;
    };
    
    //Consultar usuario pelo id
    public static Usuario consultarUsuario(int idUsuario){
        
        ResultSet rst;
        Usuario retUsuario = null;
        
        //Abre conexão
        Connection conn = ConexaoJDBC.conectar();
        
        try{
            //Atribui a query ao PreparedStatement
            pst = conn.prepareStatement(SQL_CONSULTAR_USUARIO);
            
            //Seta valores na query
            pst.setString(1, Integer.toString(idUsuario));
            
            //Executa a inserção e retorna um registro
            rst = pst.executeQuery();            
            
            //Caso a query tenha retorno, então recupero o nome do usuário
            if(rst.next()){
                retUsuario = new Usuario();
                retUsuario.setId(rst.getInt("id"));
                retUsuario.setNome(rst.getString("nome"));
                retUsuario.setEmail(rst.getString("email"));
                retUsuario.setTelefones(TelefoneDAO.consultarTelefones(rst.getInt("id")));
            }
            
            //Fecha o ResultSet
            rst.close();
            
            //Término de uso do recurso PreparedStatement
            pst.close();            
            
        }catch(SQLException exSql){
            
            //Exibe mensagem mais detalhada do problema
            exSql.printStackTrace();            
            
        }finally{
            
            //Fechando conexão com o BD
            ConexaoJDBC.desconectar();
        }
        
        return retUsuario;
    };
    
    //Cadastra usuario no sistema
    public static boolean incluirUsuario(Usuario usuario){
        
        boolean retUsuario = true;
        boolean retTelefones = false;
        
        //Abre conexão
        Connection conn = ConexaoJDBC.conectar();
        
        try{
            //Atribui a query ao PreparedStatement
            pst = conn.prepareStatement(SQL_INCLUIR_USUARIO);
            
            //Seta valores na query
            pst.setString(1, usuario.getNome());
            pst.setString(2, usuario.getEmail());
            pst.setString(3, usuario.getSenha());
            
            //Executa a inserção de usuário na tabela correspondente
            retUsuario = pst.execute();
            
            //Se inseriu o usuario (false), tentará inserir os telefones
            if(!retUsuario){
                int retIdUsuario = consultarIdUsuario(usuario);
                usuario.setId(retIdUsuario);
                retTelefones = TelefoneDAO.incluirTelefone(usuario);                
            }
            
            //Término de usar o recurso PreparedStatement
            pst.close();
            
        }catch(SQLException exSql){           
            
            //Exibe mensagem mais detalhada do problema
            exSql.printStackTrace();            
        }finally{
            
            //Fechando conexão com o BD
            ConexaoJDBC.desconectar();
        }
        
        
        return !retTelefones;
    };
    
    //Lista TODOS os usuarios
    public static ArrayList<Usuario> consultarUsuarios(){
        
        ResultSet rst;
        Usuario usuarioTemp = null;
        ArrayList<Usuario> usuarios = new ArrayList();
        ArrayList<Telefone> telefones = new ArrayList();
        
        //Abre conexão
        Connection conn = ConexaoJDBC.conectar();
        
        try{
            //Atribui a query ao PreparedStatement
            pst = conn.prepareStatement(SQL_CONSULTAR_USUARIOS);
           
            //Executa o comando e retorna os registros encontrados
            rst = pst.executeQuery();
            
            //Caso o Select tenha retorno, então recupero o Id do usuário
            while(rst.next()){
                int idUsuarioTemp = rst.getInt("id");
                usuarioTemp = new Usuario();
                usuarioTemp.setId(rst.getInt("id"));
                usuarioTemp.setNome(rst.getString("nome"));
                usuarioTemp.setEmail(rst.getString("email"));
                
                telefones = (ArrayList) TelefoneDAO.consultarTelefones(idUsuarioTemp);
                
                usuarioTemp.setTelefones(telefones);
                
                usuarios.add(usuarioTemp);
            }
            
            //Fecha o ResultSet
            rst.close();
            
            //Término de usar o recurso PreparedStatement
            pst.close();
            
        }catch(SQLException exSql){            
            
            //Exibe mensagem mais detalhada do problema
            exSql.printStackTrace();
            
        }finally{
            
            //Fechando conexão com o BD
            ConexaoJDBC.desconectar();
        }
        
        return usuarios;
    };
    
    //Altera os dados do usuario, exceto a senha
    public static boolean alterarUsuario(int idUsuario, String nome, String email){
        
        boolean retAtualziacao = true;
        
        //Abre a conexão
        Connection conn = ConexaoJDBC.conectar();
        
        try{
            //Atribui a query ao PreparedStatement
            pst = conn.prepareStatement(SQL_ALTERAR_USUARIO);
            
            //Seta valores na query
            pst.setString(1, nome);
            pst.setString(2, email);
            pst.setString(3, String.valueOf(idUsuario));
            
            //Executa a deleção do telefone
            retAtualziacao = pst.execute(); 
            
            //Término de usar o recurso PreparedStatement
            pst.close();
            
        }catch(SQLException exSql){ 
            
            //Exibe mensagem mais detalhada do problema
            exSql.printStackTrace();            
        }finally{
            
            //Fechando conexão com o BD
            ConexaoJDBC.desconectar();
        }
        
        return !retAtualziacao;
    };
    
    //Altera apenas a senha do usuario
    public static boolean alterarSenha(int idUsuario, String novaSenha){
        
        boolean retAtualziacao = true;
        
        //Abre a conexão
        Connection conn = ConexaoJDBC.conectar();
        
        try{
            //Atribui a query ao PreparedStatement
            pst = conn.prepareStatement(SQL_ALTERAR_SENHA_USUARIO);
            
            //Seta valores na query
            pst.setString(1, novaSenha);
            pst.setString(2, String.valueOf(idUsuario));
            
            //Executa a deleção do telefone
            retAtualziacao = pst.execute(); 
            
            //Término de usar o recurso PreparedStatement
            pst.close();
            
        }catch(SQLException exSql){ 
            
            //Exibe mensagem mais detalhada do problema
            exSql.printStackTrace();  
            
        }finally{
            
            //Fechando conexão com o BD
            ConexaoJDBC.desconectar();
        }
        
        return !retAtualziacao;
    };
    
    //Deleta o usuario do sistema, bem como seus respectivos telefones
    public static boolean removerUsuario(int idUsuario){
        
        boolean retExclusao = true;
        
        //Abre conexão
        Connection conn = ConexaoJDBC.conectar();
        
        try{
            //Atribui a query ao PreparedStatement
            pst = conn.prepareStatement(SQL_REMOVER_USUARIO);
           
            //Seta valores na query
            pst.setString(1, String.valueOf(idUsuario));
            
            //Executa a deleção do telefone
            retExclusao = pst.execute(); 
            
            //Término de usar o recurso PreparedStatement
            pst.close();
            
        }catch(SQLException exSql){
            
            //Exibe mensagem mais detalhada do problema
            exSql.printStackTrace();
            
        }finally{
            
            //Fechando conexão com o BD
            ConexaoJDBC.desconectar();
        }
        
        return !retExclusao;
    };
     
}
