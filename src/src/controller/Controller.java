/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.controller;

import java.io.File;
import javax.swing.JFrame;
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
    
    private JFrame jFrame;
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
        generatorExcel.getFileExcel().addObserver(jPanelProgress);
    }

    public void generateFile(String location, String directory) {
        GeneratorByArea generator = (GeneratorByArea) generatorExcel;
        generator.setDirectory(directory);
        generator.setLocation(location);
        generator.setColumnsSelect(jPanelColumns.getjListColumnsSelect());
        Thread t = new Thread(generator);
        t.start();
    }

    public GeneratorExcel getGeneratorExcel() {
        return generatorExcel;
    }

    public JFrame getjFrame() {
        return jFrame;
    }

    public JPanelMenu getjPanelMenu() {
        return jPanelMenu;
    }

    public JPanelColumns getjPanelColumns() {
        return jPanelColumns;
    }

    public void setjFrame(JFrame jFrame) {
        this.jFrame = jFrame;
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
