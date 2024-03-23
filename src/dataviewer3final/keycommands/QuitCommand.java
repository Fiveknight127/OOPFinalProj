package dataviewer3final.keycommands;

import dataviewer3final.DataViewer;
import dataviewer3final.DataViewerHUD;
import dataviewer3final.keycommands.KeyCommand;

public class QuitCommand extends KeyCommand {

	public QuitCommand(DataViewer dV, DataViewerHUD dvHUD) {
		super(dV, dvHUD);
	}
	
	public void execute() {
		System.out.println("Bye");
        System.exit(0);
	}
}
