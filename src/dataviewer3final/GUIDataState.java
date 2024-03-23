package dataviewer3final;

import edu.du.dudraw.Draw;

import java.awt.*;
import java.util.SortedMap;


public class GUIDataState extends GUIState {

    private final static int 		VISUALIZATION_RAW_IDX = 0;
    private final static int		VISUALIZATION_EXTREMA_IDX = 1;
    private final static double 	DATA_WINDOW_BORDER = 50.0;
    private final static double 	EXTREMA_PCT = 0.1;
    private final static String[] 	MONTH_NAMES = { "", // 1-based
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
//    private final static double		TEMPERATURE_MAX_C = 30.0;
//    private final static double		TEMPERATURE_MIN_C = -10.0;
//    private final static double		TEMPERATURE_RANGE = TEMPERATURE_MAX_C - TEMPERATURE_MIN_C;
    
    
    private final static double		TEMPERATURE_MAX_C = 30.0;
    private final static double		TEMPERATURE_MIN_C = -10.0;
    private final static double		TEMPERATURE_RANGE = TEMPERATURE_MAX_C - TEMPERATURE_MIN_C;
    
    private VisualizationStrategy strategy;

    

    public GUIDataState(DataViewer dataViewer){
        super(dataViewer);

    }


    @Override
    public void drawState(Draw window) {
        // Give a buffer around the plot window
        window.setXscale(-DATA_WINDOW_BORDER, window.getCanvasWidth()+DATA_WINDOW_BORDER);
        window.setYscale(-DATA_WINDOW_BORDER, window.getCanvasHeight()+DATA_WINDOW_BORDER);

        // gray background
        window.clear(Color.LIGHT_GRAY);

        // white plot area
        window.setPenColor(Color.WHITE);
        //window.filledRectangle(DataViewerHUD.getWindowWidth()/2.0, DataViewerHUD.getWindowHeight()/2.0, DataViewerHUD.getWindowWidth()/2.0, DataViewerHUD.getWindowHeight()/2.0);
        window.filledRectangle(window.getCanvasWidth()/2.0, window.getCanvasHeight()/2.0, window.getCanvasWidth()/2.0, window.getCanvasHeight()/2.0);
        window.setPenColor(Color.BLACK);

        double nCols = 12; // one for each month
        double nRows = super.dataViewer.getM_selectedEndYear() - super.dataViewer.getM_selectedStartYear() + 1; // for the years

        //debug("nCols = %f, nRows = %f", nCols, nRows);


        double cellWidth = window.getCanvasWidth()/nCols;
        double cellHeight = window.getCanvasHeight()/nRows;
        
        
        //debug("cellWidth = %f, cellHeight = %f", cellWidth, cellHeight);
//        if(strategy instanceof ExtremaStrategy) {
//        	extremaVisualization = true;
//        } else {
//        	extremaVisualization = false;
//        }
//        boolean extremaVisualization = super.dataViewer.getM_selectedVisualization().equals(dataViewer.getVisualizationModes()[VISUALIZATION_EXTREMA_IDX]);
        //info("visualization: %s (extrema == %b)", super.dataViewer.getM_selectedVisualization(), extremaVisualization);

        
        boolean extremaVisualization = super.dataViewer.getM_selectedVisualization().equals(dataViewer.getVisualizationModes()[VISUALIZATION_EXTREMA_IDX]);
        ///ADDED
        
        for(int month = 1; month <= 12; month++) {
            double fullRange = dataViewer.getM_plotMonthlyMaxValue().get(month) - dataViewer.getM_plotMonthlyMinValue().get(month);
            
            double extremaMinBound = dataViewer.getM_plotMonthlyMinValue().get(month) + EXTREMA_PCT * fullRange;
            double extremaMaxBound = dataViewer.getM_plotMonthlyMaxValue().get(month) - EXTREMA_PCT * fullRange;

            
            // draw the line separating the months and the month label
            window.setPenColor(Color.BLACK);
            double lineX = (month-1.0)*cellWidth;
            window.line(lineX, 0.0, lineX, window.getCanvasHeight());
            window.text(lineX+cellWidth/2.0, -DATA_WINDOW_BORDER/2.0, MONTH_NAMES[month]);

            // there should always be a map for the month
            SortedMap<Integer,Double> monthData = dataViewer.getM_plotData().get(month);

            for(int year = super.dataViewer.getM_selectedStartYear(); year <= super.dataViewer.getM_selectedEndYear(); year++) {

                // month data structure might not have every year
                if(monthData.containsKey(year)) {
                    Double value = monthData.get(year);

                    double x = (month-1.0)*cellWidth + 0.5 * cellWidth;
                    double y = (year-super.dataViewer.getM_selectedStartYear())*cellHeight + 0.5 * cellHeight;

                    Color cellColor = null;

                    // get either color or grayscale depending on visualization mode

                    if(extremaVisualization){
                    
                        this.strategy = new RawStrategy(value);
                        cellColor = this.strategy.execute();
                    }
                    else if(!extremaVisualization) {
                        this.strategy = new ExtremaStrategy(value, extremaMinBound, extremaMaxBound);
                        cellColor = this.strategy.execute();

                    }
                    


                    // draw the rectangle for this data point
                    window.setPenColor(cellColor);
                    //trace("month = %d, year = %d -> (%f, %f) with %s", month, year, x, y, cellColor.toString());
                    window.filledRectangle(x, y, cellWidth/2.0, cellHeight/2.0);
                }
            }
        }
    }

    @Override
    public boolean isMainMenu() {
        return false;
    }

//    private Color getDataColor(Double value, boolean doGrayscale) {
//        if(null == value) {
//            return null;
//        }
//        double pct = (value - TEMPERATURE_MIN_C) / TEMPERATURE_RANGE;
//        //trace("converted %f raw value to %f %%", value, pct);
//
//        if (pct > 1.0) {
//            pct = 1.0;
//        }
//        else if (pct < 0.0) {
//            pct = 0.0;
//        }
//        int r, g, b;
//        // Replace the color scheme with my own
//        if (!doGrayscale) {
//            r = (int)(255.0 * pct);
//            g = 0;
//            b = (int)(255.0 * (1.0-pct));
//
//        } else {
//            // Grayscale for the middle extema
//            r = g = b = (int)(255.0 * pct);
//        }
//
//        //trace("converting %f to [%d, %d, %d]", value, r, g, b);
//
//        return new Color(r, g, b);
//    }


}
