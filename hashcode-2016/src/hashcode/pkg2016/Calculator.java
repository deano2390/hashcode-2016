package hashcode.pkg2016;

import hashcode.pkg2016.models.Drone;
import hashcode.pkg2016.models.Grid;
import hashcode.pkg2016.models.Order;
import hashcode.pkg2016.models.OrderItem;
import hashcode.pkg2016.models.Product;
import hashcode.pkg2016.models.Warehouse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Neil C Smith (http://neilcsmith.net)
 * @author Dean Wild
 */
public class Calculator {

    private final Grid grid;

    public Calculator(Grid grid) {
        this.grid = grid;
    }

    public String[] calculate() {
        ArrayList<String> commands = new ArrayList<>();

        Collections.sort(grid.orders, new Comparator<Order>() {

            @Override
            public int compare(Order t, Order t1) {
                int len1 = t.size();
                int len2 = t.size();
                return len1 < len2 ? -1 : len1 == len2 ? 0 : 1;
            }
        });

        
        for (int t = 0; t < grid.turns; t++) {

            if (grid.orders.isEmpty()) {
                break;
            }

            for (Drone drone : grid.drones) {
                
                if (drone.nextFreeTime > t) {
                    continue;
                }
                
                workDrone(drone, commands);
               
            }

        }

        return commands.toArray(new String[commands.size()]);
    }

    private void workDrone(Drone drone, List<String> commands) {
        
        Order order = grid.orders.get(0);
        OrderItem item = order.get(0);
        
        Warehouse warehouse = findBestWareHouse(drone, item.product);   
        
                
        // update warehouse stock level
        warehouse.decrementProductStock(item.product);
        
        order.remove(item);
        
        // order complete
        if (order.isEmpty()) {
            grid.orders.remove(order);
        }

        // load from warehouse
        String load = (drone.id + " L " + warehouse.id + " " + item.product.id + " 1");
        drone.nextFreeTime += 1 + distance(drone.X, drone.Y, warehouse.X, warehouse.Y);
        drone.X = warehouse.X;
        drone.Y = warehouse.Y;

        // deliver
        String deliver = (drone.id + " D " + order.id + " " + item.product.id + " 1");
        drone.nextFreeTime += 1 + distance(drone.X, drone.Y, order.X, order.Y);
        drone.X = order.X;
        drone.Y = order.Y;

        if (drone.nextFreeTime < grid.turns) {
            commands.add(load);
            commands.add(deliver);
        }
                
    }

    private Warehouse findBestWareHouse(Drone drone, Product product) {
        Warehouse bestWarehouse = null;
        int bestDistance = Integer.MAX_VALUE;
        for (int warehouseID = 0; warehouseID < grid.warehouses.size(); warehouseID++) {
            Warehouse warehouse = grid.warehouses.get(warehouseID);
            if (warehouse.hasProduct(product)) {
                int d = distance(drone.X, drone.Y, warehouse.X, warehouse.Y);
                if (d < bestDistance) {
                    bestDistance = d;
                    bestWarehouse = warehouse;
                }
            }
        }

        return bestWarehouse;
     
    }

    private int distance(int x1, int y1, int x2, int y2) {
        int dX = Math.abs(x2 - x1);
        int dY = Math.abs(y2 - y1);
        double hyp = Math.sqrt((dX * dX) + (dY * dY));
        return (int) Math.ceil(hyp);
    }

}
