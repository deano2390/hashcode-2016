/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016;

import hashcode.pkg2016.models.Grid;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author deanwild
 */
public class Hashcode2016 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ArrayList<String> attempts = new ArrayList<>();

        attempts.add("busy_day");
        attempts.add("mother_of_all_warehouses");
        attempts.add("redundancy");

        for (String attempt : attempts) {
            
            String inFile = "assets/" + attempt + ".in";
            String outFile  = attempt + ".out";
            InputParser parser = new InputParser(new File(inFile));
            Grid grid = parser.parse();
            Calculator calculator = new Calculator(grid);
            String[] output = calculator.calculate();

//        System.out.println(output.length);
//        for (String out : output) {
//            System.out.println(out);
//        }
            InstructionWriter.writeInstructions(output, outFile);
        }

    }

}
