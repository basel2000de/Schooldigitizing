package com.company;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Path;


public class DocumentOrganizerImpl implements DocumentOrganizer {

    private final String basePath;

    public DocumentOrganizerImpl(String basePath) {
        this.basePath = basePath;
    }

    @Override
    public String organizeDocument(String inputFilePath, String documentDate, String lastName, String firstName, int documentTypeIndex, String customName) throws IOException {
        String folderName = FOLDER_NAMES[documentTypeIndex];
        String workerFolderPath = basePath + File.separator + lastName + "_" + firstName;
        String documentFolderPath = workerFolderPath + File.separator + folderName;

        File documentFolder = new File(documentFolderPath);
        if (!documentFolder.exists()) {
            documentFolder.mkdirs();
        }

        String outputFileName = documentDate + "_" + customName + "_" + lastName + ".pdf";
        String outputFilePath = documentFolderPath + File.separator + outputFileName;

        Path sourcePath = Paths.get(inputFilePath);
        Path destinationPath = Paths.get(outputFilePath);

        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

        return outputFilePath;
    }
}
