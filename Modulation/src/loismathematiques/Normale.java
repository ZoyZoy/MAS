/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package loismathematiques;

import java.util.Random;

/**
 *
 * @author al_fa_000
 */
public class Normale extends LoisMathematiques{

    double sigma;
    double mu;
    
    public Normale(double mu, double sigma) {
        this.mu = mu;
        this.sigma = sigma;
    }
    
    @Override
    public double genererNombreSuivantLoi() {
        return Math.sqrt(-2 * Math.log(new Random().nextDouble())) * Math.cos( 2 * Math.PI * new Random().nextDouble()) * sigma + mu ;
    }

    @Override
    public double pQueXSoitInferieurA(double x) {
        throw new UnsupportedOperationException("La fonction de répartition de la loi normale n'est pas encore disponible."); //To change body of generated methods, choose Tools | Templates.
        //return (1+erf((x-mu)/(sigma*Math.sqrt(2))))/2;
    }
    
    /*public double erf(double x){
        return 
    }*/
    
}
