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
import javax.swing.JOptionPane;
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
    
    private final JFileChooser jFileChooserExcel = new JFileChooser();
    private final JFileChooser jFileChooserGenerate = new JFileChooser();
    
    private final Controller controller;

    public JPanelMenu(Controller controller) {
        initComponents();
        this.controller = controller;
    }

    private void initComponents() {
        //Config components
        setBorder(new TitledBorder("Menu"));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("EXCEL FILES", "xlsx", "xls");
        jFileChooserExcel.setFileFilter(filter);
        jFileChooserGenerate.setFileFilter(filter);
        jButtonLoadFile = new JButton("UPLOAD");
        jButtonGenerate = new JButton("GENERATE");
        jButtonClear = new JButton("RESET");
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
        //add(jButtonClear, gb);
        add(jButtonClose, gb);
    }

    @Override
    public void actionPerformed(ActionEvent e) {        
        if (e.getSource() == jButtonLoadFile) {
            int returnVal = jFileChooserExcel.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = jFileChooserExcel.getSelectedFile();
                controller.loadFile(file);
            }
        }
        
        if (e.getSource() == jButtonGenerate) {
            if (controller.getGeneratorExcel().getFileExcel() != null) {
                if (controller.getjPanelColumns().getjListColumnsSelect().length > 1) {
                    jFileChooserGenerate.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    jFileChooserGenerate.setSelectedFile(new File("report.xlsx"));
                    jFileChooserGenerate.setAcceptAllFileFilterUsed(false);
                    int returnVal = jFileChooserGenerate.showSaveDialog(this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        String name = jFileChooserGenerate.getSelectedFile().getName();
                        String directory = jFileChooserGenerate.getCurrentDirectory().getAbsolutePath();
                        if (name.endsWith(".xls") || name.endsWith(".xlsx"))
                            controller.generateFile(jFileChooserGenerate.getSelectedFile().getAbsolutePath(), directory);                            
                        else
                            controller.generateFile(jFileChooserGenerate.getSelectedFile().getAbsolutePath() + ".xlsx", directory);
                    }
                } else {
                    JOptionPane.showMessageDialog(controller.getjFrame(), "Selected one more columns", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(controller.getjFrame(), "Loaded one file", "Error", JOptionPane.ERROR_MESSAGE);
            } 
        }
        
        if (e.getSource() == jButtonClose) {
            controller.getjFrame().setVisible(false);
            controller.getjFrame().dispose();
            System.exit(0);
        }
    }

    public JButton getjButtonGenerate() {
        return jButtonGenerate;
    }
    
}
