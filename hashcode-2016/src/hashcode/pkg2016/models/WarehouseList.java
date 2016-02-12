/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016.models;

import hashcode.pkg2016.DistanceCalculator;
import java.util.ArrayList;

/**
 *
 * @author deanwild
 */
public class WarehouseList extends ArrayList<Warehouse> {

    public Warehouse findBestWareHouse(Order order) {

        Warehouse bestWarehouse = null;
        int bestDistance = Integer.MAX_VALUE;
        for (int warehouseID = 0; warehouseID < size(); warehouseID++) {
            Warehouse warehouse = get(warehouseID);

            if (warehouse.hasProducts(order)) {
                int d = DistanceCalculator.distance(order.X, order.Y, warehouse.X, warehouse.Y);
                if (d < bestDistance) {
                    bestDistance = d;
                    bestWarehouse = warehouse;
                }
            }
        }

        
        // if no warehouse had everything in stock then find the one with the most
        if (bestWarehouse == null) {

            int bestStock = 0;

            for (int warehouseID = 0; warehouseID < size(); warehouseID++) {

                Warehouse warehouse = get(warehouseID);

                int stock = warehouse.amountOfOrderStocked(order);

                if (stock > bestStock) {
                    bestStock = stock;
                    bestWarehouse = warehouse;
                }
            }
        }
        
        return bestWarehouse;

    }

    public Warehouse findBestWareHouse(Drone drone, Order order) {

        Warehouse bestWarehouse = null;
        int bestDistance = Integer.MAX_VALUE;
        for (int warehouseID = 0; warehouseID < size(); warehouseID++) {
            Warehouse warehouse = get(warehouseID);

            if (warehouse.hasProducts(order)) {
                int d = DistanceCalculator.distance(drone.X, drone.Y, warehouse.X, warehouse.Y);
                if (d < bestDistance) {
                    bestDistance = d;
                    bestWarehouse = warehouse;
                }
            }
        }

        

        return bestWarehouse;

    }

    public Warehouse findBestWareHouse(Drone drone, Product product) {
        Warehouse bestWarehouse = null;
        int bestDistance = Integer.MAX_VALUE;
        for (int warehouseID = 0; warehouseID < size(); warehouseID++) {
            Warehouse warehouse = get(warehouseID);
            if (warehouse.hasProduct(product)) {
                int d = DistanceCalculator.distance(drone.X, drone.Y, warehouse.X, warehouse.Y);
                if (d < bestDistance) {
                    bestDistance = d;
                    bestWarehouse = warehouse;
                }
            }
        }

        return bestWarehouse;
    }

}
