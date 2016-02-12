package hashcode.pkg2016;

import hashcode.pkg2016.models.CommandList;
import hashcode.pkg2016.models.Drone;
import hashcode.pkg2016.models.DroneList;
import hashcode.pkg2016.models.Grid;
import hashcode.pkg2016.models.Order;
import hashcode.pkg2016.models.OrderItem;
import hashcode.pkg2016.models.Warehouse;
import hashcode.pkg2016.models.WarehouseList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Neil C Smith (http://neilcsmith.net)
 * @author Dean Wild
 */
public class Calculator {

    private final Grid grid;
    private final WarehouseList warehouses;
    private final DroneList drones;

    public Calculator(Grid grid) {
        this.grid = grid;
        this.warehouses = grid.warehouses;
        this.drones = grid.drones;
    }

    public String[] calculate() {
        CommandList commandList = new CommandList();

        grid.orders.initialSort();

        int counter = 0;

        while (!grid.orders.isEmpty()) {
            
                        
            ArrayList<String> newCommands = null;

            Order bestOrder = grid.orders.findBest();

            if (bestOrder != null) {
                Warehouse bestWareHouse = warehouses.findBestWareHouse(bestOrder);

                if (bestWareHouse != null) {
                    Drone bestDrone = drones.findBest(bestOrder, bestWareHouse);

                    if (bestDrone != null) {
                        newCommands = fulfillOrder(bestOrder, bestWareHouse, bestDrone);
                    }
                }
               
                commandList.addCommands(newCommands);

            } else {
                break;
            }
            
            log("orders: " + grid.orders.size());

            counter++;
        }

        return commandList.results();
    }

    void log(String text) {
        Logger.getLogger(InputParser.class.getName()).log(Level.SEVERE, text, 0);
    }

    private ArrayList<String> fulfillOrder(Order order, Warehouse warehouse, Drone drone) {

        int turnCost = 0;
        turnCost += DistanceCalculator.distance(drone.X, drone.Y, warehouse.X, warehouse.Y);
        turnCost += DistanceCalculator.distance(warehouse.X, warehouse.Y, order.X, order.Y);

        
        drone.nextFreeTime += turnCost;

        ArrayList<String> commands = new ArrayList<String>();

        Iterator<OrderItem> itemsIterator = order.items.iterator();

        while (itemsIterator.hasNext()) {

            OrderItem item = itemsIterator.next();

            // keep filling until the drone is full or we run out of items
            while (item.quantity > 0) {
                
                if(!warehouse.hasProduct(item.product)){
                    break;
                }
                
                boolean didLoad = drone.tryLoadItem(item.product);
                if (didLoad) {

                    warehouse.decrementProductStock(item.product);
                    
                    item.quantity--;
                    order.remainingWeight -= item.product.weight;
                    
                    if (item.quantity <= 0) {
                        itemsIterator.remove();                       

                        if (order.items.isEmpty()) {
                            grid.orders.remove(order);
                            break;
                        }
                    }

                } else {
                    break;
                }
            }
        }

        commands = drone.executeCommands(warehouse, order);
       
        if (drone.nextFreeTime < grid.MAX_TIME) {
            
            return commands;
        } else {
            log("bail out");
            throw new RuntimeException("trying to send drone with too many commands");
        }
    }

    private ArrayList<String> tryWorkWholeOrder(Drone drone) {

        Order order = null;

        for (Order thisOrder : grid.orders) {
            if (thisOrder.remainingWeight < grid.MAX_PAYLOAD) {
                order = thisOrder;
                break;
            }
        }

        if (order == null) {
            return null;
        }

        Warehouse warehouse = warehouses.findBestWareHouse(drone, order);

        if (warehouse == null) {
            return null;
        }

        // load from warehouse
        ArrayList<String> commands = new ArrayList<String>();

        int turnCost = 0;

        turnCost += DistanceCalculator.distance(drone.X, drone.Y, warehouse.X, warehouse.Y);

        for (OrderItem orderItem : order.items) {
            turnCost++;
           
           // String loadCommand = drone.load(warehouse.id, orderItem);
           // commands.add(loadCommand);
        }

        // deliver                        
        turnCost += DistanceCalculator.distance(warehouse.X, warehouse.Y, order.X, order.Y);

        for (OrderItem orderItem : order.items) {
            turnCost++;
           
           // String deliverCmd = drone.deliver(order.id, orderItem);
           // commands.add(deliverCmd);
        }

        // check if this drone can actually execute the delivery
        if (drone.nextFreeTime + turnCost < grid.MAX_TIME) {

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

    private ArrayList<String> workSingleOrderItem(Drone drone) {

        Order order = grid.orders.get(0);
        OrderItem orderItem = order.items.get(0);

        Warehouse warehouse = warehouses.findBestWareHouse(drone, orderItem.product);

        ArrayList<String> commands = new ArrayList<String>();
        int turnCost = 0;

        // load from warehouse        
        //String loadCmd = drone.load(warehouse.id, orderItem, 1);
        //commands.add(loadCmd);
        turnCost += 1 + DistanceCalculator.distance(drone.X, drone.Y, warehouse.X, warehouse.Y);

        // deliver        
        //String deliverCmd = drone.deliver(order.id, orderItem, 1);
        //commands.add(deliverCmd);
        turnCost += 1 + DistanceCalculator.distance(warehouse.X, warehouse.Y, order.X, order.Y);

        if (drone.nextFreeTime + turnCost < grid.MAX_TIME) {

            // update warehouse stock level
            warehouse.decrementProductStock(orderItem.product);
            order.decrementItem(orderItem.product);

            // order complete
            if (order.items.isEmpty()) {
                grid.orders.remove(order);
            }

            drone.X = order.X;
            drone.Y = order.Y;
            drone.nextFreeTime += turnCost;

            return commands;
        }

        return null;
    }

}
