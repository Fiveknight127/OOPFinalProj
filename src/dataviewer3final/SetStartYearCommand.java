package dataviewer3final;

import javax.swing.JOptionPane;

public class SetStartYearCommand extends KeyCommand{

	public SetStartYearCommand(DataViewer dV, DataViewerHUD dvHUD) {
		super(dV, dvHUD);
	}
	
	public void execute() {
		// set the start year
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Choose the start year", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                dataViewer.getM_dataYears().toArray(), dataViewer.getM_selectedStartYear());

        if(selectedValue != null) {
        	dataViewerHUD.info("User seleted: '%s'", selectedValue);
            Integer year = (Integer)selectedValue;
            if(year.compareTo(dataViewer.getM_selectedEndYear()) > 0) {
            	dataViewerHUD.error("new start year (%d) must not be after end year (%d)", year, dataViewer.getM_selectedEndYear());
            }
            else {
                if(!dataViewer.getM_selectedStartYear().equals(year)) {
                    //m_selectedStartYear = year;
                    dataViewer.setM_selectedStartYear(year);
                    dataViewerHUD.needsUpdate = true;
                    dataViewerHUD.needsUpdatePlotData = true;
                }
            }
        }
	}
}
