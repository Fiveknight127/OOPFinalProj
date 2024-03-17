package dataViewerJackVersion;

import edu.du.dudraw.Draw;


public abstract class GUIState {


    protected DataViewer dataViewer;



    public GUIState(DataViewer dV){
        this.dataViewer=dV;
    }

    public abstract void drawState(Draw window);

    public abstract boolean isMainMenu();


}

//package dataviewer2;
//
//import edu.du.dudraw.Draw;
//
//public interface GUIState {
//
//    void drawState(Draw window);
//}