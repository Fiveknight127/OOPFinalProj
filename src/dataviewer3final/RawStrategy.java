package dataviewer3final;
import java.awt.Color;

public class RawStrategy extends VisualizationStrategy{

    private boolean extremaVisualization = false;
    
	public RawStrategy() {
		
	}

	public boolean execute() {
		return ExtremaStrategy.getDataColor(value, false);
		
//			extremaVisualization = false;
//			return extremaVisualization;
		}
	}
