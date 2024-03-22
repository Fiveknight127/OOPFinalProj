package dataviewer3final;

public abstract class KeyCommand {

	protected DataViewer dataViewer;
	protected DataViewerHUD dataViewerHUD;
	
	public KeyCommand(DataViewer dV, DataViewerHUD dvHUD) { //added DataViewerHUD?
		this.dataViewer =dV;
		this.dataViewerHUD = dvHUD;
	}
	
	public void execute() {
		
	}
	
}
