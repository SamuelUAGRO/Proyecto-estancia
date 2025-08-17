package com.registro_alumno_qr.Model;

import java.sql.*;
import com.registro_alumno_qr.Config.ConexionDB;
import java.util.ArrayList;
import java.util.List;

public class Asistencia_DAO {

    // Registrar entrada de un alumno
    public boolean registrarEntrada(int idAlumno) {
        String sql = "INSERT INTO asistencias (dia_asistencia, hora_entrada, hora_actualizacion, alumno_id) "
                   + "SELECT ?, ?, ?, ? FROM DUAL "
                   + "WHERE NOT EXISTS ("
                   + "  SELECT 1 FROM asistencias WHERE alumno_id = ? AND dia_asistencia = CURRENT_DATE"
                   + ")";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            Timestamp ahora = new Timestamp(System.currentTimeMillis());
            stmt.setDate(1, new java.sql.Date(ahora.getTime()));
            stmt.setTimestamp(2, ahora);
            stmt.setTimestamp(3, ahora);
            stmt.setInt(4, idAlumno);
            stmt.setInt(5, idAlumno);

            int filas = stmt.executeUpdate();
            return filas > 0;

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
            stmt.setTimestamp(1, ahora);
            stmt.setTimestamp(2, ahora);
            stmt.setInt(3, idAlumno);

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener todas las asistencias
    public List<Asistencia> obtenerTodasAsistencias() {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT a.id, a.dia_asistencia, a.hora_entrada, a.hora_salida, "
                   + "al.id_alumno, al.matricula, al.nombre, al.a_paterno, al.a_materno "
                   + "FROM asistencias a "
                   + "JOIN alumnos al ON a.alumno_id = al.id_alumno "
                   + "ORDER BY a.dia_asistencia DESC";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Asistencia asistencia = new Asistencia();
                asistencia.setId_asistencia(rs.getInt("id"));
                asistencia.setDia_asistencia(rs.getDate("dia_asistencia").toLocalDate());
                asistencia.setHora_entrada(rs.getTimestamp("hora_entrada")); // Timestamp
                asistencia.setHora_salida(rs.getTimestamp("hora_salida"));   // Timestamp

                Alumno alumno = new Alumno();
                alumno.setId_alumno(rs.getInt("id_alumno"));
                alumno.setMatricula(rs.getString("matricula"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setA_paterno(rs.getString("a_paterno"));
                alumno.setA_materno(rs.getString("a_materno"));

                asistencia.setAlumno(alumno);
                lista.add(asistencia);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Obtener asistencias por matrícula
    public List<Asistencia> obtenerAsistenciasPorMatricula(String matricula) {
        List<Asistencia> lista = new ArrayList<>();
        String sql = "SELECT a.id, a.dia_asistencia, a.hora_entrada, a.hora_salida, "
                   + "al.id_alumno, al.matricula, al.nombre, al.a_paterno, al.a_materno "
                   + "FROM asistencias a "
                   + "JOIN alumnos al ON a.alumno_id = al.id_alumno "
                   + "WHERE al.matricula = ? "
                   + "ORDER BY a.dia_asistencia DESC, a.hora_entrada ASC";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Asistencia asistencia = new Asistencia();
                    asistencia.setId_asistencia(rs.getInt("id"));
                    asistencia.setDia_asistencia(rs.getDate("dia_asistencia").toLocalDate());
                    asistencia.setHora_entrada(rs.getTimestamp("hora_entrada")); // Timestamp
                    asistencia.setHora_salida(rs.getTimestamp("hora_salida"));   // Timestamp

                    Alumno alumno = new Alumno();
                    alumno.setId_alumno(rs.getInt("id_alumno"));
                    alumno.setMatricula(rs.getString("matricula"));
                    alumno.setNombre(rs.getString("nombre"));
                    alumno.setA_paterno(rs.getString("a_paterno"));
                    alumno.setA_materno(rs.getString("a_materno"));

                    asistencia.setAlumno(alumno);
                    lista.add(asistencia);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
