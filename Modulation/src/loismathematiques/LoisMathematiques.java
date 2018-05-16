package loismathematiques;

import java.util.logging.Level;
import java.util.logging.Logger;

import rangement.Rangement;

/**
 * @authors Alice Avoustin - Fabien Breton - Jeremy Pepin
 */
public abstract class LoisMathematiques {
	
    private Rangement objRangement;
    private double plusGrand;
    private double plusPetit;
    
    public double[][] ranger(double[] p, int nbClassesVoulues) {
        try {
            objRangement = new Rangement();
            return objRangement.trier(p,plusPetit,plusGrand,(plusGrand-plusPetit)/nbClassesVoulues);
        } catch (Exception ex) {
            Logger.getLogger(Exponentielle.class.getName()).log(Level.SEVERE, null, ex);
            return new double[0][0];
        }
    }
    
    public double[] pDesClasses(double[] range) {
        double[] pi = new double[(int)range[range.length-1]];
        double pas = (range[1]-(range[0]))/(range[2]-1);
        //double pas = 1;
        for (int i=0;i<range[range.length-1];i++){
            double pHaute = pQueXSoitInferieurA(range[0]+(i+1)*pas);
            double pBasse = pQueXSoitInferieurA(range[0]+i*pas);
            pi[i] = pHaute - pBasse;
        }
        return pi;
    }
    /**
     * Génère un nombre qui suit cette loi
     * @return le nombre
     */
    public  abstract double genererNombreSuivantLoi(); // pas de corps de méthode  
    
    public abstract double pQueXSoitInferieurA(double x); // pas de corps de méthode
    
    public Rangement getObjRangement() {
    	return this.objRangement;
    }
    
    public void setExtremites(double[] nombre) {
        for (int i=1;i<nombre.length;i++){
            if (this.plusPetit>nombre[i]) this.plusPetit=nombre[i];
            else if (this.plusGrand<nombre[i]) this.plusGrand=nombre[i];
        }
    }
    
    public double getPlusGrand() {
    	return this.plusGrand;
    }
    public double getPlusPetit() {
    	return this.plusPetit;
    }
}