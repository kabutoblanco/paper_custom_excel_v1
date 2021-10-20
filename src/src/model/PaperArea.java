/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

import java.util.ArrayList;

/**
 *
 * @author daniel
 */
public class PaperArea {

    private String area;
    private ArrayList<PaperArea> subAreas;

    public PaperArea(String area) {
        this.area = area;
        this.subAreas = new ArrayList<>();
    }

    public String getArea() {
        return area;
    }

    public ArrayList<PaperArea> getSubAreas() {
        return subAreas;
    }

}
