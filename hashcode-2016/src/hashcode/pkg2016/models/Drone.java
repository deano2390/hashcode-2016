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
public class Drone extends ArrayList<OrderItem> {

    public int id;
    public int X;
    public int Y;
    public int nextFreeTime;
    int currentPayloadWeight = 0;

    public Drone(int id, int startX, int startY) {
        this.id = id;
        this.X = startX;
        this.Y = startY;
    }

    /*public String load(int warehouseID, OrderItem orderItem) {
        return this.load(warehouseID, orderItem, orderItem.quantity);
    }

    public String load(int warehouseID, OrderItem orderItem, int quantity) {

        int weight = orderItem.product.weight * quantity;
        modifyWeight(weight);

        return id + " L " + warehouseID + " " + orderItem.product.id + " " + quantity;
    }

    public String deliver(int orderID, OrderItem orderItem) {
        return this.deliver(orderID, orderItem, orderItem.quantity);
    }

    public String deliver(int orderID, OrderItem orderItem, int quantity) {

        int weight = orderItem.product.weight * quantity;
        modifyWeight(-weight);

        return id + " D " + orderID + " " + orderItem.product.id + " " + quantity;
    }*/

    public int getSpareCapacity() {
        return Grid.MAX_PAYLOAD - currentPayloadWeight;
    }

    public boolean hasSpareCapacity() {
        return getSpareCapacity() > 0;
    }

    private void modifyWeight(int weightDiff) {
        currentPayloadWeight += weightDiff;

        if (currentPayloadWeight < 0) {
            throw new RuntimeException("negative weight on drone: " + id);
        }

        if (currentPayloadWeight > Grid.MAX_PAYLOAD) {
            throw new RuntimeException("too much weight on drone: " + id);
        }
    }

    public boolean tryLoadItem(Product product) {

        if (currentPayloadWeight + product.weight <= Grid.MAX_PAYLOAD) {
            return loadItem(product);
        }

        return false;
    }

    private boolean loadItem(Product product) {

        for (OrderItem item : this) {
            if (item.product == product) {
                item.quantity++;
                modifyWeight(product.weight);
                return true;
            }
        }

        // only allow the new product type if we have enough room
        // for an extra 2 moves
        if (nextFreeTime < Grid.MAX_TIME - 1) {
            OrderItem item = new OrderItem(product, 1);
            add(item);
            modifyWeight(product.weight);
            nextFreeTime += 2;
            return true;
        }

        return false;

    }

    public void unloadItem(Product product) {
        for (OrderItem item : this) {
            if (item.product == product) {

                item.quantity--;
                modifyWeight(-product.weight);

                if (item.quantity <= 0) {
                    remove(item);
                }

                return;
            }
        }

        throw new RuntimeException("tried to unload product that wasn't on board");
    }

    public int itemsOnBoard() {

        int itemsOnBoard = 0;
        for (OrderItem item : this) {
            if (item.quantity > 0) {
                itemsOnBoard++;
            }
        }
        return itemsOnBoard;
    }

    public ArrayList<String> executeCommands(Warehouse warehouse, Order order) {

        ArrayList<String> commands = new ArrayList<>();

        for (OrderItem item : this) {
            commands.add(id + " L " + warehouse.id + " " + item.product.id + " " + item.quantity);
        }

        for (OrderItem item : this) {
            commands.add(id + " D " + order.id + " " + item.product.id + " " + item.quantity);
        }

        // update our position
        X = order.X;
        Y = order.Y;
        
        currentPayloadWeight = 0;
        this.clear();

        return commands;
    }

    
}
