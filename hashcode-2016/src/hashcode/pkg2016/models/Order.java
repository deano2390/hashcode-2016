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
public class Order extends ArrayList<OrderItem> {

    public int id;
    public int X;
    public int Y;
    public int remainingWeight;
   
  
    @Override
    public boolean add(OrderItem item) {
        remainingWeight += item.product.weight;
        return super.add(item); 
    }
    
    public boolean remove(OrderItem item) {
        remainingWeight -= item.product.weight;
        return super.remove(item);
    }
    
    
    
    
        
    
}
