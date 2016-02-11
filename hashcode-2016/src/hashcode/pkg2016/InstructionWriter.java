/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author deanwild
 */
public class InstructionWriter {

    public static void writeInstructions(ArrayList<String> instructions, String outputPath) {

        try (PrintStream out = new PrintStream(new FileOutputStream(outputPath))) {
            
            out.println(instructions.size());
            
            for (String instruction : instructions) {
                out.println(instruction);
            }           
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InstructionWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
