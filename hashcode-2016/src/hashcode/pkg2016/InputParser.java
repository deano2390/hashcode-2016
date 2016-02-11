/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016;

import hashcode.pkg2016.models.Drone;
import hashcode.pkg2016.models.Grid;
import hashcode.pkg2016.models.Order;
import hashcode.pkg2016.models.OrderItem;
import hashcode.pkg2016.models.Product;
import hashcode.pkg2016.models.Warehouse;
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
            
            int droneCount = Integer.parseInt(strValues[2]);
            
            for (int i = 0; i < droneCount; i++) {
                grid.drones.add(new Drone());
            }
            
            
            int turns = Integer.parseInt(strValues[3]);
            grid.turns = turns;
            
            int maxPayload = Integer.parseInt(strValues[4]);
            grid.maxPayload = maxPayload;
            
            strLine = br.readLine();            //skip a line
            strLine = br.readLine();    // product weights        
            strValues = strLine.split(" ");
            
            int productTypeCount = strValues.length;
           
                       
            for (int i = 0; i < productTypeCount; i++) {
                Product product = new Product();
                product.type = i;
                product.weight = asInt(strValues[i]);
                grid.products.add(product);
            }
            
            ArrayList<Product> allProducts = grid.products;
            
            strLine = br.readLine();    // warehouse count
            int warehouseCount = asInt(strLine);
            
            for (int i = 0; i < warehouseCount; i++) {
                strLine = br.readLine();    // warehouse location
                strValues = strLine.split(" ");
                Warehouse warehouse = new Warehouse();
                warehouse.X = asInt(strValues[0]);
                warehouse.Y = asInt(strValues[1]);
                
                strLine = br.readLine();    // warehouse contents
                strValues = strLine.split(" ");
                
                for (int productID = 0; productID < productTypeCount; productID++) {                   
                    warehouse.products.put(allProducts.get(productID), asInt(strValues[productID]));                    
                }
                
                grid.warehouses.add(warehouse);                
                
            }
            
            strLine = br.readLine();    // order count
            int orderCount = asInt(strLine);
            
            
            for (int i = 0; i < orderCount; i++) {
                strLine = br.readLine();    // order destination
                strValues = strLine.split(" ");
                Order order = new Order();
                order.X = asInt(strValues[0]);
                order.Y = asInt(strValues[1]);
                
                strLine = br.readLine();    // order item count
                int itemCount = asInt(strLine);
                
                strLine = br.readLine();    // order item quantities
                strValues = strLine.split(" ");
                
                for (int itemNumber = 0; itemNumber < itemCount; itemNumber++) {
                    int productID = asInt(strValues[itemNumber]);
                    Product product = allProducts.get(productID);
                    OrderItem orderItem = new OrderItem();
                    orderItem.product = product;
                    orderItem.quantity = 1;              
                    order.items.add(orderItem);
                }  
                
                
                grid.orders.add(order);
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
        return Integer.parseInt(in.trim());
    }

}
