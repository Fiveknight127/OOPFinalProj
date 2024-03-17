package dataViewerJackVersion;



public abstract class DoCommand {
    protected DataViewerHUD screen;

    public DoCommand(DataViewerHUD HUD){
        this.screen=HUD;

    }

    public abstract void execute();
}
