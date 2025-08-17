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
import com.registro_alumno_qr.Model.Asistencia;
import com.registro_alumno_qr.Model.Asistencia_DAO;
import com.registro_alumno_qr.View.AgregarAlumno;
import com.registro_alumno_qr.View.VistaAsistencia;
import com.registro_alumno_qr.View.VistaCamara;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.registro_alumno_qr.Utils.GeneradorReportePDF;
import java.io.FileOutputStream;
import java.time.LocalDateTime;

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
    
        public AsistenciaControlador(VistaAsistencia vista, Asistencia_DAO asistenciaDAO) {
        this.vista = vista;
        this.asistenciaDAO = asistenciaDAO;
        
         this.vista.btnRegistrarAlumno.addActionListener(e -> abrirRegistroAlumno());
        this.vista.btnCamara.addActionListener(e -> abrirCamara());
          this.vista.btnGenerarReporte.addActionListener(e -> {
    System.out.println("Botón presionado");
    generarReportePDF();
});
          this.vista.btnGenerarReporteGeneral.addActionListener(e -> {
    System.out.println("Botón presionado");
    generarReportePDFGeneral();
});

        cargarAsistenciasEnTabla(); // cargamos al inicio
    }


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
   
   
   
   private void cargarAsistenciasEnTabla() {
    List<Asistencia> lista = asistenciaDAO.obtenerTodasAsistencias();

    String[] columnas = {"Matrícula", "Nombre", "Fecha", "Hora Entrada", "Hora Salida"};
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

    for (Asistencia asistencia : lista) {
        modelo.addRow(new Object[]{
            asistencia.getAlumno().getMatricula(),
            asistencia.getAlumno().getNombre(),
            asistencia.getDia_asistencia(),
            asistencia.getHora_entrada() != null ? asistencia.getHora_entrada() : "",
            asistencia.getHora_salida() != null ? asistencia.getHora_salida() : ""
        });
    }

    vista.setModeloTabla(modelo);
}

public void refrescarTabla() {
    cargarAsistenciasEnTabla();
}
  private void abrirRegistroAlumno() {
        AgregarAlumno vistaRegistro = new AgregarAlumno();
        Alumno_DAO dao = new Alumno_DAO();
        AlumnoControlador controladorRegistro = new AlumnoControlador(vistaRegistro, dao);

        vistaRegistro.setVisible(true);
        vista.dispose(); // Cierra la vista actual
    }

   
    private void abrirCamara() {
        VistaCamara vistaCamara = new VistaCamara();
        Asistencia_DAO dao = new Asistencia_DAO();
        CamaraControlador controladorCamara = new CamaraControlador(vistaCamara);
        vistaCamara.setVisible(true);
        vista.dispose();
    }
    
private void generarReportePDF() {
    String matricula = JOptionPane.showInputDialog(vista, "Ingrese la matrícula del alumno:");

    if (matricula == null || matricula.trim().isEmpty()) {
        JOptionPane.showMessageDialog(vista, "Matrícula inválida");
        return;
    }

    List<Asistencia> asistencias = asistenciaDAO.obtenerAsistenciasPorMatricula(matricula);

    if (asistencias.isEmpty()) {
        JOptionPane.showMessageDialog(vista, "No se encontraron asistencias para esta matrícula");
        return;
    }

    try {
        String carpeta = "C:\\Users\\samue\\OneDrive\\Desktop\\Proyeto estancia\\registro_alumno_qr\\pdfs\\";
String ruta = carpeta + "Reporte_Asistencia_" + matricula + ".pdf";
        GeneradorReportePDF generador = new GeneradorReportePDF(asistenciaDAO);
        generador.generarPDF(ruta, asistencias);

        JOptionPane.showMessageDialog(vista, "Reporte generado en: " + ruta);

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(vista, "Error al generar el PDF: " + ex.getMessage());
    }
}

private void generarReportePDFGeneral() {

    List<Asistencia> asistencias = asistenciaDAO.obtenerTodasAsistencias();

    if (asistencias.isEmpty()) {
        JOptionPane.showMessageDialog(vista, "No se encontraron asistencias para esta matrícula");
        return;
    }

    try {
        String carpeta = "C:\\Users\\samue\\OneDrive\\Desktop\\Proyeto estancia\\registro_alumno_qr\\pdfs\\";
String ruta = carpeta + "Reporte_Asistencia_" + "general" + ".pdf";
        GeneradorReportePDF generador = new GeneradorReportePDF(asistenciaDAO);
        generador.generarPDF(ruta, asistencias);

        JOptionPane.showMessageDialog(vista, "Reporte generado en: " + ruta);

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(vista, "Error al generar el PDF: " + ex.getMessage());
    }
}




}
