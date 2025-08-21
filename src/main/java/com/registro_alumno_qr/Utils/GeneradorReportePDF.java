
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
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
    
    public void generarPDFGeneral(String rutaArchivo, List<Asistencia> listaAsistencias) {
    Document document = new Document(PageSize.A4, 40, 40, 50, 50);
    try {
        PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
        document.open();

        Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

        // ================== TITULO ==================
        Paragraph titulo = new Paragraph("Reporte General de Asistencias", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        document.add(new Paragraph("Generado el: " + java.time.LocalDate.now(), normalFont));
        document.add(new Paragraph(" "));

        // ================== TABLA ==================
        PdfPTable table = new PdfPTable(7); // + columna promedio
        table.setWidthPercentage(100);
        table.setSpacingBefore(15f);
        table.setSpacingAfter(15f);
        table.setWidths(new float[]{2f, 3f, 2.5f, 2.5f, 2.5f, 2f, 2.5f}); 

        String[] encabezados = {"Matrícula", "Nombre", "Fecha", "Hora Entrada", "Hora Salida", "Duración (min)", "Promedio Horas"};
        for (String encabezado : encabezados) {
            PdfPCell cell = new PdfPCell(new Phrase(encabezado, headerFont));
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8f);
            table.addCell(cell);
        }

        // ================== PROMEDIO POR ALUMNO ==================
        Map<String, Double> promedioHorasPorAlumno = new HashMap<>();

        Map<String, List<Asistencia>> asistenciasPorAlumno = listaAsistencias.stream()
                .collect(Collectors.groupingBy(a -> a.getAlumno().getMatricula()));

        for (Map.Entry<String, List<Asistencia>> entry : asistenciasPorAlumno.entrySet()) {
            List<Asistencia> asistenciasAlumno = entry.getValue();

            long totalMinutos = 0;
            int conteo = 0;
            for (Asistencia a : asistenciasAlumno) {
                if (a.getHora_entrada() != null && a.getHora_salida() != null) {
                    LocalDateTime entrada = a.getHora_entrada().toLocalDateTime();
                    LocalDateTime salida = a.getHora_salida().toLocalDateTime();
                    totalMinutos += java.time.Duration.between(entrada, salida).toMinutes();
                    conteo++;
                }
            }
            double promedioHoras = conteo > 0 ? (totalMinutos / 60.0) / conteo : 0;
            promedioHorasPorAlumno.put(entry.getKey(), promedioHoras);
        }

        // ================== FILAS ==================
        for (Asistencia asistencia : listaAsistencias) {
            Alumno alumno = asistencia.getAlumno();

            table.addCell(new PdfPCell(new Phrase(alumno.getMatricula(), normalFont)));
            table.addCell(new PdfPCell(new Phrase(alumno.getNombre(), normalFont)));
            table.addCell(new PdfPCell(new Phrase(asistencia.getDia_asistencia().toString(), normalFont)));

            Timestamp entradaTimestamp = asistencia.getHora_entrada();
            Timestamp salidaTimestamp = asistencia.getHora_salida();

            table.addCell(new PdfPCell(new Phrase(
                    entradaTimestamp != null ? entradaTimestamp.toLocalDateTime().toLocalTime().toString() : "",
                    normalFont)));

            table.addCell(new PdfPCell(new Phrase(
                    salidaTimestamp != null ? salidaTimestamp.toLocalDateTime().toLocalTime().toString() : "",
                    normalFont)));

            if (entradaTimestamp != null && salidaTimestamp != null) {
                LocalDateTime entrada = entradaTimestamp.toLocalDateTime();
                LocalDateTime salida = salidaTimestamp.toLocalDateTime();
                long minutos = java.time.Duration.between(entrada, salida).toMinutes();
                table.addCell(new PdfPCell(new Phrase(minutos + " min", normalFont)));
            } else {
                table.addCell(new PdfPCell(new Phrase("", normalFont)));
            }

            // Promedio horas por alumno
            double promedio = promedioHorasPorAlumno.get(alumno.getMatricula());
            table.addCell(new PdfPCell(new Phrase(String.format("%.2f h", promedio), normalFont)));
        }

        document.add(table);
        document.close();
        System.out.println("✅ Reporte general generado en: " + rutaArchivo);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void generarPDFMes(String rutaArchivo, List<Asistencia> listaAsistencias) {
    Document document = new Document(PageSize.A4.rotate(), 40, 40, 50, 50); // horizontal
    try {
        PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
        document.open();

        // ================== FUENTES ==================
        Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        Font normalFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

        // ================== TITULO ==================
        Paragraph titulo = new Paragraph("Reporte Resumido de Asistencias", tituloFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        document.add(new Paragraph("Generado el: " + java.time.LocalDate.now(), normalFont));
        document.add(new Paragraph(" ")); // espacio

        // ================== TABLA UNIFICADA ==================
        PdfPTable table = new PdfPTable(7); 
        table.setWidthPercentage(100);
        table.setSpacingBefore(15f);
        table.setWidths(new float[]{2f, 3f, 2f, 2f, 3f, 3f, 3f});

        String[] encabezados = {
                "Matrícula", "Nombre", "Mes", "Asistencias", 
                "Hora Llegada Frec.", "Hora Salida Frec.", "Promedio Mensual (h)"
        };
        for (String encabezado : encabezados) {
            PdfPCell cell = new PdfPCell(new Phrase(encabezado, headerFont));
            cell.setBackgroundColor(BaseColor.GRAY);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(8f);
            table.addCell(cell);
        }

        // ================== AGRUPAR POR ALUMNO ==================
        Map<String, List<Asistencia>> asistenciasPorAlumno = listaAsistencias.stream()
                .collect(Collectors.groupingBy(a -> a.getAlumno().getMatricula()));

        ReporteAsistencia reporte = new ReporteAsistencia();

        for (Map.Entry<String, List<Asistencia>> entry : asistenciasPorAlumno.entrySet()) {
            String matricula = entry.getKey();
            List<Asistencia> asistenciasAlumno = entry.getValue();
            Alumno alumno = asistenciasAlumno.get(0).getAlumno();

            // ---- Agrupar asistencias por mes ----
            Map<Month, Long> asistenciasPorMes = reporte.asistenciasPorMes(asistenciasAlumno);

            for (Map.Entry<Month, Long> mesEntry : asistenciasPorMes.entrySet()) {
                String mes = mesEntry.getKey() .getDisplayName(java.time.format.TextStyle.FULL, new java.util.Locale("es", "ES"));
                Long cantidad = mesEntry.getValue();

                LocalTime llegadaFrecuente = reporte.horaLlegadaFrecuente(asistenciasAlumno);
                LocalTime salidaFrecuente = reporte.horaSalidaFrecuente(asistenciasAlumno);
                double promedioMensual = reporte.calcularPromedioHorasMes(asistenciasAlumno);

                // ---- Agregar fila ----
                table.addCell(new PdfPCell(new Phrase(matricula, normalFont)));
                table.addCell(new PdfPCell(new Phrase(alumno.getNombre(), normalFont)));
                table.addCell(new PdfPCell(new Phrase(mes, normalFont)));
                table.addCell(new PdfPCell(new Phrase(cantidad.toString(), normalFont)));
                table.addCell(new PdfPCell(new Phrase(
                        llegadaFrecuente != null ? llegadaFrecuente.toString() : "N/A", normalFont)));
                table.addCell(new PdfPCell(new Phrase(
                        salidaFrecuente != null ? salidaFrecuente.toString() : "N/A", normalFont)));
                table.addCell(new PdfPCell(new Phrase(
                        String.format("%.2f", promedioMensual), normalFont)));
            }
        }

        document.add(table);

        // ================== CIERRE ==================
        document.close();
        System.out.println("✅ PDF generado correctamente en: " + rutaArchivo);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}
