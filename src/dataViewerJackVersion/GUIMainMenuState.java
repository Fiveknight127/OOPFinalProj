package dataViewerJackVersion;

import edu.du.dudraw.Draw;

import java.awt.*;

public class GUIMainMenuState extends GUIState {

    //MENU GUI CONSTANT VARIABLES
    private final static double		MENU_STARTING_X = 40.0;
    private final static double 	MENU_STARTING_Y = 90.0;
    private final static double 	MENU_ITEM_SPACING = 5.0;



    public GUIMainMenuState(DataViewer dataViewer){
        super(dataViewer);

    }

    @Override
    public void drawState(Draw window) {
        window.clear(Color.WHITE);

        String[] menuItems = {
                "Type the menu number to select that option:",
                "",
                String.format("C     Set country: [%s]", super.dataViewer.getM_selectedCountry()),
                String.format("T     Set state: [%s]", super.dataViewer.getM_selectedState()),
                String.format("S     Set start year [%d]", super.dataViewer.getM_selectedStartYear()),
                String.format("E     Set end year [%d]", super.dataViewer.getM_selectedEndYear()),
                String.format("V     Set visualization [%s]", super.dataViewer.getM_selectedVisualization()),
                String.format("P     Plot data"),
                String.format("Q     Quit"),
        };

        // enable drawing by "percentage" with the menu drawing
        window.setXscale(0, 100);
        window.setYscale(0, 100);

        // draw the menu
        window.setPenColor(Color.BLACK);

        double yCoord = MENU_STARTING_Y;

        for(int i=0; i<menuItems.length; i++) {
            window.textLeft(MENU_STARTING_X, yCoord, menuItems[i]);
            yCoord -= MENU_ITEM_SPACING;
        }
    }

    @Override
    public boolean isMainMenu() {
        return true;
    }
}




