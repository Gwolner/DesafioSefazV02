/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.pe.sefaz.test;

import br.gov.pe.sefaz.controller.jdbc.ConexaoJDBC;
import java.sql.Connection;

/**
 *
 * @author wolner
 */
public class TesteConexaoJDBC {
    
//    Teste de conexão com o HSQLDB via JDBC
    public static void main(String[] args) {
        
        Connection c = ConexaoJDBC.conectar();
        
        if(c == null){
            System.out.println("Não conectou!");
        }else{
            System.out.println("Conectou!");
        }
    }
}
