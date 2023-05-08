package com.company;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select the operation you want to perform:");
        System.out.println("1. Split PDFs");
        System.out.println("2. Digitize papers");
        int operation = scanner.nextInt();
        scanner.nextLine();

        if (operation == 1) {
            System.out.println("Enter the path of the PDF file to split:");
            String inputFilePath = scanner.nextLine();

            System.out.println("Enter the output folder path for the split PDFs:");
            String outputFolderPath = scanner.nextLine();

            PdfSplitter pdfSplitter = new PdfSplitterImpl();
            try {
                pdfSplitter.splitPdf(inputFilePath, outputFolderPath);
                System.out.println("PDF file has been split successfully.");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } else if (operation == 2) {
            System.out.println("Enter the base path for storing documents:");
            String basePath = scanner.nextLine();

            System.out.println("Enter the path of the folder containing scanned documents:");
            String inputFolderPath = scanner.nextLine();

            System.out.println("Enter the worker's last name:");
            String lastName = scanner.nextLine();

            System.out.println("Enter the worker's first name:");
            String firstName = scanner.nextLine();

            File inputFolder = new File(inputFolderPath);
            File[] inputFiles = inputFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

            if (inputFiles == null) {
                System.out.println("Error: Unable to read files from the input folder.");
                scanner.close();
                return;
            }

            DocumentOrganizer documentOrganizer = new DocumentOrganizerImpl(basePath);

            for (File inputFile : inputFiles) {
                System.out.println("Processing: " + inputFile.getName());
                openPdf(inputFile.getPath());

                System.out.println("Enter the date of the document (YYYYMMDD):");
                String documentDate = scanner.nextLine();

                System.out.println("Select the document type:");
                for (int i = 0; i < DocumentOrganizer.FOLDER_NAMES.length; i++) {
                    System.out.println((i + 1) + ". " + DocumentOrganizer.FOLDER_NAMES[i]);
                }
                int documentTypeIndex = scanner.nextInt() - 1;
                scanner.nextLine(); // Consume the newline character

                System.out.println("Enter the custom document name:");
                String customName = scanner.nextLine();

                try {
                    String outputFilePath = documentOrganizer.organizeDocument(inputFile.getPath(), documentDate, lastName, firstName, documentTypeIndex, customName);
                    System.out.println("Document moved to: " + outputFilePath);
                } catch (IOException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

        scanner.close();
    }

    private static void openPdf(String filePath) {
        if (!Desktop.isDesktopSupported()) {
            System.out.println("Desktop is not supported. Cannot open the PDF file.");
            return;
        }

        File file = new File(filePath);
        Desktop desktop = Desktop.getDesktop();

        if (!desktop.isSupported(Desktop.Action.OPEN)) {
            System.out.println("Desktop does not support the open action. Cannot open the PDF file.");
            return;
        }

        try {
            desktop.open(file);
        } catch (IOException e) {
            System.out.println("Error opening PDF file: " + e.getMessage());
        }
    }
}
