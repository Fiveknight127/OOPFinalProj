package dataviewer3final.keycommands;

import dataviewer3final.DataViewer;
import dataviewer3final.DataViewerHUD;
import dataviewer3final.StaticDebuggingStatements;
import dataviewer3final.keycommands.KeyCommand;

import javax.swing.JOptionPane;

public class SetEndYearCommand extends KeyCommand {

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
        	StaticDebuggingStatements.info("User seleted: '%s'", selectedValue);
            Integer year = (Integer)selectedValue;
            if(year.compareTo(dataViewer.getM_selectedStartYear()) < 0) {
                StaticDebuggingStatements.error("new end year (%d) must be not be before start year (%d)", year, dataViewer.getM_selectedStartYear());
            }
            else {
                if(!dataViewer.getM_selectedEndYear().equals(year)) {
                    //m_selectedEndYear = year;
                    dataViewer.setM_selectedEndYear(year);
                    dataViewerHUD.setNeedsUpdate(true);
                    dataViewerHUD.setNeedsUpdatePlotData(true);
                }
            }
        }
	}
}
