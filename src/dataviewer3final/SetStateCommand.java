package dataviewer3final;

import javax.swing.JOptionPane;

public class SetStateCommand extends KeyCommand{

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
            dataViewerHUD.info("User selected: '%s'", selectedValue);
            if(!selectedValue.equals(dataViewer.getM_selectedState())) {
                // change in data
                //m_selectedState = (String)selectedValue;
                dataViewer.setM_selectedState((String)selectedValue);
                dataViewerHUD.needsUpdate = true;
                dataViewerHUD.needsUpdatePlotData = true;
            }
        }
	}
}