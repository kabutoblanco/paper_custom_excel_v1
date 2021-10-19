/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.view;

import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author daniel
 */
public class JFrameMain extends JFrame {
    private JPanelMenu jPanelMenu;
    private JPanelColumns jPanelColumns;
    private JPanelInformation jPanelInformation;

    public JFrameMain(JPanelMenu jPanelMenu, JPanelColumns jPanelColumns, JPanelInformation jPanelInformation, String title) throws HeadlessException {
        super(title);
        this.jPanelMenu = jPanelMenu;
        this.jPanelColumns = jPanelColumns;
        this.jPanelInformation = jPanelInformation;
    }
    
    public void config() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout (new GridBagLayout());
        getContentPane().add(jPanelMenu, 0);
        getContentPane().add(jPanelColumns, 1);
        pack();
    }
    
}
