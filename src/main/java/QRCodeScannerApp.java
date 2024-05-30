import javax.swing.*;

import com.google.zxing.FormatException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QRCodeScannerApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QRCodeScannerApp::new);
    }

    public QRCodeScannerApp() {
        JFrame frame = new JFrame("QR Code Scanner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);

        JButton fileScanButton = new JButton("Scan from File");
        JButton webcamScanButton = new JButton("Scan from Webcam");

        fileScanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FileScan.chooseImageFile();
            }
        });

        webcamScanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(() -> {
                    try {
                        QRScanner.scanFromWebcam();
                    } catch (FormatException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            }
        });

        JPanel panel = new JPanel();
        panel.add(fileScanButton);
        panel.add(webcamScanButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
