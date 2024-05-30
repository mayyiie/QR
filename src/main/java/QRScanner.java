import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.global.opencv_imgproc;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.*;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class QRScanner {

    public static void scanFromWebcam() throws FormatException {
        // Initialize camera
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0); // 0 for default camera
        try {
            grabber.start();

            // Initialize decoder
            MultiFormatReader reader = new MultiFormatReader();

            // Keep track of detected QR codes to prevent duplicates
            Set<String> detectedQRs = new HashSet<>();

            // Continuously capture frames
            while (true) {
                Frame frame = grabber.grab();
                if (frame == null) {
                    continue;
                }

                // Convert frame to BufferedImage
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage bufferedImage = converter.convert(frame);

                // Decode the QR code using ZXing
                try {
                    // Convert BufferedImage to LuminanceSource
                    LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    Result result = reader.decode(bitmap);
                    String qrContent = result.getText();

                    // Check if the QR code content is new
                    if (!detectedQRs.contains(qrContent)) {
                        // Print the result
                        System.out.println("QR Code content: " + qrContent);
                        detectedQRs.add(qrContent);
                    }
                } catch (NotFoundException e) {
                    // QR code not found in the current frame
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Release resources
            try {
                grabber.stop(); // Stop the camera
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
