package dataviewer3final.keycommands;

import dataviewer3final.DataViewer;
import dataviewer3final.DataViewerHUD;
import dataviewer3final.GUIMainMenuState;
import dataviewer3final.GUIState;
import dataviewer3final.keycommands.KeyCommand;

public class MainMenuCommand extends KeyCommand {

	protected dataviewer3final.GUIState GUIState;
	
	public MainMenuCommand(DataViewer dV, DataViewerHUD dvHUD, GUIState gs) {
		super(dV, dvHUD);
		this.GUIState = gs;
	}
	
	public void execute() {
		//m_guiMode = GUI_MODE_MAIN_MENU;
        dataViewerHUD.setGUIState(new GUIMainMenuState(dataViewer));//
		dataViewerHUD.setNeedsUpdate(true);
	}
}
