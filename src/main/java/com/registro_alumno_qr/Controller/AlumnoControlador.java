
package com.registro_alumno_qr.Controller;

import com.registro_alumno_qr.Model.Alumno;
import com.registro_alumno_qr.Model.Alumno_DAO;
import com.registro_alumno_qr.View.AgregarAlumno;
import com.google.zxing.WriterException;
import com.registro_alumno_qr.Utils.HashGenerator;
import com.registro_alumno_qr.Utils.QrGenerator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;

public class AlumnoControlador implements ActionListener {
    
    private AgregarAlumno vista;
    private Alumno_DAO alumnoDAO;
    
      public AlumnoControlador(AgregarAlumno vista) {
        this.vista = vista;
        this.alumnoDAO = new Alumno_DAO();

        // Asignar el listener al botón
        this.vista.getBtnRegistrar().addActionListener(this);
    }
      
        
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnRegistrar()) {
            registrarAlumno();
        }
    }

private void registrarAlumno() {
    // Tomar datos de la vista
    String matricula = vista.getMatricula();
    String nombre = vista.getNombre();
    String aPaterno = vista.getApellidoP();
    String aMaterno = vista.getApellidoM();
    String email = vista.getEmail();
    String password = vista.getPassword();

    // Crear objeto Alumno
    Alumno alumno = new Alumno();
    alumno.setMatricula(matricula);
    alumno.setNombre(nombre);
    alumno.setA_paterno(aPaterno);
    alumno.setA_materno(aMaterno);
    alumno.setEmail(email);
    alumno.setPassword(password);
    
    if (alumnoDAO.existeMatricula(matricula)) {
    mostrarMensaje("Ya existe un alumno con esa matrícula");
    limpiarCampos();
    return;
}
    
    String hashQR = HashGenerator.generarSHA256(matricula);
    alumno.setQr(hashQR);

    // Generar ruta del QR (asegúrate de que la carpeta "qrs" exista)
    String rutaQR = "qrs/" + matricula + ".png";

    try {
        // Generar código QR y guardarlo en disco
        QrGenerator.generarQRCode(hashQR, rutaQR, 200, 200);

        alumno.setQr(hashQR);

    } catch (WriterException | IOException e) {
        e.printStackTrace();
        mostrarMensaje("Error al generar código QR");
        return; // Salir si hubo error en QR
    }

    // Insertar alumno en base de datos con la ruta del QR
    if (alumnoDAO.insertarAlumno(alumno)) {
        mostrarMensaje("Alumno registrado con éxito");
        limpiarCampos();
    } else {
        mostrarMensaje("Error al registrar alumno");
    }
}

    
      private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje);
    }

    private void limpiarCampos() {
        vista.setMatricula("");
        vista.setNombre("");
        vista.setApellidoP("");
        vista.setApellidoM("");
        vista.setEmail("");
        vista.setPassword("");
    }
    
}
