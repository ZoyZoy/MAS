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
public class Poisson extends LoisMathematiques{
    double lambda;
    
    public Poisson(double lambda) {
        this.lambda = lambda;
    }

    @Override
    public double genererNombreSuivantLoi() {
        double p = 1.0;
        int i = 0;
        do{
          i++;
          p *= new Random().nextDouble();
        } while(p > Math.exp(-lambda)) ;
        return i-1;
    }

    @Override
    public double pQueXSoitInferieurA(double x) {
        return gammaIncomplete((int)(x+1),lambda)/(factorielle((int)x));
    }
    
    int factorielle(int nombre)
    {
        int x = 0;
        if (nombre == 1)
        {
            x = 1;
            return(x);
        }
        else
        {
            x = nombre * factorielle(nombre - 1); // on appelle encore la fonction qui s'appellera encore elle meme si nombre est != de 1
            return(x);
        }
    }
    public double gammaIncomplete(double x, double y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
