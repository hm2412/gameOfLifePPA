import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Challenge Task #4
 * 
 * This life form works slightly different from the others such that its only purpose is to provide
 * a visually appealing ripple like rainbow effect when ran for multiple generations. 
 *
 * @author Ahmet Taramis (K22038914)
 */

public class Ripples extends Cell {

    // Starting hue value for the color cycle
    private float hue;
    
    /**
     * Create a new Ripple life form.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The colour of the cell
     */
    public Ripples(Field field, Location location) {
        super(field, location, Color.WHITE);
        hue = 0;
        setColor(Color.hsb(hue, 1, 1));
    }

    /**
    * This is how the Ripple cell's decides its behaviour
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        Field field = getField();
        Location location = getLocation();
        
        if (neighbours.size() >= 2) {
            hue += 20;
            if (hue >= 360) {
                hue = 0; // Reset hue after completing a full cycle
            }
            setColor(Color.hsb(hue, 1, 1));
            setNextState(true);
        } else {
            hue = 0;
            setColor(Color.hsb(hue, 1, 1));
            setNextState(false); 
        }
    }
}
