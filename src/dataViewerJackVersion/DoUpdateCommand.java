package dataViewerJackVersion;

public class DoUpdateCommand extends DoCommand{

    public DoUpdateCommand(DataViewerHUD window){
        super(window);
    }


    public void execute() {
        super.screen.update();
    }
}
