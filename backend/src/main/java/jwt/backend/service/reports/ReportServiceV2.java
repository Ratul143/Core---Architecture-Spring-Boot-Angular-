package jwt.backend.service.reports;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ReportServiceV2 {

    private byte[] generatePDF(JasperPrint jasperPrint, String reportName) throws JRException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        log.info("PDF File Generated: {}", reportName);
        return outputStream.toByteArray();
    }

    public byte[] generatePdfReport(Collection<?> obj, String jrxmlPath, String reportName) throws IOException, JRException {
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
            return generatePDF(jasperPrint, reportName);

        } catch (Exception e) {
            log.info("Failed to generate PDF report due to Exception: " + e.getMessage());
            throw e;
        }
    }

    public void downloadPdfReport(byte[] pdfBytes, HttpServletResponse response, String reportName) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + reportName + ".pdf");
        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
        log.info("PDF Report Downloaded: {}", reportName);
    }

    private byte[] generateXLSX(JasperPrint jasperPrint, String reportName) throws JRException {
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setOnePagePerSheet(true);
        configuration.setRemoveEmptySpaceBetweenColumns(true);
        configuration.setDetectCellType(true);
        configuration.setCollapseRowSpan(true);
        configuration.setWhitePageBackground(false);
        configuration.setRemoveEmptySpaceBetweenRows(true);
        configuration.setIgnoreGraphics(true); // set true to ignore graphics
        configuration.setIgnoreCellBorder(false); // set true to ignore cell border
        configuration.setIgnoreCellBackground(false); // set true to ignore cell background
        configuration.setFontSizeFixEnabled(false);
        configuration.setIgnoreTextFormatting(false); // Ignores text formatting
        configuration.setSheetNames(new String[] { "Sheet1", "Sheet2" }); // Sets custom sheet names

        exporter.setConfiguration(configuration);

        exporter.exportReport();
        log.info("XLSX File Generated: {}", reportName);

        return outputStream.toByteArray();
    }

    public byte[] generateXlsxReport(Collection<?> obj, String jrxmlPath, String reportName) throws IOException, JRException {
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
            byte[] xlsxBytes;
            xlsxBytes = generateXLSX(jasperPrint, reportName);

            return xlsxBytes;

        } catch (Exception e) {
            log.info("Inside Report service failed to generate XLSX report due to Exception: " + e.getMessage());
            return null;
        }
    }

    public void downloadXlsxReport(byte[] xlsxBytes, HttpServletResponse response, String reportName) throws IOException {
        // Set the appropriate headers for file download
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + reportName + ".xlsx");

        // Write the generated XLSX data to the response output stream
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(xlsxBytes);
        outputStream.flush();
        outputStream.close();
        log.info("XLSX Report Downloaded: {}", reportName);
    }

}
