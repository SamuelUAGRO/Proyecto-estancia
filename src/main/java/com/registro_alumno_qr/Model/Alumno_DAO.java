package com.registro_alumno_qr.Model;

import java.sql.*;
import com.registro_alumno_qr.Model.Alumno;
import com.registro_alumno_qr.Config.ConexionDB;


public class Alumno_DAO {

    public boolean insertarAlumno(Alumno alumno) {
        String sql = "INSERT INTO alumnos (matricula, nombre, a_paterno, a_materno, email, password, qr ) "
                + "VALUES (?, ?, ?, ?, ?, ?, ? )";

        try ( Connection conn = ConexionDB.getConexion();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, alumno.getMatricula());
            stmt.setString(2, alumno.getNombre());
            stmt.setString(3, alumno.getA_paterno());
            stmt.setString(4, alumno.getA_materno());
            stmt.setString(5, alumno.getEmail());
            stmt.setString(6, alumno.getPassword());
            stmt.setString(7, alumno.getQr());

            stmt.executeUpdate();
            return true;
            
            } catch (SQLIntegrityConstraintViolationException e) {
        System.out.println("Error: Matrícula duplicada");
        return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
      
    }

    public Alumno obtenerAlumnoPorID(int idAlumno) {
        String sql = "SELECT * FROM alumnos WHERE id_alumno = ?";
        Alumno alumno = null;

        try ( Connection conn = ConexionDB.getConexion();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAlumno);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                alumno = new Alumno();
                alumno.setId_alumno(rs.getInt("id_alumno"));
                alumno.setMatricula(rs.getString("matricula"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setA_paterno(rs.getString("a_paterno"));
                alumno.setA_materno(rs.getString("a_materno"));
                alumno.setEmail(rs.getString("email"));
                alumno.setPassword(rs.getString("password"));
                alumno.setQr(rs.getString("qr"));
                alumno.setCreado_hora(rs.getTimestamp("creado_hora"));
                alumno.setModificado_hora(rs.getTimestamp("modificado_hora"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alumno;
    }

    public boolean actualizarAlumno(Alumno alumno) {
        String sql = "UPDATE alumnos SET matricula = ?, nombre = ?, a_paterno = ?, a_materno = ?, email = ?, password = ?, qr = ?, modificado_hora = ? "
                + "WHERE id_alumno = ?";

        try ( Connection conn = ConexionDB.getConexion();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, alumno.getMatricula());
            stmt.setString(2, alumno.getNombre());
            stmt.setString(3, alumno.getA_paterno());
            stmt.setString(4, alumno.getA_materno());
            stmt.setString(5, alumno.getEmail());
            stmt.setString(6, alumno.getPassword());
            stmt.setString(7, alumno.getQr());
            stmt.setTimestamp(8, alumno.getModificado_hora());
            stmt.setInt(9, alumno.getId_alumno());

            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarAlumno(int idAlumno) {
        String sql = "DELETE FROM alumnos WHERE id_alumno = ?";

        try ( Connection conn = ConexionDB.getConexion();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAlumno);
            int filas = stmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean existeMatricula(String matricula) {
    String sql = "SELECT 1 FROM alumnos WHERE matricula = ?";
    try (Connection conn = ConexionDB.getConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setString(1, matricula);
        ResultSet rs = stmt.executeQuery();
        return rs.next(); // true si ya existe
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
       public Alumno obtenerAlumnoPorQR(String qrHash) {
        String sql = "SELECT * FROM alumnos WHERE QR = ?";
        Alumno alumno = null;

        try ( Connection conn = ConexionDB.getConexion();  PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, qrHash);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                alumno = new Alumno();
                alumno.setId_alumno(rs.getInt("id_alumno"));
                alumno.setMatricula(rs.getString("matricula"));
                alumno.setNombre(rs.getString("nombre"));
                alumno.setA_paterno(rs.getString("a_paterno"));
                alumno.setA_materno(rs.getString("a_materno"));
                alumno.setEmail(rs.getString("email"));
                alumno.setPassword(rs.getString("password"));
                alumno.setQr(rs.getString("qr"));
                alumno.setCreado_hora(rs.getTimestamp("creado_hora"));
                alumno.setModificado_hora(rs.getTimestamp("modificado_hora"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return alumno;
    }

}
