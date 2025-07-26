
package com.registro_alumno_qr.Model;

import java.sql.*;
import com.registro_alumno_qr.Model.Alumno;
import com.registro_alumno_qr.Config.ConexionDB;
import java.util.ArrayList;
import java.util.List;

public class Alumno_DAO {
    
    public boolean insertarAlumno(Alumno alumno) {
    String sql = "INSERT INTO alumnos (id_alumno, email, password, nombre, a_paterno, a_materno, huella_digital,clave_unica, email_verificado_hora, creado_hora, modificado_hora)"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    

    try (Connection conn = ConexionDB.getConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, alumno.getId_alumno());
        stmt.setString(2, alumno.getEmail());
        stmt.setString(3, alumno.getPassword());
        stmt.setString(4, alumno.getNombre());
        stmt.setString(5, alumno.getA_paterno());
        stmt.setString(6, alumno.getA_materno());
        stmt.setString(7, alumno.getHuella_digital());
        stmt.setString(8, alumno.getClave_unica());
        stmt.setTimestamp(9, alumno.getEmail_verificado_hora());
        stmt.setTimestamp(10, alumno.getCreado_hora());
        stmt.setTimestamp(11, alumno.getModificado_hora());

        stmt.executeUpdate();
        return true;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    
    public List<Alumno> obtenerTodosLosAlumnos() {
    List<Alumno> lista = new ArrayList<>();
    String sql = "SELECT * FROM alumnos";

    try (Connection conn = ConexionDB.getConexion();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Alumno alumno = new Alumno();
            alumno.setId_alumno(rs.getInt("id_alumno"));
            alumno.setEmail(rs.getString("email"));
            alumno.setPassword(rs.getString("password"));
            alumno.setNombre(rs.getString("nombre"));
            alumno.setA_paterno(rs.getString("a_paterno"));
            alumno.setA_materno(rs.getString("a_materno"));
            alumno.setHuella_digital(rs.getString("huella_digital"));
            alumno.setClave_unica(rs.getString("clave_unica"));
            alumno.setEmail_verificado_hora(rs.getTimestamp("email_verificado_hora"));
            alumno.setCreado_hora(rs.getTimestamp("creado_hora"));
            alumno.setModificado_hora(rs.getTimestamp("modificado_hora"));
            lista.add(alumno);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}
public boolean actualizarAlumno(Alumno alumno) {
    String sql = "UPDATE alumnos SET email=?, password=?, nombre=?, a_paterno=?, a_materno=?, huella_digital=?, clave_unica=?, email_verificado_hora=?, creado_hora=?, modificado_hora=? WHERE id_alumno=?";

    try (Connection conn = ConexionDB.getConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, alumno.getEmail());
        stmt.setString(2, alumno.getPassword());
        stmt.setString(3, alumno.getNombre());
        stmt.setString(4, alumno.getA_paterno());
        stmt.setString(5, alumno.getA_materno());
        stmt.setString(6, alumno.getHuella_digital());
        stmt.setString(7, alumno.getClave_unica());
        stmt.setTimestamp(8, alumno.getEmail_verificado_hora());
        stmt.setTimestamp(9, alumno.getCreado_hora());
        stmt.setTimestamp(10, alumno.getModificado_hora());
        stmt.setInt(11, alumno.getId_alumno());

        int filas = stmt.executeUpdate();
        return filas > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public boolean eliminarAlumno(int idAlumno) {
    String sql = "DELETE FROM alumnos WHERE id_alumno=?";

    try (Connection conn = ConexionDB.getConexion();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idAlumno);
        int filas = stmt.executeUpdate();
        return filas > 0;

    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


}
