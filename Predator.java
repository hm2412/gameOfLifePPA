import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;
import java.util.Iterator;
import java.util.Collections;

/**
 * Challenge Task (Symbiosis)
 * 
 * This cell is part of a parasitic relationship with prey cells.
 * If in contact with a prey cell, predators may eat them.
 * Otherwise, they move into empty adjacent space. If the predator has not moved or
 * eaten, it is at risk of dying.
 * 
 * @author Haleema Mohammed
 * @version 2024.02.28
 */

public class Predator extends Cell {
    
    /**
     * Create a new Predator.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The colour of the cell. 
     */
    public Predator(Field field, Location location, Color col) {
        super(field, location, col);
    }
    
    /**
    * This method determins Predator behaviour.
    * Predator cells examine adjacent locations for possibly prey to kill. If
    * adjacent to a prey cell, it has a 30% chance of eating it. Otherwise, it 
    * moves into adjacent empty space. 
    * If the predator has not eaten or moved, it has a 10% chance of dying.
    */
    public void act() {
        if(!isAlive()) {
            return;
        }
        
        Field field = getField();
        Location location = getLocation();
        List<Location> adjacentLocations = field.adjacentLocations(location);
        Random rand;
        
        for (Location adjacent : adjacentLocations) {
            Cell cell = field.getObjectAt(adjacent);
            if (cell instanceof Prey) {
                rand = Randomizer.getRandom();
                if (rand.nextDouble() <= 0.3) {
                    cell.setNextState(false);
                    setNextState(true);
                    return;
                }
            }
            if (field.isEmpty(adjacent)) {
                setLocation(adjacent);
                setNextState(true);
                return;
            }
            rand = Randomizer.getRandom();
            // if the cell hasn't eaten or moved
            if (rand.nextDouble() <= 0.01) {
                setNextState(false);
                break;
            }
        }             
    }
}
