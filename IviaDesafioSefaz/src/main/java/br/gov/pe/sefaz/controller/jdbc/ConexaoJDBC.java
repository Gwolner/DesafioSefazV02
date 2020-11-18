/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.gov.pe.sefaz.controller.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author wolner
 */
public class ConexaoJDBC {

    //Definição de parámetros de conexão
    private static Connection cnx = null;
    private static String usuario = "SA";
    private static String senha = "";
    private static String pathBase = "C:\\Users\\wolner\\Documents\\NetBeansProjects\\IviaDesafioSefaz\\database-hsqldb\\sefaz";
    private static String shutdown = ";shutdown=true"; //Quando a aplicação terminar, o BD deve estar apto a realizar shutdown. 
    private static String writeDelay = ";hsqldb.write_delay=false"; //O BD deve executar o write (INSERT) assim que recebe esta instrução.
    private static final String DRIVE_CLASS = "org.hsqldb.jdbcDriver";
    private static final String URL = "jdbc:hsqldb:file:" + pathBase + shutdown + writeDelay;

    //Cria uma conexão ou devolve a já existente
    public static Connection conectar() {

        //Design Pattern Singleton: permite uma única instancia do objeto Connection
        if (cnx == null) {
            try {
                //Carrega o drive
                Class.forName(DRIVE_CLASS);

                //Estabelece conexão
                cnx = DriverManager.getConnection(URL, usuario, senha);

            } catch (SQLException exSql) { //Exceção de DriverManager.getConnection
                
                //Exibe detalhes da exceção
                exSql.printStackTrace();

            } catch (ClassNotFoundException exClass) { //Exceção de Class.forName
                
                //Exibe detalhes da exceção
                exClass.printStackTrace();
            }
        }
        
        return cnx;
    }
    
    //Se houver uma conexão ela é encerrada
    public static void desconectar(){
        try{
            if(cnx != null){
                cnx.close();
                cnx = null;
            }            
        }catch(SQLException exSql){
            
            //Exibe detalhes da exceção
            exSql.printStackTrace();
        }
    }
}
