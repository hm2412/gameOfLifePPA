import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Iterator;
import java.util.Collections;

/**
 * Challenge Task (Symbiosis)
 * 
 * This cell is part of a parasitic relationship with predator cells. 
 * When in contact with predator cells, it is at risk of being eaten. Otherwise,
 * prey cells can flee to safer areas.
 * 
 * @author Haleema Mohammed
 * @version 2024.02.28
 */

public class Prey extends Cell {
    
    /**
     * Create a new Prey.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The colour of the cell. 
     */
    public Prey(Field field, Location location, Color col) {
        super(field, location, col);
    }
    
    /**
    * This method determines Prey behaviour.
    * Prey cells constantly examine adjacent locations to find safe areas to 
    * move to and survive. If dead, they remain dead.
    */
    public void act() {
        if(!isAlive()){
            return;
        }
        
        Field field = getField();
        Location location = getLocation();
        
        for (Location adjacent : field.adjacentLocations(location)) {
            if (field.isEmpty(adjacent)) {
                setLocation(adjacent);
                setNextState(true);
            }
        }
    }
}
