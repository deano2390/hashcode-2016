
package hashcode.pkg2016.models;

import java.util.ArrayList;

/**
 *
 * @author deanwild
 */
public class Grid {
    
    public int rows;
    public int columns;
    public int maximumTime;
    public int maxPayload;
        public int turns;
    public ArrayList<Drone> drones = new ArrayList<>();
    public ArrayList<Warehouse> warehouses = new ArrayList<>();
    public ArrayList<Order> orders = new ArrayList<>();
    public ArrayList<Product> products = new ArrayList<>();

    
    
}
