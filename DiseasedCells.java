import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * Challenge Task (Disease)
 * DiseasedCells represents a life form that can catch a disease and spread it to its neighbors.
 * Once infected its behaviours change and it has a new color.
 *
 * @author Ahmet Taramis (K22038914)
 */

public class DiseasedCells extends Cell {
    // Healthy color of cell
    private static final Color HEALTHY_COLOR = Color.GREEN;
    // Infected color of cell
    private static final Color INFECTED_COLOR = Color.RED;
    private boolean infected = false;

    /**
     * Create a new DiseasedCell.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public DiseasedCells(Field field, Location location) {
        super(field, location, HEALTHY_COLOR);
    }
    
    /**
     * This method determines behaviours of the cell.
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        List<Location> adjacentLocations = getField().adjacentLocations(getLocation());
        Random rand = Randomizer.getRandom();
        
        if (getColor() == INFECTED_COLOR) {
            infected = true;
        }
        
        if (isAlive() && !(infected)) {
            if (neighbours.size() < 2 || neighbours.size() > 3) {
                setNextState(false);
                infected = false;
            }
            else {
                setNextState(true);
            }
            // Chance of randomly being infected
            if (rand.nextDouble() <= 0.1) {
                infected = true;
                setColor(INFECTED_COLOR);
            }
        }
        else if (!isAlive()) {
            if (neighbours.size() == 3) {
                setColor(HEALTHY_COLOR);
                setNextState(true);
            }
        }
        // Once infected cell's behaviour changes (element two for the disease)
        else if (infected) {
            // Chance of dying if infected
            if (rand.nextDouble() < 0.5) {
                setNextState(false);
                infected = false;
            }
            // Chance of curing disease
            else if (rand.nextDouble() < 0.1) {
                infected = false;
            }
            // Chance of spreading disease to each neigbouring cells (element one for the disease)
            for (Location loc : adjacentLocations) {
                if (rand.nextDouble() < 0.3) {
                    Cell cell = getField().getObjectAt(loc);
                    if (cell.isAlive()) {
                        cell.setColor(INFECTED_COLOR);
                    }
                }
            }
        }
    }
}
