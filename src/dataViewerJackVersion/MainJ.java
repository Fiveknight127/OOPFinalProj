package dataViewerJackVersion;

import java.io.FileNotFoundException;
 
public class MainJ {
    public static void main(String[] args) throws FileNotFoundException {
    	String data = "data/GlobalLandTemperaturesByState.csv";
    	//String data = "data/sample.csv";
        new DataViewerAppJ(data);
    }
}

