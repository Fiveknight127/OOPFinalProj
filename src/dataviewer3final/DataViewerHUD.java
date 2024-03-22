package dataviewer3final;

import edu.du.dudraw.Draw;
import edu.du.dudraw.DrawListener;

import javax.swing.*;
import java.io.FileNotFoundException;


public class DataViewerHUD extends DataObserver implements DrawListener {

    private final static boolean	DO_DEBUG = true;
    private final static boolean	DO_TRACE = false;
    private final static int 		WINDOW_HEIGHT = 720;
    private final static String 	WINDOW_TITLE = "DataViewer Application";
    private final static int 		WINDOW_WIDTH = 1320; // should be a multiple of 12

    private Draw window;
    private dataviewer3final.GUIState GUIState;
//    private DataViewer dataViewer;

    
    boolean needsUpdate;
    boolean needsUpdatePlotData;//


    public DataViewerHUD(DataViewer dv) throws FileNotFoundException {
    	
    	super(dv); 

        this.GUIState = new GUIMainMenuState(this.dataViewer);

        // Setup the DuDraw board
        window = new Draw(WINDOW_TITLE);
        window.setCanvasSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.enableDoubleBuffering(); // Too slow otherwise -- need to use .show() later

        // Add the mouse/key listeners
        window.addListener(this);
        update();
    }


    @Override
    public void update() {
        updateObserver(); 

    }
    
	@Override
	public void updateObserver() {
		this.GUIState.drawState(this.window);
        this.window.show();
		
	}




    //HELPER COMMANDS
    /**
     * For debugging.  Use 'trace' for older debugging messages that you don't want to see.
     *
     * Output is shown based on the M_DO_TRACE constant.
     */
    public void trace(String format, Object...args) {
        if(DO_TRACE) {
            System.out.print("TRACE: ");
            System.out.println(String.format(format, args));
        }
    }

    /**
     * For informational output.
     * @param format
     * @param args
     */
    public void info(String format, Object... args) {
        System.out.print("INFO: ");
        System.out.println(String.format(format, args));
    }

    /**
     * For error output.
     * @param format
     * @param args
     */
    public void error(String format, Object... args) {
        System.out.print("ERROR: ");
        System.out.println(String.format(format, args));
    }

    /**
     * For debugging output.  Output is controlled by the DO_DEBUG constant.
     * @param format
     * @param args
     */
    public void debug(String format, Object... args) {
        if(DO_DEBUG) {
            System.out.print("DEBUG: ");
            System.out.println(String.format(format, args));
        }
    }




    @Override
    public void keyPressed(int key) {
    	needsUpdate = false;
        needsUpdatePlotData = false;
        //reset update booleans 
        
        trace("key pressed '%c'", (char)key);
        
        // regardless of draw mode, 'Q' or 'q' means quit:
        if(key == 'Q') {
        	QuitCommand quitCommand = new QuitCommand(dataViewer, this);
        	quitCommand.execute(); //should I add list?, check first functionality
        	
        }
        else if(GUIState.isMainMenu()) {
            if(key == 'P') {
                // plot the data
            	PlotDataCommand plotDataCommand = new PlotDataCommand(dataViewer, this, GUIState);
            	plotDataCommand.execute();
            	


            }
            else if(key == 'C') {
                // set the Country
                SetCountryCommand setCountryCommand = new SetCountryCommand(dataViewer, this);
            	setCountryCommand.execute();
                    }


            else if(key == 'T') {
                // set the state
                SetStateCommand setStateCommand = new SetStateCommand(dataViewer, this);
                setStateCommand.execute();
            }
            else if(key == 'S') {
                // set the start year
                SetStartYearCommand setStartYearCommand = new SetStartYearCommand(dataViewer,this);
                setStartYearCommand.execute();
            }
            else if(key == 'E') {
                // set the end year
            	SetEndYearCommand setEndYearCommand = new SetEndYearCommand(dataViewer,this);
                setEndYearCommand.execute();
            }
            else if(key == 'V') {
                // set the visualization
            	SetVisualizationCommand setVisualizationCommand = new SetVisualizationCommand(dataViewer,this);
                setVisualizationCommand.execute();
            }

        }
        else if (!GUIState.isMainMenu()) {
            if(key == 'M') {
            	MainMenuCommand mainMenuCommand = new MainMenuCommand(dataViewer,this, GUIState);
            	mainMenuCommand.execute();
            }
        }
        else {
            throw new IllegalStateException(String.format("unexpected mode: %d", GUIState));
        }
        if(needsUpdatePlotData) {
            // something changed with the data that needs to be plotted
            dataViewer.updatePlotData();
        }
        if(needsUpdate) {
            update();
        }
  
    }


    // setter for changing GUIState
    public void setGUIState(GUIState gs) {
    	GUIState = gs;
    }






    //TODO LEAVE UNSORTED
    @Override
    public void mousePressed(double v, double v1) {

    }

    @Override
    public void keyTyped(char c) {

    }

    @Override
    public void mouseDragged(double v, double v1) {

    }

    @Override
    public void mouseReleased(double v, double v1) {

    }

    @Override
    public void mouseClicked(double v, double v1) {

    }

    @Override
    public void keyReleased(int i) {

    }


}
