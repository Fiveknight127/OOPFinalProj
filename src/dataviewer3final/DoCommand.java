package dataviewer3final;

public abstract class DoCommand {
	
	public DataViewerHUD HUD;

	public void DoCommand(DataViewerHUD HUD) {
		this.HUD = HUD;
	}
	
	
	public abstract void execute();
}
