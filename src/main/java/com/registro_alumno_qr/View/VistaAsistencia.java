/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.registro_alumno_qr.View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaAsistencia extends javax.swing.JFrame {

    public VistaAsistencia() {
       
        initComponents();
         setLocationRelativeTo(null);
    }

    public void setModeloTabla(DefaultTableModel modelo) {
        tablaAsistencias.setModel(modelo);
    }

    public JButton getBtnRegistrarAlumno() {
        return btnRegistrarAlumno;
    }

    public JButton getBtnAsistencias() {
        return btnCamara;
    }
    public JButton getGenerarReport() {
        return btnGenerarReporte;
    }
    public JButton getGenerarReporteGeneral() {
        return btnGenerarReporteGeneral;
    }
    public JButton getGenerarReporteMensual() {
        return btnGenerarReporteMensual;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnRegistrarAlumno = new javax.swing.JButton();
        btnCamara = new javax.swing.JButton();
        scroll = new javax.swing.JScrollPane();
        tablaAsistencias = new javax.swing.JTable();
        btnGenerarReporte = new javax.swing.JButton();
        btnGenerarReporteGeneral = new javax.swing.JButton();
        btnGenerarReporteMensual = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(800, 500));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(800, 500));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnRegistrarAlumno.setBackground(new java.awt.Color(255, 51, 0));
        btnRegistrarAlumno.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        btnRegistrarAlumno.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarAlumno.setText("Registrar Alumno");
        jPanel1.add(btnRegistrarAlumno, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 60));

        btnCamara.setBackground(new java.awt.Color(255, 51, 0));
        btnCamara.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        btnCamara.setForeground(new java.awt.Color(255, 255, 255));
        btnCamara.setText("Camara");
        jPanel1.add(btnCamara, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 0, 400, 60));

        scroll.setBackground(new java.awt.Color(204, 204, 204));
        scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tablaAsistencias.setFont(new java.awt.Font("Trebuchet MS", 1, 12)); // NOI18N
        tablaAsistencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Matricula", "Nombre", "Fecha", "Hora_Entrada", "Hora_Salida"
            }
        ));
        scroll.setViewportView(tablaAsistencias);

        jPanel1.add(scroll, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 610, 390));

        btnGenerarReporte.setBackground(new java.awt.Color(255, 51, 0));
        btnGenerarReporte.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        btnGenerarReporte.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarReporte.setText("Imprimir Reporte");
        jPanel1.add(btnGenerarReporte, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 260, 160, 40));

        btnGenerarReporteGeneral.setBackground(new java.awt.Color(255, 51, 0));
        btnGenerarReporteGeneral.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        btnGenerarReporteGeneral.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarReporteGeneral.setText("Imprimir Reporte General");
        jPanel1.add(btnGenerarReporteGeneral, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 200, 160, 40));

        btnGenerarReporteMensual.setBackground(new java.awt.Color(255, 51, 0));
        btnGenerarReporteMensual.setFont(new java.awt.Font("Trebuchet MS", 1, 10)); // NOI18N
        btnGenerarReporteMensual.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarReporteMensual.setText("Imprimir reporte mensual");
        jPanel1.add(btnGenerarReporteMensual, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 320, 160, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VistaAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VistaAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VistaAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VistaAsistencia.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VistaAsistencia().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnCamara;
    public javax.swing.JButton btnGenerarReporte;
    public javax.swing.JButton btnGenerarReporteGeneral;
    public javax.swing.JButton btnGenerarReporteMensual;
    public javax.swing.JButton btnRegistrarAlumno;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane scroll;
    public javax.swing.JTable tablaAsistencias;
    // End of variables declaration//GEN-END:variables
}
