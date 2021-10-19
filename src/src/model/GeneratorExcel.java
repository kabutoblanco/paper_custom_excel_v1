/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.io.File;
import java.util.Observable;

/**
 *
 * @author daniel
 */
public abstract class GeneratorExcel extends Observable {

    protected FileExcel fileExcel;

    public abstract void generate(String location, Object[] columnsSelect);

    public abstract void loadFile(File fileExcel);

    public FileExcel getFileExcel() {
        return fileExcel;
    }

}
