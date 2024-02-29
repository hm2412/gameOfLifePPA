import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Base Task 2
 * ChromaCell represents a life form that changes its color based on how long it has survived.
 * Each generation it lives it cycles the colour wheel and starts with a specific color when coming to life.
 *
 * @author Ahmet Taramis (K22038914)
 */

public class ChromaCell extends Cell {
    
    // Starting hue value for the color cycle
    private float hue;

    /**
     * Create a new ChromaCell.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public ChromaCell(Field field, Location location) {
        super(field, location, Color.WHITE);
        hue = 0;
        setColor(Color.hsb(hue, 1, 1));
    }

    /**
     * This method determines the next state of the ChromaCell based on its neighbors.
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());

        if (isAlive()) {
            if (neighbours.size() < 2 || neighbours.size() > 3) {
                setNextState(false);
            }
            else {
                // Increment hue to cycle through rainbow colors
                hue += 20; // Adjust this value to control the speed of the color cycle
                if (hue >= 360) {
                    hue = 0; // Reset hue after completing a full cycle
                }
                setColor(Color.hsb(hue, 1, 1));
                setNextState(true);
            }
        }
        else {
            if (neighbours.size() == 3) {
                setColor(Color.hsb(0, 1, 1));
                setNextState(true);
            }
        }
    }
}
