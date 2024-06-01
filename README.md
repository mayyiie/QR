# QR Scanner

This is a QR scanner application with a graphical user interface (GUI) that allows users to scan QR codes from two sources:
1. Local image files
2. Web camera

## Features

- Scan QR codes from local files via a file chooser dialog
- Scan QR codes using the web camera via a button
- Supports multiple QR code formats
- Easy-to-use graphical interface

## Technologies Used

- Java
- Maven
- Swing (for GUI)
- ZXing (Zebra Crossing) library for QR code processing
- OpenCV library for accessing the web camera

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven

### Installing

1. **Clone the repository**

    ```sh
    git clone https://github.com/mayyiie/qr-scanner.git
    cd qr-scanner
    ```

2. **Build the project using Maven**

    ```sh
    mvn clean install
    ```

## Usage

### User Interface

When you run the application, a window will appear with the following components:

- **Select File Button**: Opens a dialog box to select an image file containing a QR code.
- **Scan with Camera Button**: Activates the web camera to scan a QR  code present in a frame.
