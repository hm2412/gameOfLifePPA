import javafx.scene.paint.Color;
import java.util.List;
import java.util.Random;

/**
 * This cell evolves (i.e. exhibits different behaviours) over time. The longer a cell has
 * lived, the further it progresses. 
 * Upon reaching a certain generation, it changes colour based on a randomly generated value.
 * 
 * This cell covers both parts of base task 2, as well as challenge task 1 (non-determinism).
 * 
 * @author Haleema Mohammed
 * @version 2024.02.28
 */

public class Evolver extends Cell {
    private int currentGeneration;
    
    /**
     * Create a new Evolver.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param col The colour of the cell. 
     */
    public Evolver(Field field, Location location, Color col) {
        super(field, location, col);
        currentGeneration = 1; // Start from generation 1
    }

    /**
     * This is called when a cell has lived another generation.
     */
    public void evolve() {
        currentGeneration++; 
    }
    
    /**
     * Behaviour is implemented based on the current generation
     */
    public void act() {
        switch(currentGeneration) {
            case 1:
                actForGeneration1();
                break;
            case 2:
                actForGeneration2();
                break;
            case 3:
                actForGeneration3();
                break;
            default:
                actForFutureGenerations();
                break;
        }
    }

    /**
     * This is how the Evolver acts during its first generation.
     * If the cell has two or more live neighbours, it will survive and evolve to the 
     * next generation.
     * Otherwise, the cell dies.
     */
    private void actForGeneration1() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        if (neighbours.size() >= 2) {
            setNextState(true); 
            evolve(); 
        } else {
            setNextState(false); 
        }
    }

    /**
     * This is how the Evolver acts during its second generation.
     * If the cell has less than two live neighbours, it will move to an adjacent empty 
     * location and evolve to the next generation.
     * Otherwise, the cell dies.
     */
    private void actForGeneration2() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        Field field = getField();
        Location location = getLocation();
        
        if (neighbours.size() < 2) {
            for (Location adjacent : field.adjacentLocations(location)) {
                if (field.isEmpty(adjacent)) {
                    setLocation(adjacent);
                    setNextState(true);
                    evolve();
                    return;
                }
            }
        }
        setNextState(false); // Die if it can't find a suitable location to move
    }

    /**
     * This is how the Evolver acts during its third generation.
     * If the cell has fewer than two or greater than three live neighbours, 
     * it will die. Else, it will evolve to the next generation.
     * Any dead cell will come alive if it has exactly three neighbours.
     */
    private void actForGeneration3() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        if (isAlive()) {
            if (neighbours.size() < 2 || neighbours.size() > 3){
                setNextState(false);
            } else {
                setNextState(true);
                evolve();
            }
        } else {
            if (neighbours.size() == 3){
                setNextState(true);
            }
        }
        
    }

    /**
     * This is how the Evolver acts from its fourth generation onwards.
     * The cell will change to a randomly generated colour.
     * 
     * Non determinism: Given currentGeneration > 3, set to [colour] based on a 
     * random probability. 
     */
    private void actForFutureGenerations() {
        Random rand = Randomizer.getRandom();
        setColor(new Color(rand.nextDouble(), rand.nextDouble(), rand.nextDouble(), 1)); 
        setNextState(isAlive()); // Maintain current state
    }
}

