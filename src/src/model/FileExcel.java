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
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import src.response.Response;

/**
 *
 * @author daniel
 */
public class FileExcel extends Observable implements Runnable {
    private File file;
    private XSSFWorkbook fileExcel;

    public FileExcel(File file){
        this.file = file;       
    }
    
    public void loadFile(){
        setChanged();
        notifyObservers(true);
        setChanged();
        try {
            notifyObservers(new Response(1, "Loading file...", Response.NORMAL));
            setChanged();
            notifyObservers(new Response(1, "Converting file to Excel format...", Response.NORMAL));
            fileExcel = (XSSFWorkbook) WorkbookFactory.create(file);
            verifySheet();
            setChanged();
            notifyObservers(getColumnHeader());
            setChanged();
            notifyObservers(new Response(2, file.getName(), Response.NORMAL));
            setChanged();
            notifyObservers(new Response(3, file.getAbsolutePath(), Response.NORMAL));
            setChanged();
            notifyObservers(new Response(1, "File loaded!", Response.OK));
        } catch (FileNotFoundException ex) {
            notifyObservers(new Response(1, "File not found", Response.FAIL));
            setChanged();
            notifyObservers(new Response(2, "not found", Response.NORMAL));
            setChanged();
            notifyObservers(new Response(3, "/", Response.NORMAL));
        } catch (IOException | InvalidFormatException | EncryptedDocumentException ex) {
            notifyObservers(new Response(1, "File not open", Response.FAIL));
            setChanged();
            notifyObservers(new Response(2, "not found", Response.NORMAL));
            setChanged();
            notifyObservers(new Response(3, "File not open", Response.NORMAL));
        } catch (Exception ex) {
            setChanged();
            notifyObservers(new Response(1, ex.getMessage(), Response.FAIL));
        }
        setChanged();
        notifyObservers(false);
    }
    
    private void verifySheet() throws Exception {
        XSSFSheet sheet1 = fileExcel.getSheet("Sources");
        XSSFSheet sheet2 = fileExcel.getSheet("ASJC codes");
        if (sheet1 == null && sheet2 == null) throw new Exception("File not found sheet \"Sources\" and \"ASJC codes\"");
        if (sheet1 == null) throw new Exception("File not found sheet \"Sources\"");
        if (sheet2 == null) throw new Exception("File not found sheet \"ASJC codes\"");        
    }
    
    public ArrayList<String> getColumnHeader() {
        ArrayList<String> headers = new ArrayList<>();
        XSSFSheet sheet = fileExcel.getSheet("Sources");
        if (sheet != null) {
            for (Row row : sheet) {
                int i = 0;
                for (Cell cell : row) {
                    headers.add(i + "-" + cell.getStringCellValue());
                    i++;
                }
                break;
            }
        } else {
            setChanged();
            notifyObservers(new Response(1, "File not found sheet \"Sources\"", Response.FAIL));
        }
        return headers;
    }
    
    public ArrayList<PaperArea> getSubAreas() {
        ArrayList<PaperArea> areas = new ArrayList<>();
        XSSFSheet sheet = fileExcel.getSheet("ASJC codes");
        if (sheet != null) {
            for (Row row : sheet) {
                double valueCode = -1;
                try {
                    valueCode = row.getCell(0).getNumericCellValue();
                } catch (Exception e) {}
                String valueName = row.getCell(1).getStringCellValue();
                XSSFCellStyle cellStyle = (XSSFCellStyle) row.getCell(1).getCellStyle();
                XSSFFont font = cellStyle.getFont();
                if (font.getBold() && !valueName.equals("Description")) {
                    areas.add(new PaperArea(valueName));
                } else if (!font.getBold()) {
                    areas.get(areas.size() - 1).getSubAreas().add(new PaperArea(valueCode + "-" + valueName));
                }
            }
        } else {
            setChanged();
            notifyObservers(new Response(1, "File not found sheet \"ASJC codes\"", Response.FAIL));
        }
        return areas;
    }
    
    @Override
    public void run() {
        loadFile();
    }

    public File getFile() {
        return file;
    }

    public XSSFWorkbook getFileExcel() {
        return fileExcel;
    }
    
}
