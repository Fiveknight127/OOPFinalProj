package dataviewer3final;

import javax.swing.JOptionPane;

public class SetEndYearCommand extends KeyCommand{

	public SetEndYearCommand(DataViewer dV, DataViewerHUD dvHUD) {
		super(dV, dvHUD);
	}
	
	public void execute() {
		// set the end year
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Choose the end year", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                dataViewer.getM_dataYears().toArray(), dataViewer.getM_selectedEndYear());

        if(selectedValue != null) {
        	dataViewerHUD.info("User seleted: '%s'", selectedValue);
            Integer year = (Integer)selectedValue;
            if(year.compareTo(dataViewer.getM_selectedStartYear()) < 0) {
            	dataViewerHUD.error("new end year (%d) must be not be before start year (%d)", year, dataViewer.getM_selectedStartYear());
            }
            else {
                if(!dataViewer.getM_selectedEndYear().equals(year)) {
                    //m_selectedEndYear = year;
                    dataViewer.setM_selectedEndYear(year);
                    dataViewerHUD.needsUpdate = true;
                    dataViewerHUD.needsUpdatePlotData = true;
                }
            }
        }
	}
}
