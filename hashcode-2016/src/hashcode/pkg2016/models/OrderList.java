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
public class OrderList extends ArrayList<Order> {
               
    public Order findBest(){
        for (Order thisOrder : this) {
            if (thisOrder.remainingWeight < Grid.MAX_PAYLOAD) {
                return thisOrder;             
            }
        }
        
        return null;
    }
    
}
