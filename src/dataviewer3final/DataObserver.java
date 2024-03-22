package dataviewer3final;

public abstract class DataObserver {
	public DataViewer dataViewer; 
	
	public DataObserver(DataViewer subject) {
		this.dataViewer = subject; 
	}
	
	public abstract void updateObserver();
}
