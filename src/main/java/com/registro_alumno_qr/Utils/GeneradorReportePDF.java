
package com.registro_alumno_qr.Utils;

import com.registro_alumno_qr.Model.Alumno;
import com.registro_alumno_qr.Model.Asistencia;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.registro_alumno_qr.Entities.ReporteAsistencia;
import com.registro_alumno_qr.Model.Asistencia_DAO;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.time.LocalTime;

public class GeneradorReportePDF {

    private Asistencia_DAO asistenciaDAO;

    public GeneradorReportePDF(Asistencia_DAO asistenciaDAO) {
        this.asistenciaDAO = asistenciaDAO;
    }

    public void generarPDF(String rutaArchivo, List<Asistencia> listaAsistencias) {
        Document document = new Document(PageSize.A4, 40, 40, 50, 50);
        try {
            PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
            document.open();

            // ================== FUENTES ==================
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

            // ================== TITULO ==================
            Paragraph titulo = new Paragraph("Reporte de Asistencias", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph("Generado el: " + java.time.LocalDate.now(), normalFont));
            document.add(new Paragraph(" ")); // espacio

            // ================== TABLA ASISTENCIAS ==================
            PdfPTable table = new PdfPTable(6); // Matrícula, Nombre, Fecha, Entrada, Salida, Minutos
            table.setWidthPercentage(100);
            table.setSpacingBefore(15f);
            table.setSpacingAfter(15f);
            table.setWidths(new float[]{2f, 3f, 2.5f, 2.5f, 2.5f, 2f}); // proporción de columnas

            // Encabezados
            String[] encabezados = {"Matrícula", "Nombre", "Fecha", "Hora Entrada", "Hora Salida", "Duración (min)"};
            for (String encabezado : encabezados) {
                PdfPCell cell = new PdfPCell(new Phrase(encabezado, headerFont));
                cell.setBackgroundColor(BaseColor.GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8f);
                table.addCell(cell);
            }

            // Filas de datos
            for (Asistencia asistencia : listaAsistencias) {
                Alumno alumno = asistencia.getAlumno();

                table.addCell(new PdfPCell(new Phrase(alumno.getMatricula(), normalFont)));
                table.addCell(new PdfPCell(new Phrase(alumno.getNombre(), normalFont)));
                table.addCell(new PdfPCell(new Phrase(asistencia.getDia_asistencia().toString(), normalFont)));

                Timestamp entradaTimestamp = asistencia.getHora_entrada();
                Timestamp salidaTimestamp = asistencia.getHora_salida();

                // Hora Entrada
                table.addCell(new PdfPCell(new Phrase(
                        entradaTimestamp != null ? entradaTimestamp.toLocalDateTime().toLocalTime().toString() : "",
                        normalFont)));

                // Hora Salida
                table.addCell(new PdfPCell(new Phrase(
                        salidaTimestamp != null ? salidaTimestamp.toLocalDateTime().toLocalTime().toString() : "",
                        normalFont)));

                // Duración en minutos
                if (entradaTimestamp != null && salidaTimestamp != null) {
                    LocalDateTime entrada = entradaTimestamp.toLocalDateTime();
                    LocalDateTime salida = salidaTimestamp.toLocalDateTime();
                    long minutos = java.time.Duration.between(entrada, salida).toMinutes();
                    table.addCell(new PdfPCell(new Phrase(minutos + " min", normalFont)));
                } else {
                    table.addCell(new PdfPCell(new Phrase("", normalFont)));
                }
            }

            document.add(table);

            // ================== RESUMEN ESTADÍSTICO ==================
            ReporteAsistencia reporte = new ReporteAsistencia();

            double promedioSemanal = reporte.calcularPromedioHorasSemana(listaAsistencias);
            double promedioMensual = reporte.calcularPromedioHorasMes(listaAsistencias);
            LocalTime llegadaFrecuente = reporte.horaLlegadaFrecuente(listaAsistencias);
            LocalTime salidaFrecuente = reporte.horaSalidaFrecuente(listaAsistencias);

            Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
            Paragraph resumenTitulo = new Paragraph("Resumen de Asistencia", fontSubtitulo);
            resumenTitulo.setSpacingBefore(20);
            resumenTitulo.setSpacingAfter(10);
            document.add(resumenTitulo);

            PdfPTable resumenTable = new PdfPTable(2);
            resumenTable.setWidthPercentage(80);
            resumenTable.setWidths(new float[]{3f, 2f});
            resumenTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            resumenTable.setSpacingBefore(10);
            resumenTable.setSpacingAfter(15);

            // Encabezados resumen
            PdfPCell cellMetrica = new PdfPCell(new Phrase("Métrica", headerFont));
            cellMetrica.setBackgroundColor(BaseColor.GRAY);
            cellMetrica.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellMetrica.setPadding(8);

            PdfPCell cellValor = new PdfPCell(new Phrase("Valor", headerFont));
            cellValor.setBackgroundColor(BaseColor.GRAY);
            cellValor.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellValor.setPadding(8);

            resumenTable.addCell(cellMetrica);
            resumenTable.addCell(cellValor);

            // Filas resumen
            resumenTable.addCell(new PdfPCell(new Phrase("Promedio de horas semanales", normalFont)));
            resumenTable.addCell(new PdfPCell(new Phrase(String.format("%.2f h", promedioSemanal), normalFont)));

            resumenTable.addCell(new PdfPCell(new Phrase("Promedio de horas mensuales", normalFont)));
            resumenTable.addCell(new PdfPCell(new Phrase(String.format("%.2f h", promedioMensual), normalFont)));

            resumenTable.addCell(new PdfPCell(new Phrase("Hora de llegada más frecuente", normalFont)));
            resumenTable.addCell(new PdfPCell(new Phrase(llegadaFrecuente != null ? llegadaFrecuente.toString() : "N/A", normalFont)));

            resumenTable.addCell(new PdfPCell(new Phrase("Hora de salida más frecuente", normalFont)));
            resumenTable.addCell(new PdfPCell(new Phrase(salidaFrecuente != null ? salidaFrecuente.toString() : "N/A", normalFont)));

            document.add(resumenTable);

            // ================== CIERRE ==================
            document.close();
            System.out.println("✅ PDF generado correctamente en: " + rutaArchivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
