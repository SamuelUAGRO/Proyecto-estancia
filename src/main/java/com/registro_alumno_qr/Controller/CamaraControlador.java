package com.registro_alumno_qr.Controller;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.registro_alumno_qr.Model.Alumno_DAO;
import com.registro_alumno_qr.Model.Asistencia_DAO;
import com.registro_alumno_qr.View.AgregarAlumno;
import com.registro_alumno_qr.View.VistaAsistencia;
import com.registro_alumno_qr.View.VistaCamara;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

public class CamaraControlador {

    private VistaCamara vista;
    private Webcam webcam;
    private volatile boolean running = false;
    private Alumno_DAO alumnoDAO;
    private Asistencia_DAO asistenciaDAO;
    private VistaAsistencia vistaAsistencia;
    private AsistenciaControlador asistenciaControlador;

    public CamaraControlador(VistaCamara vista) {
        this.vista = vista;

        // Inicializamos DAOs
        alumnoDAO = new Alumno_DAO();
        asistenciaDAO = new Asistencia_DAO();

        // Creamos controlador de asistencia
        asistenciaControlador = new AsistenciaControlador(alumnoDAO, asistenciaDAO, vistaAsistencia);

        iniciarCamara();
        initEventos();
    }

    private void iniciarCamara() {
        webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));
        webcam.open();

        running = true;
        new Thread(() -> {
            while (running && webcam.isOpen()) {
                BufferedImage image = webcam.getImage();
                if (image != null) {
                    try {
                        String mensaje = asistenciaControlador.procesarQR(image);

                        vista.mostrarTextoQR(mensaje);
                    } catch (NotFoundException e) {
                        vista.mostrarTextoQR(""); // No QR encontrado
                    }
                    vista.mostrarImagen(image);
                }
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void initEventos() {
        vista.getBtnRegistrarAlumno().addActionListener(e -> abrirRegistroAlumno());
        vista.getBtnAsistencias().addActionListener(e -> abrirVistaAsistencias());

    }

    public void detenerCamara() {
        running = false;
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
        }
    }

    private void abrirRegistroAlumno() {
        detenerCamara();

        AgregarAlumno vistaRegistro = new AgregarAlumno();
        Alumno_DAO dao = new Alumno_DAO();
        AlumnoControlador controladorRegistro = new AlumnoControlador(vistaRegistro, dao);

        vistaRegistro.setVisible(true);
        vista.dispose();
    }

    private void abrirVistaAsistencias() {
       detenerCamara();

    if (vistaAsistencia == null) { // si no existe, crearla
        vistaAsistencia = new VistaAsistencia();
        Asistencia_DAO asistenciaDAO = new Asistencia_DAO();
        new AsistenciaControlador(vistaAsistencia, asistenciaDAO);
    }

    vistaAsistencia.setVisible(true);
    vista.dispose();
    }
}
