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

    public void removeItem(Product product) {
        
        Iterator<OrderItem> iterator = iterator();
        
        while(iterator.hasNext()){
            
            OrderItem item = iterator.next();
            
            if(item.product == product){
                iterator.remove();
                return;
            }
        }
    }

}
