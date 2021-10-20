/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author daniel
 */
public class JPanelInformation extends JPanel implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private JLabel jLabelNameFileCurrent;
    private JLabel jLabelLocationFileCurrent;
    private JLabel jLabelStatus;

    public JPanelInformation() {
        initComponents();
    }
    
    private void initComponents(){
        setBorder(new TitledBorder("Information"));
        jLabelNameFileCurrent = new JLabel("Uploaded file: not found");
        jLabelLocationFileCurrent = new JLabel("Location of the generated file: /home/");
        jLabelStatus = new JLabel("Status: --");
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(2, 2, 2, 2);
        gb.anchor = GridBagConstraints.LINE_START;
        gb.weightx = 1.0;
        add(jLabelNameFileCurrent, gb);
        gb.gridy = 1;
        add(jLabelLocationFileCurrent, gb);
        gb.gridy = 2;
        add(jLabelStatus, gb);
       
    }
    
}
