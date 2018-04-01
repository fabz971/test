package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    
    private Connection con;
    private Statement sentencia;
    private ResultSet tablaVirtual;
    
    public Conexion(String server,String user, String pass, String bdName) throws ClassNotFoundException, SQLException{
        
        String protocolo = "jdbc:mysql://";
        String lineaUsuario = "user="+user;
        String lineaPass = "password="+pass;
        
        String url = protocolo + 
                server + "/" + bdName 
                + "?" + lineaUsuario 
                +"&" + lineaPass;
        
        System.out.println(url);
        
        Class.forName("com.mysql.jdbc.Driver");
        
        con = DriverManager.getConnection(url);
    }
    
    public void ejecutar(String sql) throws SQLException{
        sentencia = con.createStatement();
        
        sentencia.execute(sql);
        System.out.println(sql);
        
        desconectar();
    }
    
    public ResultSet ejecutarSelect(String select) throws SQLException{
        System.out.println(select);
        sentencia = con.createStatement();
        tablaVirtual = sentencia.executeQuery(select);
        
        return tablaVirtual;
    }
    
    public void desconectar() throws SQLException{
        sentencia.close();
    }
    
}
