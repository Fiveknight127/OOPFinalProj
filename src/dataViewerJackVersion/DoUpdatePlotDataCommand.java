package dataViewerJackVersion;

public class DoUpdatePlotDataCommand extends DoCommand{

    public DoUpdatePlotDataCommand(DataViewerHUD window){
        super(window);
    }

    public void execute(){
        super.screen.updatePlotData();
    }
}
