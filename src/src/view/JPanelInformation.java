/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
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
    private String nameFileCurrent = "not found";
    private String locationFileCurrent = "/home/";
    private String status = "--";
    
    @Override
    public void update(Observable o, Object arg) {
            /*ArrayList<String> status = (ArrayList<String>) arg;
            nameFileCurrent = status.get(0);
            locationFileCurrent = status.get(1);
            jLabelNameFileCurrent.setText("Uploaded file: " + nameFileCurrent);
            jLabelLocationFileCurrent.setText("Location of the generated file: " + locationFileCurrent);*/
            
            try{
                String[] statusAux = ((String) arg).split("-");
                status = statusAux[1];
                jLabelStatus.setText("Status: " + status);
            }catch(ClassCastException e){
                
            }
    }
    
    private JLabel jLabelNameFileCurrent;
    private JLabel jLabelLocationFileCurrent;
    private JLabel jLabelStatus;

    public JPanelInformation() {
        initComponents();
    }
    
    private void initComponents(){
        setBorder(new TitledBorder("Information"));
        jLabelNameFileCurrent = new JLabel("Uploaded file: " + nameFileCurrent);
        jLabelLocationFileCurrent = new JLabel("Location of the generated file: " + locationFileCurrent);
        jLabelStatus = new JLabel("Status: " + status);
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
