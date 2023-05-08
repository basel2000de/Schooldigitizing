package com.company;
import java.io.IOException;

public interface DocumentOrganizer {
    String[] FOLDER_NAMES = {
            "1.Bewerbungsunterlagen_Zertifikate",
            "2.Führungszeugnis",
            "3.Gesundheitsschutz",
            "4.Hausordner",
            "5.Mitarbeitergespräch",
            "6.persönliche Daten",
            "7.Verträge",
            "8.Weiterbildung",
            "9.Rente_Unterlagen"
    };

    String organizeDocument(String inputFilePath, String documentDate, String lastName, String firstName, int documentTypeIndex, String customName) throws IOException;
}
