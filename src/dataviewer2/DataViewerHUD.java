package dataviewer2;

import edu.du.dudraw.Draw;
import edu.du.dudraw.DrawListener;

import javax.swing.JOptionPane;
import java.io.FileNotFoundException;


public class DataViewerHUD implements DrawListener {

    private final static boolean	DO_DEBUG = true;
    private final static boolean	DO_TRACE = false;
    private final static int 		WINDOW_HEIGHT = 720;
    private final static String 	WINDOW_TITLE = "DataViewer Application";
    private final static int 		WINDOW_WIDTH = 1320; // should be a multiple of 12

    private Draw window;
    private GUIState GUIState;
    private DataViewer dataViewer;



    public DataViewerHUD(DataViewer dataViewer){

        this.dataViewer = dataViewer;
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
        this.GUIState.drawState(this.window);
        this.window.show();

    }




    //HELPER COMMANDS
    /**
     * For debugging.  Use 'trace' for older debugging messages that you don't want to see.
     *
     * Output is shown based on the M_DO_TRACE constant.
     */
    private void trace(String format, Object...args) {
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
    private void info(String format, Object... args) {
        System.out.print("INFO: ");
        System.out.println(String.format(format, args));
    }

    /**
     * For error output.
     * @param format
     * @param args
     */
    private void error(String format, Object... args) {
        System.out.print("ERROR: ");
        System.out.println(String.format(format, args));
    }

    /**
     * For debugging output.  Output is controlled by the DO_DEBUG constant.
     * @param format
     * @param args
     */
    private void debug(String format, Object... args) {
        if(DO_DEBUG) {
            System.out.print("DEBUG: ");
            System.out.println(String.format(format, args));
        }
    }




    @Override
    public void keyPressed(int key) {
        boolean needsUpdate = false;
        boolean needsUpdatePlotData = false;
        trace("key pressed '%c'", (char)key);
        // regardless of draw mode, 'Q' or 'q' means quit:
        if(key == 'Q') {
            System.out.println("Bye");
            System.exit(0);
        }
        else if(GUIState.isMainMenu()) {
            if(key == 'P') {
                // plot the data
                //m_guiMode = GUI_MODE_DATA; OLD
            	try {
                GUIState = new GUIDataState(dataViewer);
                if(dataViewer.getM_plotData() == null) {
                    // first time going to render data need to generate the plot data
                    needsUpdatePlotData = true;
                }
                needsUpdate = true;
            	} catch (Exception e){
            	System.out.println("Error: Some values null");
            	}
            }
            else if(key == 'C') {
                // set the Country
                Object selectedValue = JOptionPane.showInputDialog(null,
                        "Choose a Country", "Input",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        dataViewer.getM_dataCountries().toArray(), dataViewer.getM_selectedCountry());

                if(selectedValue != null) {
                    info("User selected: '%s'", selectedValue);
                    if(!selectedValue.equals(dataViewer.getM_selectedCountry())) {
                        // change in data
                        //m_selectedCountry = (String)selectedValue;
                        dataViewer.setM_selectedCountry((String) selectedValue);
                        try {
                            dataViewer.loadData();
                        }
                        catch(FileNotFoundException e) {
                            // convert to a runtime exception since
                            // we can't add throws to this method
                            throw new RuntimeException(e);
                        }
                        needsUpdate = true;
                        needsUpdatePlotData = true;
                    }
                }
            }

            else if(key == 'T') {
                // set the state
                Object selectedValue = JOptionPane.showInputDialog(null,
                        "Choose a State", "Input",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        dataViewer.getM_dataStates().toArray(), dataViewer.getM_selectedState());

                if(selectedValue != null) {
                    info("User selected: '%s'", selectedValue);
                    if(!selectedValue.equals(dataViewer.getM_selectedState())) {
                        // change in data
                        //m_selectedState = (String)selectedValue;
                        dataViewer.setM_selectedState((String)selectedValue);
                        needsUpdate = true;
                        needsUpdatePlotData = true;
                    }
                }
            }
            else if(key == 'S') {
                // set the start year
                Object selectedValue = JOptionPane.showInputDialog(null,
                        "Choose the start year", "Input",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        dataViewer.getM_dataYears().toArray(), dataViewer.getM_selectedStartYear());

                if(selectedValue != null) {
                    info("User seleted: '%s'", selectedValue);
                    Integer year = (Integer)selectedValue;
                    if(year.compareTo(dataViewer.getM_selectedEndYear()) > 0) {
                        error("new start year (%d) must not be after end year (%d)", year, dataViewer.getM_selectedEndYear());
                    }
                    else {
                        if(!dataViewer.getM_selectedStartYear().equals(year)) {
                            //m_selectedStartYear = year;
                            dataViewer.setM_selectedStartYear(year);
                            needsUpdate = true;
                            needsUpdatePlotData = true;
                        }
                    }
                }
            }
            else if(key == 'E') {
                // set the end year
                Object selectedValue = JOptionPane.showInputDialog(null,
                        "Choose the end year", "Input",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        dataViewer.getM_dataYears().toArray(), dataViewer.getM_selectedEndYear());

                if(selectedValue != null) {
                    info("User seleted: '%s'", selectedValue);
                    Integer year = (Integer)selectedValue;
                    if(year.compareTo(dataViewer.getM_selectedStartYear()) < 0) {
                        error("new end year (%d) must be not be before start year (%d)", year, dataViewer.getM_selectedStartYear());
                    }
                    else {
                        if(!dataViewer.getM_selectedEndYear().equals(year)) {
                            //m_selectedEndYear = year;
                            dataViewer.setM_selectedEndYear(year);
                            needsUpdate = true;
                            needsUpdatePlotData = true;
                        }
                    }
                }
            }
            else if(key == 'V') {
                // set the visualization
                Object selectedValue = JOptionPane.showInputDialog(null,
                        "Choose the visualization mode", "Input",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        DataViewer.getVisualizationModes(), dataViewer.getM_selectedVisualization());

                if(selectedValue != null) {
                    info("User seleted: '%s'", selectedValue);
                    String visualization = (String)selectedValue;
                    if(!dataViewer.getM_selectedVisualization().equals(visualization)) {
                        //m_selectedVisualization = visualization;
                        dataViewer.setM_selectedVisualization(visualization);
                        needsUpdate = true;
                    }
                }
            }

        }
        else if (!GUIState.isMainMenu()) {
            if(key == 'M') {
                //m_guiMode = GUI_MODE_MAIN_MENU;
                GUIState = new GUIMainMenuState(dataViewer);
                needsUpdate = true;
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

    public static int getWindowHeight(){
        return WINDOW_HEIGHT;
    }

    public static int getWindowWidth(){
        return WINDOW_WIDTH;
    }


}
