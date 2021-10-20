/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import javax.swing.JFrame;

/**
 *
 * @author daniel
 */
public class JFrameMain extends JFrame {
    private JPanelMenu jPanelMenu;
    private JPanelColumns jPanelColumns;
    private JPanelInformation jPanelInformation;
    private JPanelProgress jPanelProgress;

    public JFrameMain(JPanelMenu jPanelMenu, JPanelColumns jPanelColumns, JPanelInformation jPanelInformation, JPanelProgress jPanelProgress, String title) throws HeadlessException {
        super(title);
        this.jPanelMenu = jPanelMenu;
        this.jPanelColumns = jPanelColumns;
        this.jPanelInformation = jPanelInformation;
        this.jPanelProgress = jPanelProgress;
    }
    
    public void config() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout (new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(1, 1, 1, 1);
        gb.fill = GridBagConstraints.HORIZONTAL;
        gb.weighty = 1.0;
        gb.anchor = GridBagConstraints.PAGE_START;
        getContentPane().add(jPanelMenu, gb);
        gb.gridx = 1;
        getContentPane().add(jPanelColumns, gb);
        gb.gridx = 0;
        gb.gridy = 1;
        gb.gridwidth = 2;
        getContentPane().add(jPanelInformation, gb);
        gb.gridy = 2;
        gb.gridwidth = 3;
        getContentPane().add(jPanelProgress, gb);
        pack();
    }
    
}
