import javafx.scene.paint.Color; 
import java.util.*;

/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * This updated simulator contains 6 life forms.
 * Mycoplasma, ChromaCell, Evolver, DiseasedCell, Predator and Prey
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @author Haleema Mohammed, Ahmet Taramis
 * @version 2024.02.03
 */

public class Simulator {

    private static final double MYCOPLASMA_ALIVE_PROB = 0.25;
    private static final double CHROMACELL_ALIVE_PROB = 0.5;
    private static final double EVOLVER_ALIVE_PROB = 0.025;
    private static final double DISEASEDCELLS_ALIVE_PROB = 0.4;
    private static final double PREDATOR_ALIVE_PROB = 0.015;
    private static final double PREY_ALIVE_PROB = 0.02;
    private List<Cell> cells;
    private Field field;
    private int generation;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(SimulatorView.GRID_HEIGHT, SimulatorView.GRID_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        cells = new ArrayList<>();
        field = new Field(depth, width);
        reset();
    }

    /**
     * Run the simulation from its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        generation++;
        for (Iterator<Cell> it = cells.iterator(); it.hasNext(); ) {
            Cell cell = it.next();
            cell.act();
        }
        
        for (Cell cell : cells) {
          cell.updateState();
        }

    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        cells.clear();
        field.clear();
    }
    
    /**
     * Randomly populate the field live/dead life forms for Mycoplasma
     */
    public void populateMycoplasma() {
        Random rand = Randomizer.getRandom();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                Mycoplasma myco = new Mycoplasma(field, location, Color.ORANGE);
                if (rand.nextDouble() <= MYCOPLASMA_ALIVE_PROB) {
                    cells.add(myco);
                }
                else {
                    myco.setDead();
                    cells.add(myco);
                }
            }
        }
    }
    
    /**
     * Randomly populate the field live/dead life forms for ChromaCell
     * 
     * @author Ahmet Taramis
     */
    public void populateChromaCell() {
        Random rand = Randomizer.getRandom();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                ChromaCell chroma = new ChromaCell(field, location);
                if (rand.nextDouble() <= CHROMACELL_ALIVE_PROB) {
                    cells.add(chroma);
                }
                else {
                    chroma.setDead();
                    cells.add(chroma);
                }
            }
        }
    }
    
    /**
     * Randomly populate the field live/dead life forms for Evolvers
     * 
     * @author Haleema Mohammed
     */
    public void populateEvolver() {
        Random rand = Randomizer.getRandom();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                Evolver evolver = new Evolver(field, location, Color.BLUE);
                if (rand.nextDouble() <= EVOLVER_ALIVE_PROB) {
                    cells.add(evolver);
                }
                else {
                    evolver.setDead();
                    cells.add(evolver);
                }
            }
        }
    }
    
    /**
     * Randomly populate the field live/dead life forms for DiseasedCells
     * 
     * @author Ahmet Taramis
     */
    public void populateDiseasedCells() {
        Random rand = Randomizer.getRandom();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                DiseasedCells disease = new DiseasedCells(field, location);
                if (rand.nextDouble() <= DISEASEDCELLS_ALIVE_PROB) {
                    cells.add(disease);
                }
                else {
                    disease.setDead();
                    cells.add(disease);
                }
            }
        }
    }
    
    /**
     * Randomly populate the field live/dead life forms for Predator
     * Used in tangent with populatePrey()
     * 
     * @author Haleema Mohammed
     */
    public void populatePredator() {
        Random rand = Randomizer.getRandom();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                if (rand.nextDouble() <= PREDATOR_ALIVE_PROB) {
                    Predator predator = new Predator(field, location, Color.RED);
                    cells.add(predator);
                }
            }
        }
    }
    
    /**
     * Randomly populate the field live/dead life forms for Prey
     * Used in tangent with populatePredator()
     * 
     * @author Haleema Mohammed
     */
    public void populatePrey() {
        Random rand = Randomizer.getRandom();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                if (rand.nextDouble() <= PREY_ALIVE_PROB) {
                    Prey prey = new Prey(field, location, Color.GREEN);
                    cells.add(prey);
                }
            }
        }
    }
    
    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    public void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
    
    public Field getField() {
        return field;
    }

    public int getGeneration() {
        return generation;
    }
}
