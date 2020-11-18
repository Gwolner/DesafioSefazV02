/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.pe.sefaz.junit.crud;

import br.gov.pe.sefaz.junit.generic.GenericTest;
import br.gov.pe.sefaz.model.classe.Telefone;
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
public class TelefoneCRUD extends GenericTest{    
    
    boolean retTelefone = true;
    
    @Test
    public void teste1PersistirTelefone() throws SQLException {    
        
        pst = cnx.prepareStatement("INSERT INTO \"PUBLIC\".\"TELEFONE\"(\"DDD\",\"NUMERO\",\"TIPO\",\"IDUSUARIO\") VALUES (?, ?, ?, ?);");
        
        pst.setInt(1, 81);
        pst.setString(2, "900014545");
        pst.setString(3, "Pessoal");
        pst.setInt(4, 100);
        
        //Retorna falso
        retTelefone = pst.execute();
        pst.close();
                
        assertEquals(false, retTelefone);
    }  
    
    @Test
    public void teste2AtualizarTelefone() throws SQLException {
        
        pst = cnx.prepareStatement("UPDATE PUBLIC.TELEFONE SET DDD=?, NUMERO=?, TIPO=? WHERE IDUSUARIO=?;");
        
        pst.setString(1, "87");
        pst.setString(2, "950501818");
        pst.setString(3, "Corporativo");
        pst.setString(4, "100");
        
        //Retorna falso
        retTelefone = pst.execute();
        
        pst.close();
        
        assertEquals(false, retTelefone);
    }
    
    @Test
    public void teste3ConsultarTelefone() throws SQLException {        
        
        Telefone telefone = new Telefone();
        ResultSet rst;
        
        pst = cnx.prepareStatement("SELECT * FROM PUBLIC.TELEFONE WHERE IDUSUARIO=?;");
        
        pst.setString(1, "100");
        
        //Retorna o/s registro/s
        rst = pst.executeQuery();
        
        if(rst.next()){            
            telefone.setIdUsuario(rst.getInt("id"));
            telefone.setDdd(rst.getInt("ddd"));
            telefone.setNumero(rst.getString("numero"));
            telefone.setTipo(rst.getString("tipo"));
            telefone.setIdUsuario(rst.getInt("idUsuario"));
        }
        
        rst.close();
        pst.close();
        
        assertEquals(87, telefone.getDdd());
        assertEquals("950501818", telefone.getNumero());
        assertEquals("Corporativo", telefone.getTipo());
        assertEquals(100, telefone.getIdUsuario());

    }
    
    @Test
    public void teste4RemoverTelefone() throws SQLException{
        
        pst = cnx.prepareStatement("DELETE FROM PUBLIC.TELEFONE WHERE IDUSUARIO=?;");
         
        pst.setString(1, "1");
        
        //Retorna falso
        retTelefone = pst.execute();
        
        pst.close();
        
        assertEquals(false, retTelefone);        
    }
}
