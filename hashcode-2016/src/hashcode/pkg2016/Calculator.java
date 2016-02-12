package hashcode.pkg2016;

import hashcode.pkg2016.models.CommandList;
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
        CommandList commandList = new CommandList();

        Collections.sort(grid.orders, new Comparator<Order>() {

            @Override
            public int compare(Order t, Order t1) {
                int len1 = t.size();
                int len2 = t1.size();
                int diff = len1 - len2;

                // if they have the same number of items then sort by weight
                if (diff == 0) {
                    int weight1 = t.remainingWeight;
                    int weight2 = t1.remainingWeight;
                    diff = weight1 - weight2;
                }

                return diff;
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

                ArrayList<String> newCommands = null;// tryWorkWholeOrder(drone);

                if (newCommands == null) {
                    // try old method
                    newCommands = workSingleOrderItem(drone);
                }

                commandList.addCommands(newCommands);

            }

        }

        return commandList.results();
    }

    private ArrayList<String> workSingleOrderItem(Drone drone) {

        Order order = grid.orders.get(0);
        OrderItem item = order.get(0);

        Warehouse warehouse = findBestWareHouse(drone, item.product);

        ArrayList<String> commands = new ArrayList<String>();
        int turnCost = 0;

        // load from warehouse
        String load = (drone.id + " L " + warehouse.id + " " + item.product.id + " 1");
        turnCost += 1 + distance(drone.X, drone.Y, warehouse.X, warehouse.Y);
      
        // deliver
        String deliver = (drone.id + " D " + order.id + " " + item.product.id + " 1");
        turnCost += 1 + distance(warehouse.X, warehouse.Y, order.X, order.Y);

        if (drone.nextFreeTime + turnCost < grid.turns) {

            // update warehouse stock level
            warehouse.decrementProductStock(item.product);
            order.remove(item);

            // order complete
            if (order.isEmpty()) {
                grid.orders.remove(order);
            }

            drone.X = order.X;
            drone.Y = order.Y;
            drone.nextFreeTime += turnCost;

            commands.add(load);
            commands.add(deliver);
            return commands;
        }

        return null;

    }

    Warehouse findBestWareHouse(Drone drone, Product product) {
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

    private ArrayList<String> tryWorkWholeOrder(Drone drone) {

        Order order = null;

        for (Order thisOrder : grid.orders) {
            if (thisOrder.remainingWeight < grid.maxPayload) {
                order = thisOrder;
                break;
            }
        }

        if (order == null) {
            return null;
        }

        Warehouse warehouse = findBestWareHouse(drone, order);

        if (warehouse == null) {
            return null;
        }

        // load from warehouse
        ArrayList<String> commands = new ArrayList<String>();

        int turnCost = 0;

        turnCost += distance(drone.X, drone.Y, warehouse.X, warehouse.Y);

        for (OrderItem orderItem : order) {
            turnCost++;
            String loadCommand = (drone.id + " L " + warehouse.id + " " + orderItem.product.id + " 1");
            commands.add(loadCommand);
        }

        // deliver                        
        turnCost += distance(warehouse.X, warehouse.Y, order.X, order.Y);

        for (OrderItem orderItem : order) {
            turnCost++;
            String deliver = (drone.id + " D " + order.id + " " + orderItem.product.id + " 1");
            commands.add(deliver);
        }

        // check if this drone can actually execute the delivery
        if (drone.nextFreeTime + turnCost < grid.turns) {

            /**
             * if it can then - update stock - remove the order - move the drone
             * - update i drone turn
             */
            warehouse.fulfillOrder(order);
            grid.orders.remove(order);
            drone.X = order.X;
            drone.Y = order.Y;
            drone.nextFreeTime += turnCost;
            return commands;
        }

        return null;

    }

    void addCommands() {

    }

    /**
     * Finds the closest warehouse that contains the whole order
     *
     * @param drone
     * @param products
     * @return
     */
    Warehouse findBestWareHouse(Drone drone, Order order) {

        Warehouse bestWarehouse = null;
        int bestDistance = Integer.MAX_VALUE;
        for (int warehouseID = 0; warehouseID < grid.warehouses.size(); warehouseID++) {
            Warehouse warehouse = grid.warehouses.get(warehouseID);
            if (warehouse.hasProducts(order)) {
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
