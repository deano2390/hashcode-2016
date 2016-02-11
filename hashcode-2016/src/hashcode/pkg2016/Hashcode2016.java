/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016;

import hashcode.pkg2016.models.Grid;
import java.io.File;

/**
 *
 * @author deanwild
 */
public class Hashcode2016 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        InputParser parser = new InputParser(new File(args[0]));
        Grid grid = parser.parse();
        Calculator calculator = new Calculator(grid);
        String[] output = calculator.calculate();
        
        System.out.println(output.length);
        for (String out : output) {
            System.out.println(out);
        }
    }
    
}
