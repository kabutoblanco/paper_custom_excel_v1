/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author daniel
 */
public class FileExcel {
    private File file;
    private XSSFWorkbook fileExcel;

    public FileExcel(File file) throws FileNotFoundException, IOException {
        this.file = file;
        FileInputStream fis = new FileInputStream(file);
        this.fileExcel = new XSSFWorkbook(fis);
    }
    
    public ArrayList<String> getColumnHeader() {
        ArrayList<String> headers = new ArrayList<>();
        XSSFSheet sheet = fileExcel.getSheetAt(1);
        for (Row row : sheet) {
            for (Cell cell : row) {
                headers.add(cell.getStringCellValue());
            }
            break;
        }
        return headers;
    }
    
    public ArrayList<PaperArea> getSubAreas() {
        ArrayList<PaperArea> areas = new ArrayList<>();
        XSSFSheet sheet = fileExcel.getSheetAt(2);
        for (Row row : sheet) {
            String value = row.getCell(1).getStringCellValue();
            if (row.getCell(1).getCellStyle().getFontIndex() == 5 && !value.equals("Description")) {
                areas.add(new PaperArea(value));
            } else if (row.getCell(1).getCellStyle().getFontIndex() == 6) {
                areas.get(areas.size() - 1).getSubAreas().add(new PaperArea(value));
            }
        }
        return areas;
    }

    public File getFile() {
        return file;
    }

    public XSSFWorkbook getFileExcel() {
        return fileExcel;
    }
    
}
