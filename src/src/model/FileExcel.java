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
    
    public void getSubAreas() {
        
    }
}
