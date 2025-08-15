
package com.registro_alumno_qr.Model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;


public class Asistencia {
    int id_asistencia;
      private Alumno alumno;
    LocalDate dia_asistencia;
    Timestamp hora_entrada;
    Timestamp hora_salida;
    Timestamp hora_actualizacion;

    public Asistencia() {
    }
    
    

    public Asistencia(int id_asistencia, LocalDate dia_asistencia, Timestamp hora_entrada, Timestamp hora_salida, Timestamp hora_actualizacion) {
        this.id_asistencia = id_asistencia;
        this.dia_asistencia = dia_asistencia;
        this.hora_entrada = hora_entrada;
        this.hora_salida = hora_salida;
        this.hora_actualizacion = hora_actualizacion;
    }
    
    public Asistencia(LocalDate dia_asistencia, Timestamp hora_entrada, Timestamp hora_salida, Timestamp hora_actualizacion) {
        this.dia_asistencia = dia_asistencia;
        this.hora_entrada = hora_entrada;
        this.hora_salida = hora_salida;
        this.hora_actualizacion = hora_actualizacion;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }
    

    public int getId_asistencia() {
        return id_asistencia;
    }

    public void setId_asistencia(int id_asistencia) {
        this.id_asistencia = id_asistencia;
    }

    public LocalDate getDia_asistencia() {
        return dia_asistencia;
    }

    public void setDia_asistencia(LocalDate dia_asistencia) {
        this.dia_asistencia = dia_asistencia;
    }

    public Timestamp getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(Timestamp hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public Timestamp getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(Timestamp hora_salida) {
        this.hora_salida = hora_salida;
    }

    public Timestamp getHora_actualizacion() {
        return hora_actualizacion;
    }

    public void setHora_actualizacion(Timestamp hora_actualizacion) {
        this.hora_actualizacion = hora_actualizacion;
    }

    
    
            
}//clase



