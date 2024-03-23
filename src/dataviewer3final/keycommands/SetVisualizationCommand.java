package dataviewer3final.keycommands;

import dataviewer3final.DataViewer;
import dataviewer3final.DataViewerHUD;
import dataviewer3final.StaticDebuggingStatements;
import dataviewer3final.keycommands.KeyCommand;

import javax.swing.JOptionPane;

public class SetVisualizationCommand extends KeyCommand {

	public SetVisualizationCommand(DataViewer dV, DataViewerHUD dvHUD) {
		super(dV, dvHUD);
	}
	
	public void execute() {
		// set the visualization
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Choose the visualization mode", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                dataViewer.getVisualizationModes(), dataViewer.getM_selectedVisualization());

        if(selectedValue != null) {
            StaticDebuggingStatements.info("User seleted: '%s'", selectedValue);
            String visualization = (String)selectedValue;
            if(!dataViewer.getM_selectedVisualization().equals(visualization)) {
                //m_selectedVisualization = visualization;
                dataViewer.setM_selectedVisualization(visualization);
                dataViewerHUD.setNeedsUpdate(true);
            }
        }
	}
}
