/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016.models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author deanwild
 */
public class Warehouse extends ArrayList<OrderItem>{
    public int id;
    public int X;
    public int Y;        


public boolean hasProduct(Product product){

    for (OrderItem item : this) {        
        if(item.product == product){
            return true;
        }        
    }
    
    return false;
}

    public void decrementProductStock(Product product) {
        
        Iterator<OrderItem> iterator = iterator();
        
        while(iterator.hasNext()){
            
            OrderItem item = iterator.next();
            
            if(item.product == product){
                iterator.remove();
                return;
            }
        }
    }

    public boolean hasProducts(Order order) {

        /**
         * create a shallow copy o the current stock and simulate trying to 
         * remove all of the items for this order. If we can successfully remove
         * them all then this warehouse has all of the items for this order
         */        
        
        ArrayList<OrderItem> stock = new ArrayList<>(this);        
        Iterator<OrderItem> iterator = stock.iterator();
        
        for (OrderItem orderItem : order) {
            
            boolean productIsInStock = false;
            
            while(iterator.hasNext()){
                OrderItem stockItem = iterator.next();
                if(stockItem.product == orderItem.product){
                    iterator.remove();
                    productIsInStock = true;
                    break;
                }
            }            
            
            if(!productIsInStock) return false;
        }
        
        return true;
    }
   
    public void fulfillOrder(Order order) {
        
        
        Iterator<OrderItem> iterator = iterator();
        
        for (OrderItem orderItem : order) {
                
            boolean productIsInStock = false;
            
            while(iterator.hasNext()){
                OrderItem stockItem = iterator.next();
                if(stockItem.product == orderItem.product){
                    iterator.remove();
                    productIsInStock = true;
                    break;
                }
            }            
            
            if(!productIsInStock){
                throw new RuntimeException("Product unexpectedly out of stock");
            }
        }
        
    }

}
