package com.company;

import java.io.IOException;

public interface PdfSplitter {
    void splitPdf(String inputFilePath, String outputFolderPath) throws IOException;
}
