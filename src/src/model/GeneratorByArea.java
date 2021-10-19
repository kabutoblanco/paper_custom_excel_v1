/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel
 */
public class GeneratorByArea extends GeneratorExcel {

    @Override
    public void generate(String location, Object[] columnsSelect) {
        for (Object value : columnsSelect) {
            System.out.print(value);
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
