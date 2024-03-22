package dataviewer3final;

import javax.swing.JOptionPane;

public class SetVisualizationCommand extends KeyCommand{

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
        	dataViewerHUD.info("User seleted: '%s'", selectedValue);
            String visualization = (String)selectedValue;
            if(!dataViewer.getM_selectedVisualization().equals(visualization)) {
                //m_selectedVisualization = visualization;
                dataViewer.setM_selectedVisualization(visualization);
                dataViewerHUD.needsUpdate = true;
            }
        }
	}
}
