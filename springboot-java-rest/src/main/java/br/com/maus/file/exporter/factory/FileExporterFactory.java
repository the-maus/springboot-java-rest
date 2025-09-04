package br.com.maus.file.exporter.factory;

import br.com.maus.exception.BadRequestException;
import br.com.maus.file.exporter.MediaTypes;
import br.com.maus.file.exporter.contract.FileExporter;
import br.com.maus.file.exporter.impl.CsvExporter;
import br.com.maus.file.exporter.impl.XlsxExporter;
import br.com.maus.file.importer.contract.FileImporter;
import br.com.maus.file.importer.impl.CsvImporter;
import br.com.maus.file.importer.impl.XlsxImporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component // for injection
public class FileExporterFactory {

    private Logger logger = LoggerFactory.getLogger(FileExporterFactory.class);

    @Autowired
    private ApplicationContext context;

    public FileExporter getExporter(String acceptHeader) throws Exception {
        if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_XLSX_VALUE)) {
            return context.getBean(XlsxExporter.class); // spring instantiates exporter class, no need for "new XlsxExporter()"
        } else if (acceptHeader.equalsIgnoreCase(MediaTypes.APPLICATION_CSV_VALUE)) {
            return context.getBean(CsvExporter.class);
        } else {
            throw new BadRequestException("Invalid file format!");
        }
    }
}
