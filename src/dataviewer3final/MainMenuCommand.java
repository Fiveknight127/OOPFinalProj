package dataviewer3final;

public class MainMenuCommand extends KeyCommand{

	protected GUIState GUIState;
	
	public MainMenuCommand(DataViewer dV, DataViewerHUD dvHUD, GUIState gs) {
		super(dV, dvHUD);
		this.GUIState = gs;
	}
	
	public void execute() {
		//m_guiMode = GUI_MODE_MAIN_MENU;
        dataViewerHUD.setGUIState(new GUIMainMenuState(dataViewer));//
        dataViewerHUD.needsUpdate = true;
	}
}
