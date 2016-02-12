/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016;

/**
 *
 * @author deanwild
 */
public class DistanceCalculator {
    
     public static int distance(int x1, int y1, int x2, int y2) {
        int dX = Math.abs(x2 - x1);
        int dY = Math.abs(y2 - y1);
        double hyp = Math.sqrt((dX * dX) + (dY * dY));
        return (int) Math.ceil(hyp);
    }
     
}
