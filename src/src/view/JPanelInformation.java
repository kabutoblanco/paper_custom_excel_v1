/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import src.model.GeneratorByArea;
import src.response.Response;

/**
 *
 * @author daniel
 */
public class JPanelInformation extends JPanel implements Observer {

    private String nameFile = "not found";
    private String locationFile = File.listRoots()[0].getAbsolutePath();
    private String status = "--";

    private JLabel jLabelNameFile;
    private JLabel jLabelLocationFile;
    private JLabel jLabelStatus;

    public JPanelInformation() {
        initComponents();
    }

    private void initComponents() {
        setBorder(new TitledBorder("Information"));
        jLabelNameFile = new JLabel("Uploaded file: " + nameFile);
        jLabelLocationFile = new JLabel("File location generated: " + locationFile);
        jLabelStatus = new JLabel("Status: " + status);
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(2, 2, 2, 2);
        gb.anchor = GridBagConstraints.LINE_START;
        gb.weightx = 1.0;
        add(jLabelNameFile, gb);
        gb.gridy = 1;
        add(jLabelLocationFile, gb);
        gb.gridy = 2;
        add(jLabelStatus, gb);

    }

    @Override
    public void update(Observable o, Object arg) {
        try {
            Response response = (Response) arg;
            String data = (String) response.getData();
            switch (response.getType()) {
                case 0:
                case 1:
                    status = data;
                    jLabelStatus.setText("Status: " + status);
                    break;
                case 2:
                    nameFile = data;
                    jLabelNameFile.setText("Uploaded file: " + nameFile);
                    break;
                case 3:
                    locationFile = data;
                    jLabelLocationFile.setText("File location generated: " + locationFile.split("//?")[0]);
                    break;
            }
            switch (response.getStatus()) {
                case Response.NORMAL:
                    jLabelStatus.setForeground(Color.darkGray);
                    break;
                case Response.OK:
                    jLabelStatus.setForeground(Color.decode("#006400"));
                    break;
                case Response.FAIL:
                    jLabelStatus.setForeground(Color.decode("#8B0000"));
                    break;
            }
            if (o.getClass() == GeneratorByArea.class && response.getStatus().equals(Response.OK)) {
                String[] options = new String[]{"Open file", "Open directory", "Close"};
                int option = JOptionPane.showOptionDialog(getParent(), "Message", "Title", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                switch (option) {
                    case 0:
                        try {
                            
                            Desktop.getDesktop().open(new File(locationFile.split("\\?")[0]));
                        } catch (IOException ex) {
                            Logger.getLogger(JPanelInformation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case 1: {
                        try {
                            
                            Desktop.getDesktop().open(new File(locationFile.split("\\?")[1]));
                        } catch (IOException ex) {
                            Logger.getLogger(JPanelInformation.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                }
            }
        } catch (ClassCastException e) {}
    }

}
