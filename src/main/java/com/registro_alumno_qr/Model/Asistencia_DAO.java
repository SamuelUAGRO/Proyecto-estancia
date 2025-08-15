package com.registro_alumno_qr.Model;

import java.sql.*;
import com.registro_alumno_qr.Config.ConexionDB;
import java.util.ArrayList;
import java.util.List;

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
    
    public List<Asistencia> obtenerAsistenciasPorMes(int mes, int anio) {
    List<Asistencia> lista = new ArrayList<>();
    String sql = """
        SELECT a.id, a.dia_asistencia, a.hora_entrada, a.hora_salida,
               al.id_alumno, al.matricula, al.nombre, al.a_paterno, al.a_materno
        FROM asistencias a
        INNER JOIN alumnos al ON a.alumno_id = al.id_alumno
        WHERE MONTH(a.dia_asistencia) = ? AND YEAR(a.dia_asistencia) = ?
        ORDER BY a.dia_asistencia DESC
    """;

    try (Connection con = ConexionDB.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, mes);
        ps.setInt(2, anio);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            // Creamos el alumno
            Alumno alumno = new Alumno();
            alumno.setId_alumno(rs.getInt("id_alumno"));
            alumno.setMatricula(rs.getString("matricula"));
            alumno.setNombre(rs.getString("nombre"));
            alumno.setA_paterno(rs.getString("a_paterno"));
            alumno.setA_materno(rs.getString("a_materno"));

            // Creamos la asistencia
            Asistencia asistencia = new Asistencia();
            asistencia.setId_asistencia(rs.getInt("id"));
            asistencia.setAlumno(alumno);
           asistencia.setDia_asistencia(rs.getDate("dia_asistencia").toLocalDate());
Time horaEntrada = rs.getTime("hora_entrada");
asistencia.setHora_entrada(horaEntrada != null ? horaEntrada.toLocalTime() : null);

Time horaSalida = rs.getTime("hora_salida");
asistencia.setHora_salida(horaSalida != null ? horaSalida.toLocalTime() : null);

            asistencia.setHora_salida(rs.getTime("hora_salida") != null ? rs.getTime("hora_salida").toLocalTime() : null);

            lista.add(asistencia);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}

public List<Asistencia> obtenerTodasAsistencias() {
    List<Asistencia> lista = new ArrayList<>();

    String sql = "SELECT a.id, a.dia_asistencia, a.hora_entrada, a.hora_salida, " +
                 "al.matricula, al.nombre " +
                 "FROM asistencias a " +
                 "INNER JOIN alumnos al ON a.alumno_id = al.id_alumno " +
                 "ORDER BY a.dia_asistencia DESC";

    try (Connection conn = ConexionDB.getConexion();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Asistencia asistencia = new Asistencia();

            // Datos de la asistencia
            asistencia.setId_asistencia(rs.getInt("id"));
            asistencia.setDia_asistencia(rs.getDate("dia_asistencia").toLocalDate());

            // Hora entrada y salida (mantener Timestamp)
            asistencia.setHora_entrada(rs.getTimestamp("hora_entrada"));
            asistencia.setHora_salida(rs.getTimestamp("hora_salida"));

            // Alumno relacionado
            Alumno alumno = new Alumno();
            alumno.setMatricula(rs.getString("matricula"));
            alumno.setNombre(rs.getString("nombre"));
            asistencia.setAlumno(alumno);

            lista.add(asistencia);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}


    
    
}

