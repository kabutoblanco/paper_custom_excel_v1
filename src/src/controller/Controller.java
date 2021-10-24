/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import java.io.File;
import src.model.AbstractFactory;
import src.model.GeneratorByArea;
import src.model.GeneratorByAreaAbstractFactory;
import src.model.GeneratorExcel;
import src.view.JPanelColumns;
import src.view.JPanelInformation;
import src.view.JPanelMenu;
import src.view.JPanelProgress;

/**
 *
 * @author daniel
 */
public class Controller {

    private AbstractFactory abstractFactory;
    private GeneratorExcel generatorExcel;
    
    private JPanelMenu jPanelMenu;
    private JPanelColumns jPanelColumns;
    private JPanelInformation jPanelInformation;
    private JPanelProgress jPanelProgress;

    public void createGeneraterExcel() {
        abstractFactory = new GeneratorByAreaAbstractFactory();
        generatorExcel = abstractFactory.createGeneratorExcel();
        generatorExcel.addObserver(jPanelInformation);   
        generatorExcel.addObserver(jPanelProgress);
    }

    public void loadFile(File fileExcel) {
        generatorExcel.loadFile(fileExcel);
        generatorExcel.getFileExcel().addObserver(jPanelInformation);
        generatorExcel.getFileExcel().addObserver(jPanelColumns);
    }

    public void generateFile(String location) {
        GeneratorByArea generator = (GeneratorByArea) generatorExcel;
        generator.setLocation(location);
        generator.setColumnsSelect(jPanelColumns.getjListColumnsSelect());
        Thread t = new Thread(generator);
        t.start();
    }

    public void setjPanelMenu(JPanelMenu jPanelMenu) {
        this.jPanelMenu = jPanelMenu;
    }

    public void setjPanelCollumns(JPanelColumns jPanelCollumns) {
        this.jPanelColumns = jPanelCollumns;
    }    

    public void setjPanelInformation(JPanelInformation jPanelInformation) {
        this.jPanelInformation = jPanelInformation;
    }

    public void setjPanelProgress(JPanelProgress jPanelProgress) {
        this.jPanelProgress = jPanelProgress;
    }
    
}
