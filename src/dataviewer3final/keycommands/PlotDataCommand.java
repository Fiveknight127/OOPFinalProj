package dataviewer3final.keycommands;


import dataviewer3final.DataViewer;
import dataviewer3final.DataViewerHUD;
import dataviewer3final.GUIDataState;
import dataviewer3final.GUIState;
import dataviewer3final.keycommands.KeyCommand;

public class PlotDataCommand extends KeyCommand {

	protected dataviewer3final.GUIState GUIState;
	
	public PlotDataCommand(DataViewer dV, DataViewerHUD dvHUD, GUIState gs) {
		super(dV, dvHUD);
		this.GUIState = gs;
	}
	
	public void execute() {
		// plot the data
        //m_guiMode = GUI_MODE_DATA; OLD
        dataViewerHUD.setGUIState(new GUIDataState(dataViewer));//
        
        if(dataViewer.getM_plotData() == null) {
            // first time going to render data need to generate the plot data
            dataViewerHUD.setNeedsUpdatePlotData(true);
        }
        dataViewerHUD.setNeedsUpdate(true);
	}
	
}

