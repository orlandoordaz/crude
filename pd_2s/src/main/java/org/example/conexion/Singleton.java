package org.example.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Singleton {
    private static Singleton instance;
    private Connection connection;
    private String url = "jdbc:postgresql://byteforge.ddns.net:5433/sienep";
    private String usuario = "proyecto";
    private String contrasenia = "Utec_2025*";

    private Singleton()  {
        try{
            this.connection = DriverManager.getConnection(url, usuario, contrasenia);
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public static Singleton getInstance() throws SQLException {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
