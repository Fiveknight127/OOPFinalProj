package dataviewer3final;

import java.io.FileNotFoundException;

public class Main3 {

    public static void main(String[] args) throws FileNotFoundException {
        String data = "data/GlobalLandTemperaturesByState.csv";
        //String data = "data/sample.csv";
        DataViewer dataViewer = new DataViewer(data);
        DataViewerHUD hud = new DataViewerHUD(dataViewer);
        
        dataViewer.addObserver(hud);
        

    }
}
