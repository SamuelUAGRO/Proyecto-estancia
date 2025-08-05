
package com.registro_alumno_qr.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
      private static Connection conexion = null;
    private static final String URL = "jdbc:mysql://localhost:3306/registrar_alumno_qr";
    private static final String USER = "root";
    private static final String PASSWORD = "16100";

    public static Connection getConexion() {
        if (conexion == null) {
            try {
                conexion = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión exitosa");
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return conexion;
    }

    public static void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                conexion = null;
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    
}
