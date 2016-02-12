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
public class Warehouse extends ArrayList<OrderItem> {

    public int id;
    public int X;
    public int Y;

    public Warehouse(int id) {
        this.id = id;
    }

    public boolean hasProduct(Product product) {

        for (OrderItem item : this) {
            if (item.product == product) {
                return item.quantity > 0;
            }
        }

        return false;
    }

    public void decrementProductStock(Product product) {

        Iterator<OrderItem> iterator = iterator();

        while (iterator.hasNext()) {

            OrderItem item = iterator.next();

            if (item.product == product) {
                item.quantity--;
                return;
            }
        }
    }

    public boolean hasProducts(Order order) {
              
        Iterator<OrderItem> iterator = iterator();

        for (OrderItem orderItem : order) {

            boolean productIsInStock = false;

            while (iterator.hasNext()) {
                OrderItem stockItem = iterator.next();
                if (stockItem.product == orderItem.product) {

                    if (stockItem.quantity >= orderItem.quantity) {                        
                        productIsInStock = true;
                        break;                
                    }
                }
            }

            if (!productIsInStock) {
                return false;
            }
        }

        return true;
    }

    public void fulfillOrder(Order order) {

        int orderID = order.id;

        Iterator<OrderItem> iterator = iterator();

        for (OrderItem orderItem : order) {

            while (iterator.hasNext()) {
                OrderItem stockItem = iterator.next();
                if (stockItem.product == orderItem.product) {
                    if (stockItem.quantity >= orderItem.quantity) {
                        stockItem.quantity -= orderItem.quantity;
                        break;
                    } else {
                        throw new RuntimeException("warehouse : " + id + " unexpectedly out of stock for order: " + orderID);
                    }
                }
            }
        }

    }
    
   
  

}
