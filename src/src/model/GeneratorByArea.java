/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author daniel
 */
public class GeneratorByArea extends GeneratorExcel implements Runnable {
    String location;
    Object[] columnsSelect;

    public void setLocation(String location) {
        this.location = location;
    }

    public void setColumnsSelect(Object[] columnsSelect) {
        this.columnsSelect = columnsSelect;
    }
    
    @Override
    public void run(){
        generate(location, columnsSelect);
    }
    
    @Override
    public void generate(String location, Object[] columnsSelect) {
        setChanged();
        notifyObservers(true);
        setChanged();
        notifyObservers("0-Cargando subáreas");
        ArrayList<PaperArea> areas = fileExcel.getSubAreas();
        setChanged();
        notifyObservers("0-Cargando hoja de papers");
        setChanged();
        XSSFSheet sheet = fileExcel.getFileExcel().getSheetAt(1);
        notifyObservers("0-Recorriendo lista de papers");
        int i = 0;
        for (Row row : sheet) {
            if (i != 0) {
                String value = row.getCell(9).getStringCellValue();
                for (PaperArea area : areas) {
                    boolean flag = false;
                    for (PaperArea subArea : area.getSubAreas()) {
                        if (subArea.getArea().equals(value)) {
                            // saved paper whitin subarea
                            String paperValue = "";
                            for (Object select : columnsSelect) {
                                int index = Integer.parseInt(((String) select).split("-")[0]);
                                if (row.getCell(index) != null) {
                                    Enum type = row.getCell(index).getCellTypeEnum();
                                    if (type == CellType.STRING) {
                                        paperValue += row.getCell(index).getStringCellValue() + "\r";
                                    } else if (type == CellType.NUMERIC) {
                                        paperValue += new BigDecimal(Double.toString(row.getCell(index).getNumericCellValue())).stripTrailingZeros().toPlainString() + "\r";
                                    }
                                }
                            }
                            subArea.getSubAreas().add(new PaperArea(paperValue));
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        break;
                    }
                }
            }
            i++;
        }
        Workbook wb = new XSSFWorkbook();
        setChanged();
        notifyObservers("0-Generando nuevo archivo Excel");
        XSSFSheet sheet_v1 = (XSSFSheet) wb.createSheet("example");
        setChanged();
        notifyObservers("0-Generando nuevos registros");

        i = -1;
        for (PaperArea area : areas) {
            for (PaperArea subArea : area.getSubAreas()) {
                i++;
                setChanged();
                notifyObservers("0-Generando registros de " + subArea.getArea() );
                XSSFRow rowHeader = (XSSFRow) sheet_v1.createRow(i);
                XSSFCell cellHeader = rowHeader.createCell(0);
                cellHeader.setCellValue("Scopus Suject Area \"" + area.getArea() + "\"");                
                sheet_v1.addMergedRegion(new CellRangeAddress(i, i, 0, columnsSelect.length));
                cellHeader.setCellStyle(customCellForeground("#1f4e79", "#ffffff", HorizontalAlignment.CENTER, 18, wb));
                rowHeader.setHeight((short) (7 * 100));
                i++;
                XSSFRow rowSubHeader = (XSSFRow) sheet_v1.createRow(i);
                XSSFCell cellSubHeader = rowSubHeader.createCell(0);
                cellSubHeader.setCellValue("Scopus Sub Suject Area \"" + subArea.getArea() + "\"");                
                sheet_v1.addMergedRegion(new CellRangeAddress(i, i, 0, columnsSelect.length));
                cellSubHeader.setCellStyle(customCellForeground("#deebf7", "#843c0b", HorizontalAlignment.CENTER, 16, wb));
                rowSubHeader.setHeight((short) (6 * 100));
                i++;
                XSSFRow rowTitle = (XSSFRow) sheet_v1.createRow(i);
                setChanged();
                notifyObservers("0-Generando estilos");
                for (int j = -1; j < columnsSelect.length; j++) {                    
                    if (j == -1) {
                        XSSFCell cellTitle = rowTitle.createCell(0);
                        cellTitle.setCellValue("#");
                        cellTitle.setCellStyle(customCellForeground("#5b9bd5", "#ffffff", HorizontalAlignment.LEFT, 11, wb));
                    } else {
                        String header = ((String) columnsSelect[j]).split("-")[1];
                        XSSFCell cellTitle = rowTitle.createCell(j + 1);
                        cellTitle.setCellValue(header);
                        cellTitle.setCellStyle(customCellForeground("#5b9bd5", "#ffffff", HorizontalAlignment.LEFT, 11, wb));
                    }
                    
                }
                rowTitle.setHeight((short) (5 * 100));
                int k = 0;
                for (PaperArea subAreaPaper : subArea.getSubAreas()) {
                    i++;
                    XSSFRow rowBody = (XSSFRow) sheet_v1.createRow(i);
                    String[] values = subAreaPaper.getArea().split("\r");
                    rowBody.createCell(0).setCellValue(k);
                    for (int j = 0; j < values.length; j++) {
                        rowBody.createCell(j + 1).setCellValue(values[j]);
                    }
                    k++;
                }
            }
        }
        for (int j = 0; j < columnsSelect.length; j++) {
            sheet_v1.setColumnWidth(0, 10 * 100);
            if (columnsSelect[j].equals("1-Title"))
                sheet_v1.setColumnWidth(j, 80 * 100);   
            else
                sheet_v1.setColumnWidth(j, 30 * 100);            
        }
        setChanged();
        notifyObservers("0-Intentando guardar archivo");
        setChanged();
        try {
            OutputStream fileOut = new FileOutputStream(location + "paper.xlsx");
            wb.write(fileOut);
            notifyObservers("0-Archivo guardado");
        } catch (FileNotFoundException ex) {
            notifyObservers("0-No existe el archivo");
        } catch (IOException ex) {
            notifyObservers("0-No se puede guardar el archivo");
        }
        setChanged();
        notifyObservers(false);
    }

    @Override
    public void loadFile(File fileExcel) {
        this.fileExcel = new FileExcel(fileExcel);
        Thread t = new Thread(this.fileExcel);
        t.start();
    }
    
    private XSSFCellStyle customCellForeground(String rgb, String rgb1, HorizontalAlignment alignment, int size, Workbook wb) {
        XSSFColor color = new XSSFColor(Color.decode(rgb));
        XSSFCellStyle cellStyle = (XSSFCellStyle) wb.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setFillForegroundColor(color);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        
        XSSFColor color1 = new XSSFColor(Color.decode(rgb1));
        XSSFFont font = (XSSFFont) wb.createFont();
        font.setFontHeight(size);
        font.setBold(true);
        font.setColor(color1);
        cellStyle.setFont(font);
        cellStyle.setAlignment(alignment);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

}
