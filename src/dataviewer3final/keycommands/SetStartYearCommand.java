package dataviewer3final.keycommands;

import dataviewer3final.DataViewer;
import dataviewer3final.DataViewerHUD;
import dataviewer3final.StaticDebuggingStatements;
import dataviewer3final.keycommands.KeyCommand;

import javax.swing.JOptionPane;

public class SetStartYearCommand extends KeyCommand {

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
            StaticDebuggingStatements.info("User seleted: '%s'", selectedValue);
            Integer year = (Integer)selectedValue;
            if(year.compareTo(dataViewer.getM_selectedEndYear()) > 0) {
                StaticDebuggingStatements.error("new start year (%d) must not be after end year (%d)", year, dataViewer.getM_selectedEndYear());
            }
            else {
                if(!dataViewer.getM_selectedStartYear().equals(year)) {
                    //m_selectedStartYear = year;
                    dataViewer.setM_selectedStartYear(year);
                    this.dataViewerHUD.setNeedsUpdate(true);
                    this.dataViewerHUD.setNeedsUpdatePlotData(true);
                }
            }
        }
	}
}
