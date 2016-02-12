package hashcode.pkg2016.models;

import java.util.ArrayList;

/**
 *
 * @author deanwild
 */
public class Grid {

    public int rows;
    public int columns;
    public static int MAX_TIME;
    public static int MAX_PAYLOAD;
    
    public DroneList drones = new DroneList();
    public WarehouseList warehouses = new WarehouseList();
    public ArrayList<Order> orders = new ArrayList<>();
    public ArrayList<Product> products = new ArrayList<>();

    
}
