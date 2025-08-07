package com.registro_alumno_qr.Model;

import java.sql.*;
import com.registro_alumno_qr.Config.ConexionDB;

public class Asistencia_DAO {

    public boolean insertarAsistencia(Asistencia asistencia) {
        String sql = "INSERT INTO asistencia (dia_asistencia, hora_entrada, hora_salida, hora_actualizacion) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, asistencia.getDia_asistencia());
            stmt.setTimestamp(2, asistencia.getHora_entrada());
            stmt.setTimestamp(3, asistencia.getHora_salida());
            stmt.setTimestamp(4, asistencia.getHora_actualizacion());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Asistencia obtenerAsistenciaPorID(int idAsistencia) {
        String sql = "SELECT * FROM asistencia WHERE id_asistencia = ?";
        Asistencia asistencia = null;

        try (Connection conn = ConexionDB.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAsistencia);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                asistencia = new Asistencia();
                asistencia.setId_asistencia(rs.getInt("id_asistencia"));
                asistencia.setDia_asistencia(rs.getDate("dia_asistencia"));  // 👈 aquí usamos Date
                asistencia.setHora_entrada(rs.getTimestamp("hora_entrada"));
                asistencia.setHora_salida(rs.getTimestamp("hora_salida"));
                asistencia.setHora_actualizacion(rs.getTimestamp("hora_actualizacion"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return asistencia;
    }

    public boolean actualizarAsistencia(Asistencia asistencia) {
        String sql = "UPDATE asistencia SET dia_asistencia = ?, hora_entrada = ?, hora_salida = ?, hora_actualizacion = ? WHERE id_asistencia = ?";

        try (Connection conn = ConexionDB.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, asistencia.getDia_asistencia());
            stmt.setTimestamp(2, asistencia.getHora_entrada());
            stmt.setTimestamp(3, asistencia.getHora_salida());
            stmt.setTimestamp(4, asistencia.getHora_actualizacion());
            stmt.setInt(5, asistencia.getId_asistencia());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarAsistencia(int idAsistencia) {
        String sql = "DELETE FROM asistencia WHERE id_asistencia = ?";

        try (Connection conn = ConexionDB.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idAsistencia);
            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

