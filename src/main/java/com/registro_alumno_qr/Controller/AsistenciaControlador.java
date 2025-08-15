/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.registro_alumno_qr.Controller;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.registro_alumno_qr.Model.Alumno;
import com.registro_alumno_qr.Model.Alumno_DAO;
import com.registro_alumno_qr.Model.Asistencia_DAO;
import com.registro_alumno_qr.View.VistaAsistencia;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author samuel antonio
 */
public class AsistenciaControlador {

    private Alumno_DAO alumnoDAO;
    private Asistencia_DAO asistenciaDAO;
    private VistaAsistencia vista;
    private Map<Integer, Long> ultimoRegistro = new HashMap<>();
    private static final long COOLDOWN_MS = 60_000; // 1 minuto en milisegundos

    public AsistenciaControlador(Alumno_DAO alumnoDAO, Asistencia_DAO asistenciaDAO, VistaAsistencia vista) {
        this.alumnoDAO = alumnoDAO;
        this.asistenciaDAO = asistenciaDAO;
        this.vista = vista;
    }

   public String procesarQR(BufferedImage image) throws NotFoundException {

    LuminanceSource source = new BufferedImageLuminanceSource(image);
    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
    Result result = new MultiFormatReader().decode(bitmap);
    String qrHash = result.getText();

    Alumno alumno = alumnoDAO.obtenerAlumnoPorQR(qrHash);
    if (alumno == null) {
        return "QR no corresponde a ningún alumno";
    }

    int idAlumno = alumno.getId_alumno();

    // Intentamos registrar entrada
    boolean entradaRegistrada = asistenciaDAO.registrarEntrada(idAlumno);
    if (entradaRegistrada) {
        return "Entrada registrada para: " + alumno.getNombre();
    }

    // Si ya existe registro de entrada hoy, actualizamos la hora de salida
    boolean salidaRegistrada = asistenciaDAO.registrarSalida(idAlumno);
    if (salidaRegistrada) {
        return "Hora de salida actualizada para: " + alumno.getNombre();
    }

    // Si no pudo registrar entrada ni salida
    return "Ya se registró asistencia hoy para: " + alumno.getNombre();
}

}
