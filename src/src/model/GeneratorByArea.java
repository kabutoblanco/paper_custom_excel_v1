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
import src.response.Response;

/**
 *
 * @author daniel
 */
public class GeneratorByArea extends GeneratorExcel implements Runnable {
    String location, directory;
    Object[] columnsSelect;

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
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
        notifyObservers(new Response(0, "Loading subareas...", Response.NORMAL));
        ArrayList<PaperArea> areas = fileExcel.getSubAreas();
        setChanged();
        notifyObservers(new Response(0, "Loading sheet papers...", Response.NORMAL));
        XSSFSheet sheet = fileExcel.getFileExcel().getSheet("Sources");
        setChanged();
        notifyObservers(new Response(0, "Loop papers...", Response.NORMAL));
        int i = 0;
        for (Row row : sheet) {
            if (i != 0) {
                double valueCode = row.getCell(8).getNumericCellValue();
                for (PaperArea area : areas) {
                    boolean flag = false;
                    for (PaperArea subArea : area.getSubAreas()) {
                        if (valueCode == Double.parseDouble(subArea.getArea().split("-")[0])) {
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
        notifyObservers(new Response(0, "Building new Excel file...", Response.NORMAL));
        XSSFSheet sheet_v1 = (XSSFSheet) wb.createSheet("sheet 1");
        setChanged();
        notifyObservers(new Response(0, "Building new records...", Response.NORMAL));
        
        areas.sort((PaperArea o1, PaperArea o2) -> o1.getArea().compareTo(o2.getArea()));

        i = -1;
        XSSFRow rowHeader, rowSubHeader, rowTitle;
        XSSFCell cellHeader, cellSubHeader, cellTitle;
        for (PaperArea area : areas) {
            for (PaperArea subArea : area.getSubAreas()) {
                i++;
                setChanged();
                notifyObservers(new Response(0, "Loading records of the " + subArea.getArea() + "...", Response.NORMAL));
                rowHeader = (XSSFRow) sheet_v1.createRow(i);
                cellHeader = rowHeader.createCell(0);
                cellHeader.setCellValue("Scopus Suject Area \"" + area.getArea() + "\"");                
                sheet_v1.addMergedRegion(new CellRangeAddress(i, i, 0, columnsSelect.length));
                cellHeader.setCellStyle(customCellForeground("#1f4e79", "#ffffff", HorizontalAlignment.CENTER, 18, wb));
                rowHeader.setHeight((short) (7 * 100));
                i++;
                rowSubHeader = (XSSFRow) sheet_v1.createRow(i);
                cellSubHeader = rowSubHeader.createCell(0);
                cellSubHeader.setCellValue("Scopus Sub Suject Area \"" + subArea.getArea().split("-")[1] + "\"");                
                sheet_v1.addMergedRegion(new CellRangeAddress(i, i, 0, columnsSelect.length));
                cellSubHeader.setCellStyle(customCellForeground("#deebf7", "#843c0b", HorizontalAlignment.CENTER, 16, wb));
                rowSubHeader.setHeight((short) (6 * 100));
                i++;
                rowTitle = (XSSFRow) sheet_v1.createRow(i);
                setChanged();
                notifyObservers(new Response(0, "Applying styles...", Response.NORMAL));
                for (int j = -1; j < columnsSelect.length; j++) {                    
                    if (j == -1) {
                        cellTitle = rowTitle.createCell(0);
                        cellTitle.setCellValue("#");
                        cellTitle.setCellStyle(customCellForeground("#5b9bd5", "#ffffff", HorizontalAlignment.LEFT, 11, wb));
                    } else {
                        String header = ((String) columnsSelect[j]).split("-")[1];
                        cellTitle = rowTitle.createCell(j + 1);
                        cellTitle.setCellValue(header);
                        cellTitle.setCellStyle(customCellForeground("#5b9bd5", "#ffffff", HorizontalAlignment.LEFT, 11, wb));
                    }
                    
                }
                rowTitle.setHeight((short) (5 * 100));
                System.gc();
                int k = 1;
                XSSFRow rowBody;
                String[] values;
                for (PaperArea subAreaPaper : subArea.getSubAreas()) {
                    i++;
                    rowBody = (XSSFRow) sheet_v1.createRow(i);
                    values = subAreaPaper.getArea().split("\r");
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
        notifyObservers(new Response(0, "Trying save file...", Response.NORMAL));
        setChanged();
        try {
            OutputStream fileOut = new FileOutputStream(location);
            wb.write(fileOut);
            notifyObservers(false);
            setChanged();
            notifyObservers(new Response(3, location + "?" + directory, Response.NORMAL));
            setChanged();
            notifyObservers(new Response(0, "File saved!", Response.OK));
            
        } catch (FileNotFoundException ex) {
            notifyObservers(new Response(0, "File not found", Response.FAIL));
            setChanged();
            notifyObservers(false);
        } catch (IOException ex) {
            notifyObservers(new Response(0, "File not saved", Response.FAIL));
            setChanged();
            notifyObservers(false);
        } catch (OutOfMemoryError e){
            notifyObservers(new Response(0, "Memory overhead, try with less columns", Response.FAIL));
            setChanged();
            notifyObservers(false);
        }
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
