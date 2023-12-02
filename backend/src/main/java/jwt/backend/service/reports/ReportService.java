package jwt.backend.service.reports;

import jwt.backend.constant.ReportPath;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ReportService {

    @Autowired
    private ReportPath reportPath;

    public String generateReport(Collection<?> obj, String jrxmlPath, String reportName) throws IOException, JRException {
        try {
            File file = ResourceUtils.getFile(jrxmlPath);
            InputStream input = new FileInputStream(file);

            // Compile the Jasper report from .jrxml to .jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(input);

            // Get your data source
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(obj);

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Chairman Dashboard");

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);

            // Generate and return the report in the specified formats
            generatePDF(jasperPrint, reportName);
            generateXML(jasperPrint, reportName);
            generateHTML(jasperPrint, reportName);
            generateXLSX(jasperPrint, reportName);
            generateCSV(jasperPrint, reportName);

            return reportName;
        } catch (Exception e) {
            log.info("Failed to generate report due to Exception: " + e.getMessage());
            return e.getMessage();
        }
    }

    private void generatePDF(JasperPrint jasperPrint, String reportName) throws JRException {
        JasperExportManager.exportReportToPdfFile(jasperPrint, reportPath.getBasePath() + "/" + reportName + ".pdf");
        log.info("PDF File Generated: {}", reportName);
    }

    private void generateXML(JasperPrint jasperPrint, String reportName) throws JRException {
        JasperExportManager.exportReportToXmlFile(jasperPrint, reportPath.getBasePath() + "/" + reportName + ".xml", true);
        log.info("XML File Generated: {}", reportName);
    }

    private void generateHTML(JasperPrint jasperPrint, String reportName) throws JRException {
        JasperExportManager.exportReportToHtmlFile(jasperPrint, reportPath.getBasePath() + "/" + reportName + ".html");
        log.info("HTML File Generated: {}", reportName);
    }

    private void generateXLSX(JasperPrint jasperPrint, String reportName) throws JRException {
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(reportPath.getBasePath() + "/" + reportName + ".xlsx"));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(true);
        configuration.setRemoveEmptySpaceBetweenColumns(true);
        configuration.setDetectCellType(true);
        configuration.setCollapseRowSpan(true);
        configuration.setWhitePageBackground(false);
        configuration.setRemoveEmptySpaceBetweenRows(true);
        configuration.setIgnoreGraphics(false); // set true to ignore graphics
        configuration.setIgnoreCellBorder(false); // set true to ignore cell border
        configuration.setIgnoreCellBackground(false); // set true to ignore cell background
        configuration.setFontSizeFixEnabled(false);
        exporter.setConfiguration(configuration);

        exporter.exportReport();
        log.info("XLSX File Generated: {}", reportName);
    }

    private void generateCSV(JasperPrint jasperPrint, String reportName) throws JRException {
        JRCsvExporter exporter = new JRCsvExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleWriterExporterOutput(reportPath.getBasePath() + "/" + reportName + ".csv"));

        SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
        configuration.setFieldDelimiter(",");
        configuration.setFieldEnclosure("\"");
        configuration.setRecordDelimiter("\n");
        configuration.setForceFieldEnclosure(false);
        configuration.setWriteBOM(false);
        configuration.setEscapeFormula(false);
        exporter.setConfiguration(configuration);

        exporter.exportReport();
        log.info("CSV File Generated: {}", reportName);
    }

    public String generatePdfReport(Collection<?> obj, String jrxmlPath, String reportName) throws IOException, JRException {
        try {
            File file = ResourceUtils.getFile(jrxmlPath);
            InputStream input = new FileInputStream(file);

            // Compile the Jasper report from .jrxml to .jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(input);

            // Get your data source
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(obj);

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Chairman Dashboard");

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);

            // Export the report to PDF format
            generatePDF(jasperPrint, reportName);

            return reportName;

        } catch (Exception e) {
            log.info("Inside Report service failed to generate PDF report due to Exception: " + e.getMessage());
            return e.getMessage();
        }
    }

    public String generateXlsxReport(Collection<?> obj, String jrxmlPath, String reportName) throws IOException, JRException {
        try {
            File file = ResourceUtils.getFile(jrxmlPath);
            InputStream input = new FileInputStream(file);

            // Compile the Jasper report from .jrxml to .jasper
            JasperReport jasperReport = JasperCompileManager.compileReport(input);

            // Get your data source
            JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(obj);

            // Add parameters
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "Chairman Dashboard");

            // Fill the report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, source);

            // Export the report to XLSX format
            generateXLSX(jasperPrint, reportName);

            return reportName;

        } catch (Exception e) {
            log.info("Inside Report service failed to generate XLSX report due to Exception: " + e.getMessage());
            return e.getMessage();
        }
    }
}
