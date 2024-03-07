package dataviewer2;

import dataviewer1orig.DataViewerApp;

import java.io.FileNotFoundException;

public class Main2 {

    public static void main(String[] args) throws FileNotFoundException {
        String data = "data/GlobalLandTemperaturesByState.csv";
        //String data = "data/sample.csv";
        new DataViewer(data);
    }
}
