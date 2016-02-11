/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016;

import hashcode.pkg2016.models.Grid;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author deanwild
 */
public class InputParser {

    public Grid parse() {

        
        Grid output = new  Grid();
        
        try {
            FileInputStream fstream;

            fstream = new FileInputStream("example.in");

            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;

            while ((strLine = br.readLine()) != null) {

                System.out.println(strLine);
            }

            br.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return output;
    }

}
