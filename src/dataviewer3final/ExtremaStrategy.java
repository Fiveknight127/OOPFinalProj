package dataviewer3final;

import java.awt.Color;

public class ExtremaStrategy extends VisualizationStrategy{

    private static double		TEMPERATURE_MAX_C = 30.0;
    private static double		TEMPERATURE_MIN_C = -10.0;
    private static double		TEMPERATURE_RANGE = TEMPERATURE_MAX_C - TEMPERATURE_MIN_C;

	@Override
	public Color execute() {
		// TODO Auto-generated method stub
		return null;
	}
    static Color getDataColor(Double value, boolean doGrayscale) {
        if(null == value) {
            return null;
        }
        double pct = (value - TEMPERATURE_MIN_C) / TEMPERATURE_RANGE;
        //trace("converted %f raw value to %f %%", value, pct);

        if (pct > 1.0) {
            pct = 1.0;
        }
        else if (pct < 0.0) {
            pct = 0.0;
        }
        int r, g, b;
        // Replace the color scheme with my own
        if (!doGrayscale) {
            r = (int)(255.0 * pct);
            g = 0;
            b = (int)(255.0 * (1.0-pct));

        } else {
            // Grayscale for the middle extema
            r = g = b = (int)(255.0 * pct);
        }
        return new Color(r, g, b);
    }
}
