
package com.registro_alumno_qr.Controller;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.registro_alumno_qr.View.VistaCamara;
import java.awt.image.BufferedImage;
import java.awt.Dimension;


public class CamaraControlador {
    
      private VistaCamara vista;
    private Webcam webcam;
    private volatile boolean running = false;

    public CamaraControlador(VistaCamara vista) {
        this.vista = vista;
        iniciarCamara();
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
                        LuminanceSource source = new BufferedImageLuminanceSource(image);
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                        Result result = new MultiFormatReader().decode(bitmap);
                        String qrText = result.getText();
                        vista.mostrarTextoQR(qrText);
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

    public void detenerCamara() {
        running = false;
        if (webcam != null && webcam.isOpen()) {
            webcam.close();
        }
    }
    
}
