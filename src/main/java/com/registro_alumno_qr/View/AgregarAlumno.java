package com.registro_alumno_qr.View;

import javax.swing.JButton;
import javax.swing.JTextField;

public class AgregarAlumno extends javax.swing.JFrame {

    public AgregarAlumno() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public String getMatricula() {
        return txtMatricula.getText();
    }

    public String getApellidoP() {
        return txtApellidoP.getText();
    }

    public String getApellidoM() {
        return txtApellidoM.getText();
    }

    public String getNombre() {
        return txtNombre.getText();
    }

    public String getEmail() {
        return txtEmail.getText();
    }

    public String getPassword() {
        return txtPassword.getText();
    }

    public void setMatricula(String matricula) {
        txtMatricula.setText(matricula);
    }

    public void setNombre(String nombre) {
        txtNombre.setText(nombre);
    }

    public void setApellidoP(String apellidoP) {
        txtApellidoP.setText(apellidoP);
    }

    public void setApellidoM(String apellidoM) {
        txtApellidoM.setText(apellidoM);
    }

    public void setEmail(String email) {
        txtEmail.setText(email);
    }

    public void setPassword(String password) {
        txtPassword.setText(password);
    }

    public JButton getBtnRegistrar() {
        return btnRegistrar;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtMatricula = new javax.swing.JTextField();
        txtApellidoP = new javax.swing.JTextField();
        txtApellidoM = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnRegistrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Imagenregistro.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        bg.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 390, 400));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(166, 67, 28));
        jLabel2.setText("REGISTRO ALUMNO");
        bg.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, -1, -1));

        jLabel3.setText("Matricula:");
        bg.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, 70, -1));

        jLabel4.setText("Apellido paterno:");
        bg.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 120, -1, -1));

        jLabel5.setText("Apellido materno:");
        bg.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 160, -1, -1));

        jLabel6.setText("Nombre(s)");
        bg.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 200, -1, -1));

        jLabel7.setText("Email:");
        bg.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 240, -1, -1));

        jLabel8.setText("Contraseña:");
        bg.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 280, -1, -1));
        bg.add(txtMatricula, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 80, 200, -1));
        bg.add(txtApellidoP, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 120, 200, -1));
        bg.add(txtApellidoM, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, 200, -1));
        bg.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 200, 200, -1));
        bg.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 240, 200, -1));

        txtPassword.setText("jPasswordField1");
        bg.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 280, 200, -1));

        btnRegistrar.setBackground(new java.awt.Color(255, 51, 0));
        btnRegistrar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnRegistrar.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrar.setText("Confirmar");
        bg.add(btnRegistrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 330, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
            java.util.logging.Logger.getLogger(AgregarAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgregarAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgregarAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgregarAlumno.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AgregarAlumno().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bg;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField txtApellidoM;
    private javax.swing.JTextField txtApellidoP;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMatricula;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
