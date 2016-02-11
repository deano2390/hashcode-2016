/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016.models;

import java.util.HashMap;

/**
 *
 * @author deanwild
 */
public class Warehouse {
    public int id;
    public int X;
    public int Y;
    
    public HashMap<Product, Integer> products= new HashMap<>(); 
}
