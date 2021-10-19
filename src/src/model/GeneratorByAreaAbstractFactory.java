/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src.model;

/**
 *
 * @author daniel
 */
public class GeneratorByAreaAbstractFactory extends AbstractFactory {

    @Override
    public GeneratorExcel createGeneratorExcel() {
        return new GeneratorByArea();
    }
    
}
