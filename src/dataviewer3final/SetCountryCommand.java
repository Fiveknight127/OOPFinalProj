package dataviewer3final;

import javax.swing.JOptionPane;

public class SetCountryCommand extends KeyCommand{

	public SetCountryCommand(DataViewer dV, DataViewerHUD dvHUD) {
		super(dV, dvHUD);
	}
	
	public void execute() {
		// set the Country
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Choose a Country", "Input",
                JOptionPane.INFORMATION_MESSAGE, null,
                dataViewer.getM_dataCountries().toArray(), dataViewer.getM_selectedCountry());

        if(selectedValue != null) {
        	dataViewerHUD.info("User selected: '%s'", selectedValue);
            if(!selectedValue.equals(dataViewer.getM_selectedCountry())) {
                // change in data
                //m_selectedCountry = (String)selectedValue;
                dataViewer.setM_selectedCountry((String) selectedValue);
//                try {
                dataViewer.loadData();
//                }
//                catch(FileNotFoundException e) {
//                    // convert to a runtime exception since
//                    // we can't add throws to this method
//                    throw new RuntimeException(e);
//                }
                dataViewerHUD.needsUpdate = true;
                dataViewerHUD.needsUpdatePlotData = true;
            }
        }
	}
}
