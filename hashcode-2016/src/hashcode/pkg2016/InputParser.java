/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016;

import hashcode.pkg2016.models.Grid;
import hashcode.pkg2016.models.Product;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author deanwild
 */
public class InputParser {
    private final File file;

    public InputParser(File file){
        this.file = file;
    }
    
    public Grid parse() {

        
        Grid grid = new  Grid();
        
        try {
            FileInputStream fstream;

            fstream = new FileInputStream(file);

            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine = br.readLine(); // grid size
            
            String[] strValues = strLine.split(" ");
            
            grid.rows = Integer.parseInt(strValues[0]);
            grid.columns = Integer.parseInt(strValues[1]);            
            int droneCount = Integer.parseInt(strValues[3]);
            int turns = Integer.parseInt(strValues[4]);
            int maxPayload = Integer.parseInt(strValues[5]);
                       
            strLine = br.readLine();            //skip a line
            strLine = br.readLine();    // product weights        
            strValues = strLine.split(" ");
            
            int productTypeCount = strValues.length;
            
            grid.products = new ArrayList<>();
            
            for (int i = 0; i < productTypeCount; i++) {
                Product product = new Product();
                product.type = i;
                product.weight = asInt(strValues[i]);
                grid.products.add(product);
            }
            
            
            
            
            
            
            

            br.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InputParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return grid;
    }
    
    int asInt(String in){
        return Integer.parseInt(in);
    }

}
