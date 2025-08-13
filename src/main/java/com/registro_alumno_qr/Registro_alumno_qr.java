package com.registro_alumno_qr;

import com.registro_alumno_qr.Controller.AlumnoControlador;
import com.registro_alumno_qr.Controller.CamaraControlador;
import com.registro_alumno_qr.Model.Alumno_DAO;
import com.registro_alumno_qr.View.AgregarAlumno;
import com.registro_alumno_qr.View.VistaCamara;


public class Registro_alumno_qr {

    public static void main(String[] args) {
                 VistaCamara vistaCamara = new VistaCamara();
    CamaraControlador controladorCamara = new CamaraControlador(vistaCamara);
    vistaCamara.setVisible(true);
    }
}
