/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import java.io.File;
import src.model.AbstractFactory;
import src.model.GeneratorByAreaAbstractFactory;
import src.model.GeneratorExcel;
import src.view.JPanelColumns;
import src.view.JPanelMenu;

/**
 *
 * @author daniel
 */
public class Controller {

    private AbstractFactory abstractFactory;
    private GeneratorExcel generatorExcel;
    
    private JPanelMenu jPanelMenu;
    private JPanelColumns jPanelColumns;

    public void createGeneraterExcel() {
        abstractFactory = new GeneratorByAreaAbstractFactory();
        generatorExcel = abstractFactory.createGeneratorExcel();
    }

    public void loadFile(File fileExcel) {
        generatorExcel.loadFile(fileExcel);
        jPanelColumns.loadColumns(generatorExcel.getFileExcel().getColumnHeader());
    }

    public void generateFile(String location) {
        generatorExcel.generate(location, jPanelColumns.getjListColumnsSelect());
    }

    public void setjPanelMenu(JPanelMenu jPanelMenu) {
        this.jPanelMenu = jPanelMenu;
    }

    public void setjPanelCollumns(JPanelColumns jPanelCollumns) {
        this.jPanelColumns = jPanelCollumns;
    }    
    
}
