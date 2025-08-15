package com.registro_alumno_qr.Model;

import java.sql.*;
import com.registro_alumno_qr.Config.ConexionDB;

public class Asistencia_DAO {

     public boolean registrarEntrada(int idAlumno) {
    String sql = "INSERT INTO asistencias (dia_asistencia, hora_entrada, hora_actualizacion, alumno_id) " +
                 "SELECT ?, ?, ?, ? FROM DUAL " +
                 "WHERE NOT EXISTS (" +
                 "  SELECT 1 FROM asistencias WHERE alumno_id = ? AND dia_asistencia = CURRENT_DATE" +
                 ")";
    try (Connection conn = ConexionDB.getConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        Timestamp ahora = new Timestamp(System.currentTimeMillis());
        stmt.setDate(1, new java.sql.Date(ahora.getTime()));
        stmt.setTimestamp(2, ahora);
        stmt.setTimestamp(3, ahora);
        stmt.setInt(4, idAlumno);
        stmt.setInt(5, idAlumno);

        int filas = stmt.executeUpdate();
        return filas > 0; // true si se creó un registro nuevo

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    // Registrar hora de salida de un alumno
    public boolean registrarSalida(int idAlumno) {
        String sql = "UPDATE asistencias SET hora_salida = ?, hora_actualizacion = ? "
                   + "WHERE alumno_id = ? AND dia_asistencia = CURRENT_DATE";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Timestamp ahora = new Timestamp(System.currentTimeMillis());

            stmt.setTimestamp(1, ahora); // hora_salida
            stmt.setTimestamp(2, ahora); // hora_actualizacion
            stmt.setInt(3, idAlumno);

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
  
    
    
}

