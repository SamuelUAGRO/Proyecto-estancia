package com.registro_alumno_qr;

import com.registro_alumno_qr.Controller.AlumnoControlador;
import com.registro_alumno_qr.Controller.CamaraControlador;
import com.registro_alumno_qr.Model.Alumno_DAO;
import com.registro_alumno_qr.View.AgregarAlumno;
import com.registro_alumno_qr.View.VistaCamara;


public class Registro_alumno_qr {

    public static void main(String[] args) {
             VistaCamara vista = new VistaCamara();
        CamaraControlador controlador = new CamaraControlador(vista);
        vista.setVisible(true);
       /* AgregarAlumno vista = new AgregarAlumno();
        Alumno_DAO dao = new Alumno_DAO();
        AlumnoControlador controlador = new AlumnoControlador(vista);

        vista.setVisible(true);*/
    }
}
