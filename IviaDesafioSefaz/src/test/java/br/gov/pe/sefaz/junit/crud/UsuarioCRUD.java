/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.pe.sefaz.junit.crud;

import br.gov.pe.sefaz.junit.generic.GenericTest;
import br.gov.pe.sefaz.model.classe.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author wolner
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) //Roda os métodos de teste por ordem alfabética
public class UsuarioCRUD extends GenericTest{
    
     boolean retUsuario = true;
     
    @Test
    public void teste1PersistirUsuario() throws SQLException { 
        
        pst = cnx.prepareStatement("INSERT INTO \"PUBLIC\".\"USUARIO\"(\"NOME\",\"EMAIL\",\"SENHA\") VALUES (?, ?, ?);");
        
        pst.setString(1, "Eufrain");
        pst.setString(2, "eufra@gmail.com");
        pst.setString(3, "205");
        
        //Retorna falso
        retUsuario = pst.execute();
        pst.close();
                
        assertEquals(false, retUsuario);
    }
    
    @Test
    public void teste2ConsultarUsuario() throws SQLException {   
        
        Usuario usuario = new Usuario();
        ResultSet rst;
        
        pst = cnx.prepareStatement("SELECT * FROM PUBLIC.USUARIO WHERE NOME=?;");
        
        pst.setString(1, "Eufrain");
        
        //Retorna o/s registro/s
        rst = pst.executeQuery();
        
        if(rst.next()){
            usuario.setId(rst.getInt("id"));
            usuario.setNome(rst.getString("nome"));
            usuario.setEmail(rst.getString("email"));            
        }
        
        rst.close();
        pst.close();
        
        assertEquals("Eufrain", usuario.getNome());
        assertEquals("eufra@gmail.com", usuario.getEmail());
    }
    
    @Test
    public void teste3AtualizarUsuario() throws SQLException {
        
        pst = cnx.prepareStatement("UPDATE PUBLIC.USUARIO SET NOME=?, EMAIL=? WHERE SENHA=?;");
        
        pst.setString(1, "Euzebio");
        pst.setString(2, "Euzi@gmail.com");
        pst.setString(3, "205");
        
        //Retorna falso
        retUsuario = pst.execute();
        
        pst.close();
        
        assertEquals(false, retUsuario);
    }
    
    @Test
    public void teste4RemoverUsuario() throws SQLException{
        
        pst = cnx.prepareStatement("DELETE FROM PUBLIC.USUARIO WHERE SENHA=?;");
         
        pst.setString(1, "205");
        
        //Retorna falso
        retUsuario = pst.execute();
        
        pst.close();
        
        assertEquals(false, retUsuario);
    }
}
