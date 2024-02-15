import javafx.scene.paint.Color; 
import java.util.List;

/**
 * ChromaCell represents a life form that changes color based on its surroundings.
 * It brightens when stable, darkens when underpopulated or overcrowded,
 * and starts with a specific color when coming to life.
 *
 * @author Ahmet Taramis (K22038914)
 */

public class ChromaCell extends Cell {
    // Initial color when a dead ChromaCell comes to life
    private static final Color BIRTH_COLOR = Color.AQUAMARINE;

    /**
     * Create a new ChromaCell.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public ChromaCell(Field field, Location location) {
        super(field, location, BIRTH_COLOR);
    }

    /**
     * This method determines the next state and color of the ChromaCell based on its neighbors.
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        if (isAlive()) {
            if (neighbours.size() < 2 || neighbours.size() > 3) {
                setNextState(false);
            }
            else {
                setColor(this.getColor().darker());
                setNextState(true);
            }
        }
        else {
            if (neighbours.size() == 3) {
                setColor(BIRTH_COLOR);
                setNextState(true);
            }
        }
    }
}
