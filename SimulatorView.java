import javafx.application.Application;
import javafx.application.Platform;
import java.util.*;
import javafx.scene.control.*;
import javafx.scene.layout.*; 
import javafx.scene.Group; 
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color; 

/**
 * A graphical view of the simulation grid. The view displays a rectangle for
 * each location. Colors for each type of life form can be defined using the
 * setColor method.
 *
 * This also includes a sidebar, which contains buttons to populate the simulation with 
 * different lifeforms. There is also a simulate button, where the user can input their
 * desired number of generations to simulate.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @author Haleema Mohammed
 * @version 2024.02.03
 */

public class SimulatorView extends Application {

    public static final int GRID_WIDTH = 100; //100
    public static final int GRID_HEIGHT = 80; //80   
    public static final int WIN_WIDTH = 650;
    public static final int WIN_HEIGHT = 650;  
    
    private static final Color EMPTY_COLOR = Color.WHITE;

    private final String GENERATION_PREFIX = "Generation: ";
    private final String POPULATION_PREFIX = "Population: ";

    private Label genLabel, population, infoLabel;

    private FieldCanvas fieldCanvas;
    private FieldStats stats;
    private Simulator simulator;
    private Button simulateButton;

    /**
     * Create buttons that will be displayed on the sidebar.
     * These are used to populate the field, as well as start the simulation.
     */
    private void populateButtons(VBox sidebar) {
        Button mycoplasmaButton = new Button("Mycoplasma");
        mycoplasmaButton.setOnAction(e -> {
            simulator.reset();
            simulator.populateMycoplasma();
            enableSimulateButton();
            updateCanvas(simulator.getGeneration(), simulator.getField());
        });
        
        Button chromaCellButton = new Button("Chroma Cell");
        chromaCellButton.setOnAction(e -> {
            simulator.reset();
            simulator.populateChromaCell();
            enableSimulateButton();
            updateCanvas(simulator.getGeneration(), simulator.getField());
        });

        Button symbiosisButton = new Button("Predators and Prey");
        symbiosisButton.setOnAction(e -> {
            simulator.reset();
            simulator.populatePredator();
            simulator.populatePrey();
            enableSimulateButton();
            updateCanvas(simulator.getGeneration(), simulator.getField());
        });

        Button evolverButton = new Button("Evolvers");
        evolverButton.setOnAction(e -> {
            simulator.reset();
            simulator.populateEvolver();
            enableSimulateButton();
            updateCanvas(simulator.getGeneration(), simulator.getField());
        });
        
        Button diseasedCellsButton = new Button("Diseased Cells");
        diseasedCellsButton.setOnAction(e -> {
            simulator.reset();
            simulator.populateDiseasedCells();
            enableSimulateButton();
            updateCanvas(simulator.getGeneration(), simulator.getField());
        });
        
        Button rippleCellsButton = new Button("Ripples");
        rippleCellsButton.setOnAction(e -> {
            simulator.reset();
            simulator.populateRipples();
            enableSimulateButton();
            updateCanvas(simulator.getGeneration(), simulator.getField());
        });
        
        VBox buttonBox = new VBox(); // VBox to contain the buttons
        buttonBox.getChildren().addAll(mycoplasmaButton, chromaCellButton, symbiosisButton, evolverButton, diseasedCellsButton, rippleCellsButton);
        
        simulateButton = new Button("Simulate");
        simulateButton.setDisable(true); // Cannot simulate while the field is empty
    
        simulateButton.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Simulate");
            dialog.setHeaderText("Enter number of generations:");
            dialog.setContentText("Generations:");
            
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(input -> {
                int numGenerations = Integer.parseInt(input);
                simulate(numGenerations); // User can determine the number of generations
            });
        });

        sidebar.getChildren().addAll(buttonBox,simulateButton);
        sidebar.setSpacing(20); // Separates the simulator button
    }
    
    private void enableSimulateButton() {
        simulateButton.setDisable(false);
    }
    
    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    @Override
    public void start(Stage stage) {
                
        stats = new FieldStats();
        fieldCanvas = new FieldCanvas(WIN_WIDTH - 50, WIN_HEIGHT - 50);
        fieldCanvas.setScale(GRID_HEIGHT, GRID_WIDTH); 
        simulator = new Simulator();

        Group root = new Group();
        
        genLabel = new Label(GENERATION_PREFIX);
        infoLabel = new Label("  ");
        population = new Label(POPULATION_PREFIX);
        
        VBox sidebar = new VBox();
        populateButtons(sidebar);

        BorderPane bPane = new BorderPane(); 
        HBox infoPane = new HBox();
        HBox popPane = new HBox();

        infoPane.setSpacing(10);
        infoPane.getChildren().addAll(genLabel, infoLabel);       
        popPane.getChildren().addAll(population); 
        
        bPane.setRight(sidebar);
        bPane.setTop(infoPane);
        bPane.setCenter(fieldCanvas);
        bPane.setBottom(population);
        
        root.getChildren().add(bPane);
        Scene scene = new Scene(root); 
        
        stage.setScene(scene);          
        stage.setTitle("Life Simulation");
        stage.setMinWidth(WIN_WIDTH); // Set minimum width of the window
        stage.setMinHeight(WIN_HEIGHT); // Set minimum height of the window
        stage.setWidth(WIN_WIDTH + 75); // Adjust width to include sidebar
        updateCanvas(simulator.getGeneration(), simulator.getField());
        
        stage.show();     
    }

    /**
     * Display a short information label at the top of the window.
     */
    public void setInfoText(String text) {
        infoLabel.setText(text);
    }

    /**
     * Show the current status of the field.
     * @param generation The current generation.
     * @param field The field whose status is to be displayed.
     */
    public void updateCanvas(int generation, Field field) {
        genLabel.setText(GENERATION_PREFIX + generation);
        stats.reset();
        
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Cell cell = field.getObjectAt(row, col);
        
                if (cell != null && cell.isAlive()) {
                    stats.incrementCount(cell.getClass());
                    fieldCanvas.drawMark(col, row, cell.getColor());
                }
                else {
                    fieldCanvas.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        
        stats.countFinished();
        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field) {
        return stats.isViable(field);
    }

    /**
     * Run the simulation from its current state for the given number of
     * generations.  Stop before the given number of generations if the
     * simulation ceases to be viable.
     * @param numGenerations The number of generations to run for.
     */
    public void simulate(int numGenerations) {
        new Thread(() -> {
           
            for (int gen = 1; gen <= numGenerations; gen++) {
                simulator.simOneGeneration();    
                simulator.delay(250);
                Platform.runLater(() -> {
                    updateCanvas(simulator.getGeneration(), simulator.getField());
                });
            }
            
        }).start();
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        simulator.reset();
        updateCanvas(simulator.getGeneration(), simulator.getField());
    }
    
    public static void main(String args[]){           
        launch(args);      
    } 
}
