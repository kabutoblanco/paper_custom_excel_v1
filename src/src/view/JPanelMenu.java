/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import src.controller.Controller;

/**
 *
 * @author daniel
 */
public class JPanelMenu extends JPanel implements ActionListener {

    private JButton jButtonLoadFile, jButtonGenerate;
    private JFileChooser jFileChooserExcel = new JFileChooser();
    
    private Controller controller;

    public JPanelMenu(Controller controller) {
        initComponents();
        this.controller = controller;
    }

    private void initComponents() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL FILES", "xlsx", "xls");
        jFileChooserExcel.setFileFilter(filter);
        jButtonLoadFile = new JButton("CARGAR");
        jButtonGenerate = new JButton("GENERAR");
        jButtonLoadFile.addActionListener(this);
        jButtonGenerate.addActionListener(this);
        add("loadFile", jButtonLoadFile);
        add("generate", jButtonGenerate);
    }
    
    public void notifyOpenFile(File fileExcel) {
        controller.loadFile(fileExcel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {        
        if (e.getSource() == jButtonLoadFile) {
            int returnVal = jFileChooserExcel.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooserExcel.getSelectedFile();
                notifyOpenFile(file);
            }
        }
        
        if (e.getSource() == jButtonGenerate) {
            controller.generateFile("/home/daniel/Documents/");
        }
    }
    
}
