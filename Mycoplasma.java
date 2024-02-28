import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Simplest form of life.
 * Fun Fact: Mycoplasma are one of the simplest forms of life.  A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06
 */

public class Mycoplasma extends Cell {

    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The colour of the cell
     */
    public Mycoplasma(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
    * This is how the Mycoplasma decides if it's alive or not
    * Base Task #1
    * If the cell has fewer than 2 living neighbours it will die (underpopulation).
    * If the cell has two or three live neighbours it will live on.
    * If the cell has more than three live neighbours it will die (overpopulation).
    * If a dead cell has exactly three live neighbours it will come alive (reproduction).
    * 
    * @authors Haleema Mohammed, Ahmet Taramis
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
    
        if (isAlive()) {
            if (neighbours.size() < 2 || neighbours.size() > 3) {
                setNextState(false);
            }
            else {
                setNextState(true);
            }
        }
        else {
            if (neighbours.size() == 3) {
                setNextState(true);
            }
        }
    }
}
