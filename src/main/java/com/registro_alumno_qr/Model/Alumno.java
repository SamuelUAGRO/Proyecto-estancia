/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro_alumno_qr.Model;

import java.sql.*;

public class Alumno {

    int id_alumno;
    String matricula;
    String nombre;
    String a_paterno;
    String a_materno;
    String email;
    String password;
    String qr;
    Timestamp creado_hora;
    Timestamp modificado_hora;

    public Alumno() {
    }

    public Alumno(int id_alumno, String matricula, String nombre, String a_paterno, String a_materno, String email, String password, String qr,  Timestamp creado_hora, Timestamp modificado_hora) {
        this.id_alumno = id_alumno;
        this.matricula = matricula;
        this.nombre = nombre;
        this.a_paterno = a_paterno;
        this.a_materno = a_materno;
        this.email = email;
        this.password = password;
        this.qr = qr;
        this.creado_hora = creado_hora;
        this.modificado_hora = modificado_hora;
    }

    public Alumno(String matricula, String nombre, String a_paterno, String a_materno, String email, String password, String qr,  Timestamp creado_hora, Timestamp modificado_hora) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.a_paterno = a_paterno;
        this.a_materno = a_materno;
        this.email = email;
        this.password = password;
        this.qr = qr;
        this.creado_hora = creado_hora;
        this.modificado_hora = modificado_hora;
    }

    public int getId_alumno() {
        return id_alumno;
    }

    public void setId_alumno(int id_alumno) {
        this.id_alumno = id_alumno;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getA_paterno() {
        return a_paterno;
    }

    public void setA_paterno(String a_paterno) {
        this.a_paterno = a_paterno;
    }

    public String getA_materno() {
        return a_materno;
    }

    public void setA_materno(String a_materno) {
        this.a_materno = a_materno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }


    public Timestamp getCreado_hora() {
        return creado_hora;
    }

    public void setCreado_hora(Timestamp creado_hora) {
        this.creado_hora = creado_hora;
    }

    public Timestamp getModificado_hora() {
        return modificado_hora;
    }

    public void setModificado_hora(Timestamp modificado_hora) {
        this.modificado_hora = modificado_hora;
    }

}
