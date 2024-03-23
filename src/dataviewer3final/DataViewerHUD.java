package dataviewer3final;

import dataviewer3final.keycommands.*;
import edu.du.dudraw.Draw;
import edu.du.dudraw.DrawListener;

import java.io.FileNotFoundException;


public class DataViewerHUD extends DataObserver implements DrawListener {


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




    @Override
    public void keyPressed(int key) {
    	needsUpdate = false;
        needsUpdatePlotData = false;
        //reset update booleans 
        
        StaticDebuggingStatements.trace("key pressed '%c'", (char)key);
        
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

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }

    public void setNeedsUpdatePlotData(boolean needsUpdatePlotData) {
        this.needsUpdatePlotData = needsUpdatePlotData;
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
