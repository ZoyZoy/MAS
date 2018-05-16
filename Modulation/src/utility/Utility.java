package utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utility {
	public static int getNumberDecimal(double number) {
    	String text = Double.toString(Math.abs(number));
    	int integerPlaces = text.indexOf('.');
    	return text.length() - integerPlaces - 1;
    }
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
