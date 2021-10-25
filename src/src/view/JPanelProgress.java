/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.view;

import java.util.Observable;
import java.util.Observer;
import javax.swing.JProgressBar;
import src.controller.Controller;
import src.model.FileExcel;
import src.model.GeneratorByArea;

/**
 *
 * @author judag
 */
public class JPanelProgress extends JProgressBar implements Observer, Runnable {
    
    private Boolean status = false;
    private final Object event = new Object();
    
    private final Controller controller;

    public JPanelProgress(int i, int i1, int i2, Controller controller) {
        super(i, i1, i2);
        initComponents();
        this.controller = controller;
    }

    private void initComponents() {
        setStringPainted(true);
    }

    @Override
    public void update(Observable o, Object o1) {
        try {
            status = (Boolean) o1;
            if (o.getClass() == FileExcel.class && status) {
                controller.getjPanelMenu().getjButtonGenerate().setEnabled(false);
            }
            if (o.getClass() == FileExcel.class && !status) {
                controller.getjPanelMenu().getjButtonGenerate().setEnabled(true);
            }
            if (!status) {
                setValue(0);
                setString("");
                repaint();
            } else {
                setString("running...");
                repaint();
            }
        } catch (ClassCastException e) {}
    }

    @Override
    public void run() {
        synchronized(event) {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    event.wait(100);
                } catch (InterruptedException ex) {}
                if (i > 100) i = 0;
                if (status) {
                    setValue(i);
                    repaint();
                    i += 10;
                }
            }
        }
    }
    
}
