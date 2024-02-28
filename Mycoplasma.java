import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Simplest form of life.
 * Fun Fact: Mycoplasma are one of the simplest forms of life.  A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 * 
 * This cell covers base task 1.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @author Haleema Mohammed
 * @version 2024.02.28
 */

public class Mycoplasma extends Cell {

    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The colour of the cell. 
     */
    public Mycoplasma(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
    * This is how the Mycoplasma decides if it's alive or not.
    * If the cell has fewer than two or greater than three live neighbours, 
    * it will die.
    * Any dead cell will come alive if it has exactly three neighbours.
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
    
        if (isAlive()) {
            if (neighbours.size() < 2 || neighbours.size() > 3){
                setNextState(false);
            } else {
                setNextState(true);
            }
        } else {
            if (neighbours.size() == 3){
                setNextState(true);
            }
        }
    }
}
