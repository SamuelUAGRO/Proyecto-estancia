
package com.registro_alumno_qr.Entities;

import com.registro_alumno_qr.Model.Asistencia;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteAsistencia {
    
     public double calcularPromedioHorasSemana(List<Asistencia> asistencias) {
        Map<Integer, Long> horasPorSemana = new HashMap<>();

        for (Asistencia a : asistencias) {
            if (a.getHora_entrada() != null && a.getHora_salida() != null) {
                LocalDate fecha = a.getDia_asistencia();
                int semana = fecha.get(java.time.temporal.WeekFields.ISO.weekOfYear());

                long horas = Duration.between(
                        a.getHora_entrada().toLocalDateTime(),
                        a.getHora_salida().toLocalDateTime()
                ).toHours();

                horasPorSemana.merge(semana, horas, Long::sum);
            }
        }

        return horasPorSemana.values().stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0);
    }

    /**
     * Calcula el promedio de horas trabajadas por mes.
     */
    public double calcularPromedioHorasMes(List<Asistencia> asistencias) {
        Map<Integer, Long> horasPorMes = new HashMap<>();

        for (Asistencia a : asistencias) {
            if (a.getHora_entrada() != null && a.getHora_salida() != null) {
                int mes = a.getDia_asistencia().getMonthValue();

                long horas = Duration.between(
                        a.getHora_entrada().toLocalDateTime(),
                        a.getHora_salida().toLocalDateTime()
                ).toHours();

                horasPorMes.merge(mes, horas, Long::sum);
            }
        }

        return horasPorMes.values().stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(0);
    }

    
    public LocalTime horaLlegadaFrecuente(List<Asistencia> asistencias) {
        Map<LocalTime, Long> conteoHoras = asistencias.stream()
                .filter(a -> a.getHora_entrada() != null)
                .collect(Collectors.groupingBy(
                        a -> a.getHora_entrada().toLocalDateTime().toLocalTime().withSecond(0).withNano(0),
                        Collectors.counting()
                ));

        return conteoHoras.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

   
    public LocalTime horaSalidaFrecuente(List<Asistencia> asistencias) {
        Map<LocalTime, Long> conteoHoras = asistencias.stream()
                .filter(a -> a.getHora_salida() != null)
                .collect(Collectors.groupingBy(
                        a -> a.getHora_salida().toLocalDateTime().toLocalTime().withSecond(0).withNano(0),
                        Collectors.counting()
                ));

        return conteoHoras.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * Devuelve el historial completo (ordenado por fecha).
     */
    public List<Asistencia> historialOrdenado(List<Asistencia> asistencias) {
        return asistencias.stream()
                .sorted(Comparator.comparing(Asistencia::getDia_asistencia))
                .collect(Collectors.toList());
    }
    
}
