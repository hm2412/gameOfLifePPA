import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * Challenge Task
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
        else if (infected) {
            if (rand.nextDouble() < 0.5) {
                setNextState(false);
            }
            if (rand.nextDouble() < 0.1) {
                infected = false;
            }
            for (Location loc : adjacentLocations) {
                if (rand.nextDouble() < 0.3) {
                    Cell cell = getField().getObjectAt(loc);
                    cell.setColor(INFECTED_COLOR);
                }
            }
        }
    }
}
