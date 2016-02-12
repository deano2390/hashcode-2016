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
    public OrderList orders = new OrderList();
    public ArrayList<Product> products = new ArrayList<>();

    
}
