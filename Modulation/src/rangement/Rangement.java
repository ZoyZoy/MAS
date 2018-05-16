/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rangement;

import java.util.Arrays;

import utility.Utility;

/**
 *
 * @author jerem
 */
public class  Rangement {
	
	private String[] classesLibelles;
	private double[][] numbersOrdered;
    
    public double[][] trier(double[] numbers, double rangeMin, double rangeMax, double step) throws Exception {
        if(rangeMin > rangeMax) {
            throw new IllegalArgumentException("La borne minimale doit etre inferieure a la borne maximale.");
        }
        
        if(rangeMax < rangeMin) {
            throw new IllegalArgumentException("La borne minimale doit etre superieure a la borne minimale.");
        }
        
        if(step < 0) {
            throw new IllegalArgumentException("Le pas doit etre superieur a 0.");
        }
        
        int numberClasses = (int) ((rangeMax - rangeMin) / step);
        initClassesLibelles(numberClasses, rangeMin, rangeMax, step);
             
        numbersOrdered = new double[numberClasses][];
   
        
        // Parcours les nombres
        for( int i = 0; i < numbers.length; i++ ) {
            if( numbers[i] < rangeMin || numbers[i] > rangeMax  ) {
                throw new Exception("Le nombre "+numbers[i]+" n'est pas compris entre "+rangeMin+" et "+rangeMax+".");
            }
                            
            // Parcours les nombres rang�s
            for( int y = 0; y < numbersOrdered.length; y++ ) {
                
                double borneMinTmp = rangeMin + step * y;
                double borneMaxTmp = rangeMin + step * y + step;
                
                boolean isLast = (y == numbersOrdered.length-1) ? true : false;
                
                if( (numbers[i] >= borneMinTmp && numbers[i] <  borneMaxTmp) || (isLast && numbers[i] == borneMaxTmp) ) {
                    // Ajoute un �l�ment dans le tableau
                    if( numbersOrdered[y] == null ) {
                        numbersOrdered[y] = new double[1];
                    } else {
                        numbersOrdered[y] = Arrays.copyOf(numbersOrdered[y], numbersOrdered[y].length + 1);
                    }
                    
                    numbersOrdered[y][numbersOrdered[y].length - 1] = numbers[i];
                    
                    break;
                }
            }
        }
        
        return numbersOrdered;
    }
    
    private void initClassesLibelles(int numbersClasses, double rangeMin, double rangeMax, double step) {
    	classesLibelles = new String[numbersClasses];
    	int precision = Utility.getNumberDecimal(step);
    	precision = precision > 3 ? 2 : precision;
    	
    	for(int i = 0; i < numbersClasses; i++) {
            double borneMinTmp = Utility.round(rangeMin + step * i, precision);
            double borneMaxTmp = Utility.round(rangeMin + step * i + step, precision);
    		
            boolean isLast = (i == numbersClasses-1) ? true : false;
            
    		classesLibelles[i] = "["+borneMinTmp+";"+borneMaxTmp+(isLast ? "]" : "[");
    	}
    }

    public double[][] getNumbersOrdered() {
    	return this.numbersOrdered;
    }
    
    public String[] getClassesLibelle() {
    	return this.classesLibelles;
    }
        
}
