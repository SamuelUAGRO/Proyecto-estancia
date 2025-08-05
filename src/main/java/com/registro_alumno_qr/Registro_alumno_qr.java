package com.registro_alumno_qr;

import com.registro_alumno_qr.Controller.AlumnoControlador;
import com.registro_alumno_qr.Model.Alumno_DAO;
import com.registro_alumno_qr.View.AgregarAlumno;


public class Registro_alumno_qr {

    public static void main(String[] args) {
          AgregarAlumno vista = new AgregarAlumno();
        Alumno_DAO dao = new Alumno_DAO();
        AlumnoControlador controlador = new AlumnoControlador(vista);

        vista.setVisible(true);
    }
}
