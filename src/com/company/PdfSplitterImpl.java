package com.company;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class PdfSplitterImpl implements PdfSplitter {

    @Override
    public void splitPdf(String inputFilePath, String outputFolderPath) throws IOException {
        File outputFileFolder = new File(outputFolderPath);
        if (!outputFileFolder.exists()) {
            outputFileFolder.mkdirs();
        }

        try (PDDocument document = PDDocument.load(new File(inputFilePath))) {
            Splitter splitter = new Splitter();
            splitter.setStartPage(1);
            splitter.setEndPage(document.getNumberOfPages());

            int pageNum = 1;
            for (PDDocument page : splitter.split(document)) {
                String outputFilePath = outputFolderPath + File.separator + "page_" + pageNum + ".pdf";
                page.save(outputFilePath);
                page.close();
                pageNum++;
            }
        }
    }
}
