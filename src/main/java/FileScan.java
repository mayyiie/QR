import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.global.opencv_imgproc;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class FileScan {

    public static void main(String[] args) throws ChecksumException {
        File qrCodeImage = chooseImageFile();
        if (qrCodeImage != null) {
            scanFile(qrCodeImage);
        }
    }

    public static void scanFile(File qrCodeImage) throws ChecksumException {
        try {
            BufferedImage bufferedImage = ImageIO.read(qrCodeImage);

            // Convert BufferedImage to Mat
            Java2DFrameConverter converter = new Java2DFrameConverter();
            Frame frame = converter.getFrame(bufferedImage);
            OpenCVFrameConverter.ToMat matConverter = new OpenCVFrameConverter.ToMat();
            Mat matImage = matConverter.convertToMat(frame);

            // Convert image to grayscale
            Mat grayImage = new Mat();
            opencv_imgproc.cvtColor(matImage, grayImage, opencv_imgproc.COLOR_BGR2GRAY);

            // Create BinaryBitmap from the grayscale image
            BufferedImage grayBufferedImage = converter.getBufferedImage(matConverter.convert(grayImage));
            LuminanceSource source = new BufferedImageLuminanceSource(grayBufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            // Decode the QR code using ZXing
            com.google.zxing.Reader reader = new MultiFormatReader();
            try {
                Result result = reader.decode(bitmap);
                // Print the result
                System.out.println("QR Code content: " + result.getText());
            } catch (com.google.zxing.FormatException e) {
                System.err.println("Error decoding QR code: " + e.getMessage());
            }
        } catch (IOException e) {
            System.err.println("Error loading QR code image: " + e.getMessage());
        } catch (NotFoundException e) {
            System.err.println("Error: QR code not found in the image");
        }
    }

    // Utility method to show a file chooser dialog and select an image file
    public static File chooseImageFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select QR code image");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            System.out.println("No file selected.");
            return null;
        }
    }
}
