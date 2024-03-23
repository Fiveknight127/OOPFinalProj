package dataviewer3final;


public class PlotDataCommand extends KeyCommand{

	protected GUIState GUIState;
	
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
            dataViewerHUD.needsUpdatePlotData = true;
        }
        dataViewerHUD.needsUpdate = true;
	}
	
}

