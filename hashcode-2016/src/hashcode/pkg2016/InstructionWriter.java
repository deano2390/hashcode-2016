/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author deanwild
 */
public class InstructionWriter {

    public static void writeInstructions(String[] commands, String fileName) {

        
        String outputPath  = System.getProperty("user.home") + "/hashcode";
        
        File folder = new File(outputPath);
        folder.mkdirs();
        
        File file = new File(outputPath + "/" + fileName +  ".txt");
        
        try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
            
            out.println(commands.length);
            
            for (String instruction : commands) {
                out.println(instruction);
            }           
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InstructionWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
