package dataviewer3final;

public class QuitCommand extends KeyCommand{

	public QuitCommand(DataViewer dV, DataViewerHUD dvHUD) {
		super(dV, dvHUD);
	}
	
	public void execute() {
		System.out.println("Bye");
        System.exit(0);
	}
}
