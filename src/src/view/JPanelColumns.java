/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

/**
 *
 * @author daniel
 */
public class JPanelColumns extends JPanel implements ActionListener {

    private JList jListColumnsAvaliable;
    private JList jListColumnsSelect;
    private JButton jButtonSelect;
    private JButton jButtonUnselect;

    public JPanelColumns() {
        initComponents();
    }

    private void initComponents() {
        jListColumnsAvaliable = new JList();
        jListColumnsSelect = new JList();
        jButtonSelect = new JButton(">");
        jButtonUnselect = new JButton("<");
        jButtonSelect.addActionListener(this);
        jButtonUnselect.addActionListener(this);
        add("columnsAvaliable", jListColumnsAvaliable);
        add("columnsSelect", jListColumnsSelect);
        add("select", jButtonSelect);
        add("unselect", jButtonUnselect);
    }

    public void loadColumns(ArrayList<String> headers) {
        DefaultListModel listModelAvaliable = new DefaultListModel();
        DefaultListModel listModelSelect = new DefaultListModel();
        headers.forEach((header) -> {
            listModelAvaliable.addElement(header);
        });
        jListColumnsAvaliable.setModel(listModelAvaliable);
        jListColumnsSelect.setModel(listModelSelect);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jButtonSelect) {
            List values = jListColumnsAvaliable.getSelectedValuesList();         
            DefaultListModel listModelAvaliable = (DefaultListModel) jListColumnsAvaliable.getModel();
            DefaultListModel listModelSelect = (DefaultListModel) jListColumnsSelect.getModel();
            for (Object value : values) {                
                listModelAvaliable.removeElement(value);
                listModelSelect.addElement(value);
            }
            jListColumnsSelect.setModel(listModelSelect);
        }
        
        if (e.getSource() == jButtonUnselect) {
            List values = jListColumnsSelect.getSelectedValuesList();         
            DefaultListModel listModelAvaliable = (DefaultListModel) jListColumnsAvaliable.getModel();
            DefaultListModel listModelSelect = (DefaultListModel) jListColumnsSelect.getModel();
            for (Object value : values) {                
                listModelAvaliable.addElement(value);
                listModelSelect.removeElement(value);
            }
            jListColumnsSelect.setModel(listModelSelect);
        }
    }

    public Object[] getjListColumnsSelect() {
        DefaultListModel listModelSelect = (DefaultListModel) jListColumnsSelect.getModel();
        Object[] list = listModelSelect.toArray();
        return list;
    }
    
}
