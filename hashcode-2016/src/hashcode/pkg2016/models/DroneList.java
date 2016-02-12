/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hashcode.pkg2016.models;

import hashcode.pkg2016.DistanceCalculator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author deanwild
 */
public class DroneList extends ArrayList<Drone> {

    public Drone findbestDrone() {

        Collections.sort(this, new Comparator<Drone>() {

            @Override
            public int compare(Drone drone1, Drone drone2) {
                int len1 = drone1.nextFreeTime;
                int len2 = drone2.nextFreeTime;
                int diff = len1 - len2;
                return diff;
            }
        });

        return get(0);

    }

    public Drone findBest(Order order, Warehouse warehouse) {

        Drone bestDrone = null;

        int bestTurnCost = Integer.MAX_VALUE;

        for (int droneID = 0; droneID < size(); droneID++) {
            Drone drone = get(droneID);

            int distanceToWarehouse = DistanceCalculator.distance(drone.X, drone.Y, warehouse.X, warehouse.Y);
            int distanceToCustomer = DistanceCalculator.distance(drone.X, drone.Y, order.X, order.Y);
            int totalTurnCost = drone.nextFreeTime + distanceToCustomer + distanceToWarehouse + order.turnOverhead();

            if (totalTurnCost < bestTurnCost) {

             //   if (order.remainingWeight <= Grid.MAX_PAYLOAD) {
                    // just check that this move won't tip the drone over the edge
                    if (totalTurnCost <= Grid.MAX_TIME) {
                        bestTurnCost = totalTurnCost;
                        bestDrone = drone;
                    }
             //   }
            }          
        }

        return bestDrone;

    }
}
