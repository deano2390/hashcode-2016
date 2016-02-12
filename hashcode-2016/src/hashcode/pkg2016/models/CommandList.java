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
public class CommandList extends ArrayList<String> {

    public void addCommands(ArrayList<String> newCommands) {
        if (newCommands != null) {
            for (String newCommand : newCommands) {
                add(newCommand);
            }
        }
    }

    public String[] results() {
        return toArray(new String[size()]);

    }
}
