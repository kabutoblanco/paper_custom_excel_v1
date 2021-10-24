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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author daniel
 */
public class FileExcel extends Observable implements Runnable{
    private File file;
    private XSSFWorkbook fileExcel;

    public FileExcel(File file){
        this.file = file;       
    }
    
    public void loadFile(){
        FileInputStream fis = null;
        setChanged();
        try {
            notifyObservers("1-Cargando archivo");
            fis = new FileInputStream(file);
            setChanged();
            notifyObservers("1-Convirtiendo archivo a formato Excel");
            this.fileExcel = new XSSFWorkbook(fis);
            setChanged();
            notifyObservers(this.getColumnHeader());
        } catch (FileNotFoundException ex) {
            notifyObservers("1-Archivo no encontrado");
        } catch (IOException ex) {
            notifyObservers("1-No se pudo leer el archivo");
        } finally {
            try {
                fis.close();
            } catch (IOException ex) {
                notifyObservers("1-No se pudo cerrar el archivo");
            }
        }
    }
    
    public ArrayList<String> getColumnHeader() {
        ArrayList<String> headers = new ArrayList<>();
        XSSFSheet sheet = fileExcel.getSheetAt(1);
        for (Row row : sheet) {
            int i = 0;
            for (Cell cell : row) {
                headers.add(i + "-" + cell.getStringCellValue());
                i++;
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
            XSSFCellStyle cellStyle = (XSSFCellStyle) row.getCell(1).getCellStyle();
            XSSFFont font = cellStyle.getFont();
            if (font.getBold() && !value.equals("Description")) {
                areas.add(new PaperArea(value));
            } else if (!font.getBold()) {
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

    @Override
    public void run() {
        loadFile();
    }
    
}
