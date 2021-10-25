/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import javax.swing.JProgressBar;
import src.controller.Controller;
import src.view.JFrameMain;
import src.view.JPanelMenu;
import src.view.JPanelColumns;
import src.view.JPanelInformation;
import src.view.JPanelProgress;

/**
 *
 * @author daniel
 */
public class client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {    
        Controller controller = new Controller();
        JPanelMenu jPanelMenu = new JPanelMenu(controller);
        JPanelColumns jPanelColumns = new JPanelColumns();
        JPanelInformation jPanelInformation = new JPanelInformation();
        JPanelProgress jPanelProgress = new JPanelProgress(JProgressBar.HORIZONTAL, 0, 100, controller);
        Thread t = new Thread(jPanelProgress);
        t.start();
        // Config controller        
        controller.setjPanelProgress(jPanelProgress);
        controller.setjPanelMenu(jPanelMenu);
        controller.setjPanelCollumns(jPanelColumns);
        controller.setjPanelInformation(jPanelInformation);
        controller.createGeneraterExcel();
        // Config frame
        JFrameMain jFrameMain = new JFrameMain(jPanelMenu, jPanelColumns, jPanelInformation,jPanelProgress, "Test");
        controller.setjFrame(jFrameMain);
        jFrameMain.config();
        jFrameMain.setVisible(true);
    }
    
    
    
}
