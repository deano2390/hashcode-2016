/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016.models;

import java.util.ArrayList;

/**
 *
 * @author deanwild
 */
public class Grid {
    
    public int rows;
    public int columns;
    public int turns;
    public int maxPayload;
    public ArrayList<Drone> drones = new ArrayList<>();
    public ArrayList<Warehouse> warehouses = new ArrayList<>();
    public ArrayList<Order> orders = new ArrayList<>();
    public ArrayList<Product> products = new ArrayList<>();
    
    
}
