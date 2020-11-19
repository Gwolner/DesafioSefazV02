/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.pe.sefaz.model.dao;

import static br.gov.pe.sefaz.model.dao.UsuarioDAO.removerUsuario;
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
public class TelefoneDAO {
    
    //Queries de telefone
    private static final String SQL_INCLUIR_TELEFONES = "INSERT INTO \"PUBLIC\".\"TELEFONE\"(\"DDD\",\"NUMERO\",\"TIPO\",\"IDUSUARIO\") VALUES (?, ?, ?, ?);";
    private static final String SQL_ALTERAR_TELEFONE = "UPDATE PUBLIC.TELEFONE SET DDD=?, NUMERO=?, TIPO=? WHERE ID=?;";    
    private static final String SQL_REMOVER_TELEFONE = "DELETE FROM PUBLIC.TELEFONE WHERE ID=?;";    
    private static final String SQL_REMOVER_TELEFONES_DO_USUARIO = "DELETE FROM PUBLIC.TELEFONE WHERE IDUSUARIO=?;";    
    private static final String SQL_CONSULTAR_TELEFONES_POR_ID_USUARIO = "SELECT * FROM PUBLIC.TELEFONE WHERE IDUSUARIO=?;";    
    private static final String SQL_CONSULTAR_TELEFONES_POR_ID = "SELECT * FROM PUBLIC.TELEFONE WHERE ID=?;";    
    
    private static PreparedStatement pst = null;
    
    //Cadastra telefone
    public static boolean incluirTelefone(Usuario usuario) throws SQLException{

        boolean retTelefone = true;
        
        //Abre a conexão
        Connection conn = ConexaoJDBC.conectar();
        
        try{
            //Desativa o commit automático
            conn.setAutoCommit(false);
            
            //Atribui a query ao PreparedStatement
            pst = conn.prepareStatement(SQL_INCLUIR_TELEFONES);
            
            //Define o index para novo registro de telefone
            int index =  usuario.getTelefones().size()-1;
            
            //Atribui o Id do usuario ao telefone
            usuario.getTelefones().get(index).setIdUsuario(usuario.getId());
            
            //Seta os parâmetros na query
            pst.setInt(1, usuario.getTelefones().get(index).getDdd());
            pst.setString(2, usuario.getTelefones().get(index).getNumero());
            pst.setString(3, usuario.getTelefones().get(index).getTipo());
            pst.setInt(4, usuario.getTelefones().get(index).getIdUsuario());
            
            //Executa a inserção. Se houver problemas(true), sinaliza retorno false
            retTelefone = pst.execute();

            //Chama commmit
            conn.commit();
            
            //Término de uso do recurso PreparedStatement
            pst.close();            
           
        }catch(SQLException exSql){
            
            //Exibe mensagem mais detalhada do problema
            exSql.printStackTrace();    
            
            //Executa o rollback
            conn.rollback();
            
        }finally{            
            
            //Fechando conexão com so BD
            ConexaoJDBC.desconectar();      
        }        
        
        return !retTelefone;
    }
    
    //Lista TODOS os Telefones de um Usuario
    public static ArrayList<Telefone> consultarTelefones(int idUsuario) throws SQLException{
        
        ResultSet rst;
        Telefone telefoneTemp;
        ArrayList<Telefone> telefones = new ArrayList();
        
        //Abre a conexão
        Connection conn = ConexaoJDBC.conectar();
        
        try{
            //Desativa o commit automático
            conn.setAutoCommit(false);
            
            //Atribui a query ao PreparedStatement
            pst = conn.prepareStatement(SQL_CONSULTAR_TELEFONES_POR_ID_USUARIO);
           
            //Seta o valor na query
            pst.setString(1, String.valueOf(idUsuario));
            
            //Executa a consulta
            rst = pst.executeQuery();            
            
            //Caso o Select tenha retorno, recupera telefones
            while(rst.next()){
                telefoneTemp = new Telefone();
                telefoneTemp.setId(rst.getInt("id"));
                telefoneTemp.setDdd(rst.getInt("ddd"));
                telefoneTemp.setNumero(rst.getString("numero"));
                telefoneTemp.setTipo(rst.getString("tipo"));
                telefoneTemp.setIdUsuario(rst.getInt("idUsuario"));
                telefones.add(telefoneTemp);
            }

            //Chama commmit
            conn.commit();
            
            //Fecha o ResultSet
            rst.close();
            
            //Término de usar o recurso PreparedStatement.
            pst.close();
            
        }catch(SQLException exSql){       
            
            //Exibe mensagem mais detalhada do problema
            exSql.printStackTrace();   
            
            //Executa o rollback
            conn.rollback();
            
        }finally{
            
            //Fechando conexão com o BD
            ConexaoJDBC.desconectar();
        }
        
        return telefones;
    };
    
    //Consulta telefone pelo id
    public static Telefone consultarTelefonesPorId(int idTelefone) throws SQLException{
        
        ResultSet rst;
        Telefone telefoneTemp = null;
        
        //Abre a conexão
        Connection conn = ConexaoJDBC.conectar();
        
        try{
            //Desativa o commit automático
            conn.setAutoCommit(false);
            
            //Atribui a query ao PreparedStatement
            pst = conn.prepareStatement(SQL_CONSULTAR_TELEFONES_POR_ID);
           
            //Seta o valor na query
            pst.setString(1, String.valueOf(idTelefone));
            
            //Executa a consulta
            rst = pst.executeQuery();            
            
            //Caso o Select tenha retorno, então recupero telefone
            if(rst.next()){
                telefoneTemp = new Telefone();
                telefoneTemp.setId(rst.getInt("id"));
                telefoneTemp.setDdd(rst.getInt("ddd"));
                telefoneTemp.setNumero(rst.getString("numero"));
                telefoneTemp.setTipo(rst.getString("tipo"));
                telefoneTemp.setIdUsuario(rst.getInt("idUsuario"));
            }

            //Chama commmit
            conn.commit();
            
            //Fecha o ResultSet
            rst.close();
            
            //Término de usar o recurso PreparedStatement.
            pst.close();
            
        }catch(SQLException exSql){    
            
            //Exibe mensagem mais detalhada do problema
            exSql.printStackTrace();
            
            //Executa o rollback
            conn.rollback();
            
        }finally{
            
            //Fechando conexão com o BD
            ConexaoJDBC.desconectar();
        }
        
        return telefoneTemp;
    };
    
    //Remove um telefone específico
    public static boolean removerTelefone(int idTelefone) throws SQLException{
        
        boolean retExclusao = true;
        
        //Abre conexão
        Connection conn = ConexaoJDBC.conectar();
        
        try{
            //Desativa o commit automático
            conn.setAutoCommit(false);
            
            //Atribui a query ao PreparedStatement
            pst = conn.prepareStatement(SQL_REMOVER_TELEFONE);
           
            //Seta o valor na query
            pst.setString(1, String.valueOf(idTelefone));
            
            //Executa a deleção do telefone
            retExclusao = pst.execute(); 
            
            //Chama commmit
            conn.commit();
            
            //Término de usar o recurso PreparedStatement.
            pst.close();
            
        }catch(SQLException exSql){   
            
            //Exibe mensagem mais detalhada do problema
            exSql.printStackTrace(); 
            
            //Executa o rollback
            conn.rollback();
            
        }finally{
            
            //Fechando conexão com o BD
            ConexaoJDBC.desconectar();
        }
        
        return !retExclusao;
    }; 
    
    //Remove todos os telefones de um usuario
    public static boolean removerTelefonesDoUsuario(int idUsuario) throws SQLException{
        
        boolean retTelefones;
        boolean retUsuario = false;
        
        //Abre a conexão
        Connection conn = ConexaoJDBC.conectar();
        
        try{
            //Desativa o commit automático
            conn.setAutoCommit(false);
            
            //Atribui a query ao PreparedStatement
            pst = conn.prepareStatement(SQL_REMOVER_TELEFONES_DO_USUARIO);
           
            //Seta valor na query
            pst.setString(1, String.valueOf(idUsuario));
            
            //Executa a deleção do telefone
            retTelefones = pst.execute(); 
            
            if(!retTelefones){
                retUsuario = removerUsuario(idUsuario);
            }
            
            //Chama commmit
            conn.commit();
            
            //Término de usar o recurso PreparedStatement.
            pst.close();
            
        }catch(SQLException exSql){  
            
            //Exibe mensagem mais detalhada do problema
            exSql.printStackTrace();
            
            //Executa o rollback
            conn.rollback();
            
        }finally{
            
            //Fechando conexão com o BD
            ConexaoJDBC.desconectar();
        }
        
        return !retUsuario;
    };
  
    //Altera dados do telefone
    public static boolean alterarTelefone(int idTelefoneTemp, int ddd, String numero, String tipo) throws SQLException{
        
        boolean retAtualziacao = false;
        
        //Abre a conexão
        Connection conn = ConexaoJDBC.conectar();
        
        try{
            //Desativa o commit automático
            conn.setAutoCommit(false);
            
            //Atribui a query ao PreparedStatement
            pst = conn.prepareStatement(SQL_ALTERAR_TELEFONE);
                        
            //Seta valores na query
            pst.setString(1, String.valueOf(ddd));
            pst.setString(2, numero);
            pst.setString(3, tipo);
            pst.setString(4, String.valueOf(idTelefoneTemp));
            
            //Executa a alteração do telefone
            retAtualziacao = pst.execute(); 
            
            //Chama commmit
            conn.commit();
            
            //Término de usar o recurso PreparedStatement.
            pst.close();
            
        }catch(SQLException exSql){
            
            //Exibe mensagem mais detalhada do problema
            exSql.printStackTrace();
            
            //Executa o rollback
            conn.rollback();
            
        }finally{
            
            //Fechando conexão com o BD
            ConexaoJDBC.desconectar();
        }
        
        return !retAtualziacao;
    };
}
