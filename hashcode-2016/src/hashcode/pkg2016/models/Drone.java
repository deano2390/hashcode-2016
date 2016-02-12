/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016.models;

/**
 *
 * @author deanwild
 */
public class Drone {

    public int id;
    public int X;
    public int Y;
    public int nextFreeTime;

    public Drone(int id, int startX, int startY) {
        this.id = id;
        this.X = startX;
        this.Y = startY;
    }

    public String load(int warehouseID, OrderItem orderItem) {
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
    }

    int currentPayloadWeight = 0;

    private void modifyWeight(int weightDiff) {
        currentPayloadWeight += weightDiff;

        if (currentPayloadWeight < 0) {
            throw new RuntimeException("negative weight on drone: " + id);
        }

        if (currentPayloadWeight > Grid.MAX_PAYLOAD) {
            throw new RuntimeException("too much weight on drone: " + id);
        }
    }

}
