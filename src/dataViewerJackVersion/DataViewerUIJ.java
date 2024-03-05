package dataViewerJackVersion;

import edu.du.dudraw.Draw;
import edu.du.dudraw.DrawListener;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.SortedMap;

public class DataViewerUIJ implements DrawListener {
    private final static int 		GUI_MODE_MAIN_MENU = 0;
    private final static int 		GUI_MODE_DATA = 1;
    private final static double		MENU_STARTING_X = 40.0;
    private final static double 	MENU_STARTING_Y = 90.0;
    private final static double 	MENU_ITEM_SPACING = 5.0;
    private final static int 		WINDOW_HEIGHT = 720;
    private final static String 	WINDOW_TITLE = "DataViewer Application";
    private final static int 		WINDOW_WIDTH = 1320; // should be a multiple of 12
    private Draw m_window;

    public DataViewerUIJ(){

        m_window = new Draw(WINDOW_TITLE);
        m_window.setCanvasSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        m_window.enableDoubleBuffering(); // Too slow otherwise -- need to use .show() later

        // Add the mouse/key listeners
        m_window.addListener(this);

        update();
    }

    @Override
    public void update() {
        if(m_guiMode == GUI_MODE_MAIN_MENU) {
            drawMainMenu();
        }
        else if(m_guiMode == GUI_MODE_DATA) {
            drawData();
        }
        else {
            throw new IllegalStateException(String.format("Unexpected drawMode=%d", m_guiMode));
        }
        // for double-buffering
        m_window.show();
    }

    private void drawMainMenu() {
        m_window.clear(Color.WHITE);

        String[] menuItems = {
                "Type the menu number to select that option:",
                "",
                String.format("C     Set country: [%s]", m_selectedCountry),
                String.format("T     Set state: [%s]", m_selectedState),
                String.format("S     Set start year [%d]", m_selectedStartYear),
                String.format("E     Set end year [%d]", m_selectedEndYear),
                String.format("V     Set visualization [%s]", m_selectedVisualization),
                String.format("P     Plot data"),
                String.format("Q     Quit"),
        };

        // enable drawing by "percentage" with the menu drawing
        m_window.setXscale(0, 100);
        m_window.setYscale(0, 100);

        // draw the menu
        m_window.setPenColor(Color.BLACK);

        drawMenuItems(menuItems);
    }

    private void drawMenuItems(String[] menuItems) {
        double yCoord = MENU_STARTING_Y;

        for(int i=0; i<menuItems.length; i++) {
            m_window.textLeft(MENU_STARTING_X, yCoord, menuItems[i]);
            yCoord -= MENU_ITEM_SPACING;
        }
    }

    private void drawData() {
        // Give a buffer around the plot window
        m_window.setXscale(-DATA_WINDOW_BORDER, WINDOW_WIDTH+DATA_WINDOW_BORDER);
        m_window.setYscale(-DATA_WINDOW_BORDER, WINDOW_HEIGHT+DATA_WINDOW_BORDER);

        // gray background
        m_window.clear(Color.LIGHT_GRAY);

        // white plot area
        m_window.setPenColor(Color.WHITE);
        m_window.filledRectangle(WINDOW_WIDTH/2.0, WINDOW_HEIGHT/2.0, WINDOW_WIDTH/2.0, WINDOW_HEIGHT/2.0);

        m_window.setPenColor(Color.BLACK);

        double nCols = 12; // one for each month
        double nRows = m_selectedEndYear - m_selectedStartYear + 1; // for the years

        debug("nCols = %f, nRows = %f", nCols, nRows);

        double cellWidth = WINDOW_WIDTH / nCols;
        double cellHeight = WINDOW_HEIGHT / nRows;

        debug("cellWidth = %f, cellHeight = %f", cellWidth, cellHeight);

        boolean extremaVisualization = m_selectedVisualization.equals(VISUALIZATION_MODES[VISUALIZATION_EXTREMA_IDX]);
        info("visualization: %s (extrema == %b)", m_selectedVisualization, extremaVisualization);

        for(int month = 1; month <= 12; month++) {
            double fullRange = m_plotMonthlyMaxValue.get(month) - m_plotMonthlyMinValue.get(month);
            double extremaMinBound = m_plotMonthlyMinValue.get(month) + EXTREMA_PCT * fullRange;
            double extremaMaxBound = m_plotMonthlyMaxValue.get(month) - EXTREMA_PCT * fullRange;


            // draw the line separating the months and the month label
            m_window.setPenColor(Color.BLACK);
            double lineX = (month-1.0)*cellWidth;
            m_window.line(lineX, 0.0, lineX, WINDOW_HEIGHT);
            m_window.text(lineX+cellWidth/2.0, -DATA_WINDOW_BORDER/2.0, MONTH_NAMES[month]);

            // there should always be a map for the month
            SortedMap<Integer,Double> monthData = m_plotData.get(month);

            for(int year = m_selectedStartYear; year <= m_selectedEndYear; year++) {

                // month data structure might not have every year
                if(monthData.containsKey(year)) {
                    Double value = monthData.get(year);

                    double x = (month-1.0)*cellWidth + 0.5 * cellWidth;
                    double y = (year-m_selectedStartYear)*cellHeight + 0.5 * cellHeight;

                    Color cellColor = null;

                    // get either color or grayscale depending on visualization mode
                    if(extremaVisualization && value > extremaMinBound && value < extremaMaxBound) {
                        cellColor = getDataColor(value, true);
                    }
                    else if(extremaVisualization) {
                        // doing extrema visualization, show "high" values in red "low" values in blue.
                        if(value >= extremaMaxBound) {
                            cellColor = Color.RED;
                        }
                        else {
                            cellColor = Color.BLUE;
                        }
                    }
                    else {
                        cellColor = getDataColor(value, false);
                    }

                    // draw the rectangle for this data point
                    m_window.setPenColor(cellColor);
                    trace("month = %d, year = %d -> (%f, %f) with %s", month, year, x, y, cellColor.toString());
                    m_window.filledRectangle(x, y, cellWidth/2.0, cellHeight/2.0);
                }
            }
        }

        // draw the labels for the y-axis
        m_window.setPenColor(Color.BLACK);

        double labelYearSpacing = (m_selectedEndYear - m_selectedStartYear) / 5.0;
        double labelYSpacing = WINDOW_HEIGHT/5.0;
        // spaced out by 5, but need both the first and last label, so iterate 6
        for(int i=0; i<6; i++) {
            int year = (int)Math.round(i * labelYearSpacing + m_selectedStartYear);
            String text = String.format("%4d", year);

            m_window.textRight(0.0, i*labelYSpacing, text);
            m_window.textLeft(WINDOW_WIDTH, i*labelYSpacing, text);
        }

        // draw rectangle around the whole data plot window
        m_window.rectangle(WINDOW_WIDTH/2.0, WINDOW_HEIGHT/2.0, WINDOW_WIDTH/2.0, WINDOW_HEIGHT/2.0);

        // put in the title
        String title = String.format("%s, %s from %d to %d. Press 'M' for Main Menu.  Press 'Q' to Quit.",
                m_selectedState, m_selectedCountry, m_selectedStartYear, m_selectedEndYear);
        m_window.text(WINDOW_WIDTH/2.0, WINDOW_HEIGHT+DATA_WINDOW_BORDER/2.0, title);
    }








    @Override
    public void keyReleased(int key) {}

    @Override
    public void keyTyped(char key) {}

    @Override
    public void mouseClicked(double x, double y) {}

    @Override
    public void mouseDragged(double x, double y) {}

    @Override
    public void mousePressed(double x, double y) {}

    @Override
    public void mouseReleased(double x, double y) {}

    @Override public void keyPressed(int key) {
        boolean needsUpdate = false;
        boolean needsUpdatePlotData = false;
        trace("key pressed '%c'", (char)key);
        // regardless of draw mode, 'Q' or 'q' means quit:
        if(key == 'Q') {
            System.out.println("Bye");
            System.exit(0);
        }
        else if(m_guiMode == GUI_MODE_MAIN_MENU) {
            if(key == 'P') {
                // plot the data
                m_guiMode = GUI_MODE_DATA;
                if(m_plotData == null) {
                    // first time going to render data need to generate the plot data
                    needsUpdatePlotData = true;
                }
                needsUpdate = true;
            }
            else if(key == 'C') {
                // set the Country
                Object selectedValue = JOptionPane.showInputDialog(null,
                        "Choose a Country", "Input",
                        JOptionPane.INFORMATION_MESSAGE, null,
                        m_dataCountries.toArray(), m_selectedCountry);

                if(selectedValue != null) {
                    info("User selected: '%s'", selectedValue);
                    if(!selectedValue.equals(m_selectedCountry)) {
                        // change in data
                        m_selectedCountry = (String)selectedValue;
                        try {
                            loadData();
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
                        m_dataStates.toArray(), m_selectedState);

                if(selectedValue != null) {
                    info("User selected: '%s'", selectedValue);
                    if(!selectedValue.equals(m_selectedState)) {
                        // change in data
                        m_selectedState = (String)selectedValue;
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
                        m_dataYears.toArray(), m_selectedStartYear);

                if(selectedValue != null) {
                    info("User seleted: '%s'", selectedValue);
                    Integer year = (Integer)selectedValue;
                    if(year.compareTo(m_selectedEndYear) > 0) {
                        error("new start year (%d) must not be after end year (%d)", year, m_selectedEndYear);
                    }
                    else {
                        if(!m_selectedStartYear.equals(year)) {
                            m_selectedStartYear = year;
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
                        m_dataYears.toArray(), m_selectedEndYear);

                if(selectedValue != null) {
                    info("User seleted: '%s'", selectedValue);
                    Integer year = (Integer)selectedValue;
                    if(year.compareTo(m_selectedStartYear) < 0) {
                        error("new end year (%d) must be not be before start year (%d)", year, m_selectedStartYear);
                    }
                    else {
                        if(!m_selectedEndYear.equals(year)) {
                            m_selectedEndYear = year;
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
                        VISUALIZATION_MODES, m_selectedVisualization);

                if(selectedValue != null) {
                    info("User seleted: '%s'", selectedValue);
                    String visualization = (String)selectedValue;
                    if(!m_selectedVisualization.equals(visualization)) {
                        m_selectedVisualization = visualization;
                        needsUpdate = true;
                    }
                }
            }

        }
        else if (m_guiMode == GUI_MODE_DATA) {
            if(key == 'M') {
                m_guiMode = GUI_MODE_MAIN_MENU;
                needsUpdate = true;
            }
        }
        else {
            throw new IllegalStateException(String.format("unexpected mode: %d", m_guiMode));
        }
        if(needsUpdatePlotData) {
            // something changed with the data that needs to be plotted
            updatePlotData();
        }
        if(needsUpdate) {
            update();
        }
    }
}
