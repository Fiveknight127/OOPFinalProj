package dataviewer3final;

import java.io.FileNotFoundException;

public class Main3 {

    public static void main(String[] args) throws FileNotFoundException {
        String data = "data/GlobalLandTemperaturesByState.csv";
        //String data = "data/sample.csv";
        new DataViewer(data);
        //new DataViewerHUD(data);
        //new DataViewer(data);
    }
}
