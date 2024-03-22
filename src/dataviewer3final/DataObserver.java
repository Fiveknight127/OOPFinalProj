package dataviewer3final;

public abstract class DataObserver {
	public DataViewer dv; 
	
	public DataObserver(DataViewer subject) {
		this.dv = subject; 
	}
	
	public abstract void updateObserver();
}
