/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.poi.hssf.record.aggregates.RowRecordsAggregate.createRow;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author daniel
 */
public class GeneratorByArea extends GeneratorExcel {

    @Override
    public void generate(String location, Object[] columnsSelect) {
        ArrayList<PaperArea> areas = fileExcel.getSubAreas();
        XSSFSheet sheet = fileExcel.getFileExcel().getSheetAt(1);
        int i = 0;
        for (Row row : sheet) {
            if (i != 0) {
                String value = row.getCell(9).getStringCellValue();
                for (PaperArea area : areas) {
                    boolean flag = false;
                    for (PaperArea subArea : area.getSubAreas()) {
                        if (subArea.getArea().equals(value)) {
                            // saved paper whitin subarea
                            subArea.getSubAreas().add(new PaperArea(row.getCell(1).getStringCellValue()));
                            flag = true;
                            break;
                        }
                    }
                    if (flag) break;
                }
            }
            i++;
        }
        Workbook wb = new XSSFWorkbook();
        Sheet sheet_v1 = wb.createSheet("example");
        
        i = 0;
        for (PaperArea area : areas) {
            for (PaperArea subArea : area.getSubAreas()) {
                i++;
                XSSFRow row = (XSSFRow) sheet_v1.createRow(i);
                row.createCell(0).setCellValue(subArea.getArea() + " - " + area.getArea());
                for (PaperArea subAreaPaper : subArea.getSubAreas()) {
                    i++;
                    XSSFRow row1 = (XSSFRow) sheet_v1.createRow(i);
                    row1.createCell(0).setCellValue(subAreaPaper.getArea());
                    System.out.println(i);
                }
            }
            i++;
            System.out.println(i);
        }
        System.out.println(i);
        try {   
            OutputStream fileOut = new FileOutputStream(location + "\\BankStatement.xlsx");
            wb.write(fileOut);  
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeneratorByArea.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorByArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void loadFile(File fileExcel) {
        try {
            this.fileExcel = new FileExcel(fileExcel);
        } catch (IOException ex) {
            Logger.getLogger(GeneratorByArea.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
