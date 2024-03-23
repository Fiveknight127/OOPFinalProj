package dataviewer3final.keycommands;

import dataviewer3final.DataViewer;
import dataviewer3final.DataViewerHUD;
import dataviewer3final.StaticDebuggingStatements;
import dataviewer3final.keycommands.KeyCommand;

import javax.swing.JOptionPane;

public class SetStateCommand extends KeyCommand {

	public SetStateCommand(DataViewer dV, DataViewerHUD dvHUD) {
		super(dV, dvHUD);
	}
	
	public void execute() {
		// set the state
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Choose a State", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                dataViewer.getM_dataStates().toArray(), dataViewer.getM_selectedState());

        if(selectedValue != null) {
            StaticDebuggingStatements.info("User selected: '%s'", selectedValue);
            if(!selectedValue.equals(dataViewer.getM_selectedState())) {
                // change in data
                //m_selectedState = (String)selectedValue;
                dataViewer.setM_selectedState((String)selectedValue);
                this.dataViewerHUD.setNeedsUpdate(true);
                this.dataViewerHUD.setNeedsUpdatePlotData(true);
            }
        }
	}
}
