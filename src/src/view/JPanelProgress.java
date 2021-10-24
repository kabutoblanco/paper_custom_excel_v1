/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.view;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

/**
 *
 * @author judag
 */
public class JPanelProgress extends JProgressBar implements Observer, Runnable {

    Boolean status = false;

    public JPanelProgress(int i, int i1, int i2) {
        super(i, i1, i2);
        initComponents();
    }

    private void initComponents() {
        setStringPainted(true);
    }

    @Override
    public void update(Observable o, Object o1) {
        try {
            status = (Boolean) o1;
            if(!status){
                setValue(0);
                setString("");
                repaint();
            }else{
                setString("running...");
                repaint();
            }
            System.out.println(status);
        } catch (ClassCastException e) {

        }

    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            try {
                Thread.sleep(50);
                if (i > 100) {
                    i = 0;
                }
                if (status) {
                    setValue(i);
                    repaint();
                    i += 10;
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(JPanelProgress.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
}
