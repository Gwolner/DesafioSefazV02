/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.pe.sefaz.junit.generic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 *
 * @author wolner
 */
public class GenericTest {
    
    protected static Connection cnx = null;
    protected static String usuario = "SA";
    protected static String senha = "";
    protected static String pathBase = "C:\\Users\\wolner\\Documents\\NetBeansProjects\\zDesafioSefaz\\database-hsqldb\\sefaz";
    protected static String shutdown = ";shutdown=true"; //Quando a aplicação terminar, o BD deve estar apto a realizar shutdown. 
    protected static String writeDelay = ";hsqldb.write_delay=false"; //O BD deve executar o write (INSERT) assim que recebe esta instrução.
    protected static final String DRIVE_CLASS = "org.hsqldb.jdbcDriver";
    protected static final String URL = "jdbc:hsqldb:file:" + pathBase + shutdown + writeDelay;
    protected static PreparedStatement pst = null;
    
    @BeforeClass //Mensagem para marcar início dos testes
    public static void setUpClass(){
        System.out.println("Iniciando teste de JUnit.");
    }
    
    @AfterClass //Mensagem para marcar término dos testes
    public static void tearDownClass(){
        System.out.println("Fim do teste JUnit.");
    }
    
    @Before //Cria uma conexão ou devolve a já existente
    public void setUp() throws ClassNotFoundException, SQLException {         
        Class.forName(DRIVE_CLASS);
        cnx = DriverManager.getConnection(URL, usuario, senha);      
    }

    @After //Se houver uma conexão ela é encerrada
    public void tearDown() throws SQLException {         
        if(cnx != null){
            cnx.close();
            cnx = null;
        } 
    }
}
