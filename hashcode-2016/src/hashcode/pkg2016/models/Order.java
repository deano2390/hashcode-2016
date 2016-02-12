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
public class Order {

    public int id;
    public int X;
    public int Y;
    public int remainingWeight;

    public ArrayList<OrderItem> items = new ArrayList<>();
    
    public Order(int id) {
        this.id = id;       
    }
    
            
    public void addItem(Product product) {
    
        for (OrderItem orderItem : items) {
            if(orderItem.product == product){
                orderItem.quantity++;
                remainingWeight += orderItem.product.weight;
                return;
            }
        }
        
        // new item type, create it
        OrderItem orderItem = new OrderItem(product, 1);
        remainingWeight += orderItem.product.weight;
        items.add(orderItem);        
    }
    
    public void decrementItem(Product product){
        
        
        for (OrderItem orderItem : items) {
            if(orderItem.product == product){
                orderItem.quantity--;
                remainingWeight -= orderItem.product.weight;                
                if(orderItem.quantity <= 0){
                    items.remove(orderItem);
                }
                
                return;
            }
        }
    }
    
    // the minimum number of turns needed to fulfill this order before even considering distance
    public int turnOverhead(){        
        return items.size() * 2;        
    }
    
    
        
    
}
