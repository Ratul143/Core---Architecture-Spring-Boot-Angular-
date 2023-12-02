package jwt.backend.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

/**
 * @author Jaber
 * @date 7/24/2022
 * @time 2:08 PM
 */
public interface ExcelExportService {
    byte[] exportExcel(Long id) throws FileNotFoundException, JRException;
}
