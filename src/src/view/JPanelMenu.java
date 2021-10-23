/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import src.controller.Controller;

/**
 *
 * @author daniel
 */
public class JPanelMenu extends JPanel implements ActionListener {

    private JButton jButtonLoadFile, jButtonGenerate, jButtonClear, jButtonClose;
    private JFileChooser jFileChooserExcel = new JFileChooser();
    private JFileChooser jFileChooserGenerate = new JFileChooser();
    
    private Controller controller;

    public JPanelMenu(Controller controller) {
        initComponents();
        this.controller = controller;
    }

    private void initComponents() {
        //Config components
        setBorder(new TitledBorder("Menu"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL FILES", "xlsx", "xls");
        jFileChooserExcel.setFileFilter(filter);
        jButtonLoadFile = new JButton("UPLOAD");
        jButtonGenerate = new JButton("GENERATE");
        jButtonClear = new JButton("CLEAN");
        jButtonClose = new JButton("CLOSE");
        jButtonLoadFile.addActionListener(this);
        jButtonGenerate.addActionListener(this);
        jButtonClear.addActionListener(this);
        jButtonClose.addActionListener(this);
        //Config layout       
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.insets = new Insets(2, 2, 2, 2);
        gb.gridx = 0;
        gb.fill = GridBagConstraints.HORIZONTAL;
        add(jButtonLoadFile, gb);
        add(jButtonGenerate, gb);
        add(jButtonClear, gb);
        add(jButtonClose, gb);
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
            jFileChooserGenerate.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jFileChooserGenerate.setAcceptAllFileFilterUsed(false);
            int returnVal = jFileChooserGenerate.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                controller.generateFile(jFileChooserGenerate.getSelectedFile().getAbsoluteFile()+ File.separator);
            }
            
        }
    }
    
}
