/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author deanwild
 */
public class OrderList extends ArrayList<Order>{
              
        
    public Order findBest(){
        
        return get(0);
        
       /* for (Order thisOrder : this) {
            if (thisOrder.remainingWeight <= Grid.MAX_PAYLOAD) {
                return thisOrder;             
            }
        }*/
        
        //return null;
    }
 
    
    public void initialSort(){
     Collections.sort(this, new Comparator<Order>() {

            @Override
            public int compare(Order order1, Order order2) {

                int diff = 0;

                int len1 = order1.items.size();
                int len2 = order2.items.size();

                int weight1 = order1.remainingWeight;
                int weight2 = order2.remainingWeight;                              
                                
                diff = len1 - len2;

                // if they have the same number of items then sort by weight
                if (diff == 0) {
                    diff = weight1 - weight2;
                }

                return diff;
            }
        });
     
    }

    
}
