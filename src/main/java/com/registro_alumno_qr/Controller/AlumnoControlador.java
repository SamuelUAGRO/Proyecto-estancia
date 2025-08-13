
package com.registro_alumno_qr.Controller;

import com.registro_alumno_qr.Model.Alumno;
import com.registro_alumno_qr.Model.Alumno_DAO;
import com.registro_alumno_qr.View.AgregarAlumno;
import com.google.zxing.WriterException;
import com.registro_alumno_qr.Utils.HashGenerator;
import com.registro_alumno_qr.Utils.QrGenerator;
import com.registro_alumno_qr.View.VistaCamara;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;

public class AlumnoControlador implements ActionListener {

    private AgregarAlumno vista;
    private Alumno_DAO alumnoDAO;

    public AlumnoControlador(AgregarAlumno vista, Alumno_DAO dao) {
        this.vista = vista;
        this.alumnoDAO = dao; // Asignar el DAO recibido
        initEventos();
    }

    private void initEventos() {
        this.vista.getBtnRegistrar().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnRegistrar()) {
            registrarAlumno();
        }
    }

    private void registrarAlumno() {
        String matricula = vista.getMatricula();
        String nombre = vista.getNombre();
        String aPaterno = vista.getApellidoP();
        String aMaterno = vista.getApellidoM();
        String email = vista.getEmail();
        String password = vista.getPassword();

        // Validar que no haya campos vacíos
        if (matricula.isEmpty() || nombre.isEmpty() || aPaterno.isEmpty() || 
            aMaterno.isEmpty() || email.isEmpty() || password.isEmpty()) {
            mostrarMensaje("Todos los campos son obligatorios");
            return;
        }

        // Verificar si ya existe
        if (alumnoDAO.existeMatricula(matricula)) {
            mostrarMensaje("Ya existe un alumno con esa matrícula");
            limpiarCampos();
            return;
        }

        Alumno alumno = new Alumno();
        alumno.setMatricula(matricula);
        alumno.setNombre(nombre);
        alumno.setA_paterno(aPaterno);
        alumno.setA_materno(aMaterno);
        alumno.setEmail(email);
        alumno.setPassword(password);

        // Generar hash y QR
        String hashQR = HashGenerator.generarSHA256(matricula);
        alumno.setQr(hashQR);
        String rutaQR = "qrs/" + matricula + ".png";

        try {
            QrGenerator.generarQRCode(hashQR, rutaQR, 200, 200);
        } catch (WriterException | IOException ex) {
            ex.printStackTrace();
            mostrarMensaje("Error al generar código QR");
            return;
        }

        // Guardar en base de datos
        if (alumnoDAO.insertarAlumno(alumno)) {
            mostrarMensaje("Alumno registrado con éxito");
            limpiarCampos();
               vista.dispose();

    // Volver a abrir la cámara
    VistaCamara vistaCamara = new VistaCamara();
    CamaraControlador camaraControlador = new CamaraControlador(vistaCamara);
    vistaCamara.setVisible(true);
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

